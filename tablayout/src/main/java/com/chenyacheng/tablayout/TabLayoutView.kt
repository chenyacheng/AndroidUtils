package com.chenyacheng.tablayout

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat

/**
 * @author BD
 * @date 2022/9/8 11:03
 */
class TabLayoutView @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(mContext, attrs, defStyleAttr) {

    init {
        setDefaultData()
        initAttr(attrs, defStyleAttr)
    }

    private lateinit var paint: Paint
    private lateinit var textPaint: Paint
    private lateinit var indicatorPaint: Paint
    private var viewWidth = 0
    private var viewHeight = 0 //测量的高度

    private var textWidth = 0f
    private var tabPosition = 0
    private lateinit var tabTextList: MutableList<String>
    private var viewBgCorners = 0f //圆角的大小

    private var tabTextColor = 0
    private var tabSelectTextColor = 0
    private var selectColor = 0
    private var tabTextSize = 0f
    private var tabTextStyle = 0
    private var viewBg = 0
    private var arcControlX = 100 //值越大，弧度越大
    private var tabNumber = 0 //tab的数量

    private val arcControlY = 3
    private val arcWidth = 120 //曲线的宽度
    private var centerX = 0

    private var showTabIndicator = false//默认不显示指示线
    private var showTabIndicatorSelect = false

    private var tabIndicatorHeight = 0f//指示线的高度
    private var tabIndicatorWidth = 0f //指示线的宽度
    private var tabIndicatorSpacing = 0f//指示线的与文字的间隔

    private val rectFBg = RectF()

    private val clipPath = Path()
    private val pathLeft = Path()
    private val pathRight = Path()
    private val pathCenter = Path()

    private var onTabClickListener: OnTabClickListener? = null

    private fun setDefaultData() {
        viewBg = ContextCompat.getColor(mContext, R.color.common_ffffffff)
        selectColor = ContextCompat.getColor(mContext, R.color.common_ffffffff)
        tabTextColor = ContextCompat.getColor(mContext, R.color.common_ffffffff)
        //默认选中文本和未选中文本是一个颜色值
        tabSelectTextColor = ContextCompat.getColor(mContext, R.color.common_ffffffff)

        val font = Typeface.create(Typeface.SANS_SERIF, tabTextStyle)

        indicatorPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        indicatorPaint.style = Paint.Style.FILL
        indicatorPaint.typeface = font

        tabTextStyle = Typeface.NORMAL
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL
        textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        textPaint.style = Paint.Style.FILL

        textPaint.typeface = font

        tabTextList = ArrayList()

        tabNumber = tabTextList.size
    }

    private fun initAttr(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = mContext.obtainStyledAttributes(
            attrs,
            R.styleable.TabLayoutView,
            defStyleAttr,
            0
        )
        val indexCount = typedArray.indexCount
        for (i in 0 until indexCount) {
            when (val index = typedArray.getIndex(i)) {
                R.styleable.TabLayoutView_arcControlX -> {
                    arcControlX = typedArray.getInt(index, arcControlX)
                }
                R.styleable.TabLayoutView_select_tab_color -> {
                    selectColor = typedArray.getColor(index, selectColor)
                }
                R.styleable.TabLayoutView_tab_view_bg -> {
                    viewBg = typedArray.getColor(index, viewBg)
                }
                R.styleable.TabLayoutView_tabTextColor -> {
                    tabTextColor = typedArray.getColor(index, tabTextColor)
                }
                R.styleable.TabLayoutView_tabSelectTextColor -> {
                    tabSelectTextColor = typedArray.getColor(index, tabSelectTextColor)
                }
                R.styleable.TabLayoutView_tabTextSize -> {
                    tabTextSize = typedArray.getDimension(index, tabTextSize)
                }
                R.styleable.TabLayoutView_view_bg_corners -> {
                    viewBgCorners = typedArray.getDimension(index, viewBgCorners)
                }
                R.styleable.TabLayoutView_tab_indicator_height -> {
                    tabIndicatorHeight = typedArray.getDimension(index, tabIndicatorHeight)
                }
                R.styleable.TabLayoutView_tab_indicator_width -> {
                    tabIndicatorWidth = typedArray.getDimension(index, tabIndicatorWidth)
                }
                R.styleable.TabLayoutView_tab_indicator_spacing -> {
                    tabIndicatorSpacing = typedArray.getDimension(index, tabIndicatorSpacing)
                }
                R.styleable.TabLayoutView_show_indicator -> {
                    showTabIndicator = typedArray.getBoolean(index, showTabIndicator)
                }
                R.styleable.TabLayoutView_show_indicator_select -> {
                    showTabIndicatorSelect = typedArray.getBoolean(index, showTabIndicatorSelect)
                }
            }
        }
        typedArray.recycle()
    }

    fun setSelectTab(tabPosition: Int) {
        if (tabPosition < tabNumber) {
            this.tabPosition = tabPosition
            invalidate()
            if (onTabClickListener != null) {
                onTabClickListener!!.tabSelectedListener(tabPosition)
            }
        }
    }

    /**
     * 设置tab项文本的字体样式,默认Typeface.NORMAL
     * Typeface.NORMAL  Typeface.BOLD  Typeface.ITALIC
     *
     * @param tabTextStyle
     */
    fun setTabTextStyle(tabTextStyle: Int) {
        val font = Typeface.create(Typeface.SANS_SERIF, tabTextStyle)
        textPaint.typeface = font
        indicatorPaint.typeface = font
        invalidate()
    }

    fun setTabTextList(tabTexts: MutableList<String>) {
        tabTextList = tabTexts
        tabNumber = tabTextList.size
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        viewWidth = right - left
        viewHeight = bottom - top
        centerX = viewWidth / 2
        textWidth = (viewWidth - arcWidth * (tabNumber - 1)) / tabNumber.toFloat()
        rectFBg.left = 0f
        rectFBg.top = 0f
        rectFBg.right = viewWidth.toFloat()
        rectFBg.bottom = viewHeight.toFloat()
        textPaint.textSize = tabTextSize
        textPaint.color = tabTextColor
        indicatorPaint.color = tabTextColor
        indicatorPaint.strokeWidth = tabIndicatorHeight
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var isHandleClick = false //是否处理点击事件
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val x = event.x
                var i = 0
                while (i < tabNumber) {
                    if (x <= (i + 1) * textWidth + i * arcWidth + arcWidth / 2) { //点击的第一个按钮
                        tabPosition = i
                        if (onTabClickListener != null) {
                            onTabClickListener!!.tabSelectedListener(tabPosition)
                        }
                        invalidate()
                        isHandleClick = true
                        break
                    }
                    i++
                }
                return isHandleClick
            }
        }
        return super.onTouchEvent(event)
    }

    //tab 选择监听
    fun addTabSelectedListener(onTabClickListener: OnTabClickListener?) {
        this.onTabClickListener = onTabClickListener
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = viewBg
        //范围裁切 path canvas.save(); canvas.restore();
        clipPath.addRoundRect(rectFBg, viewBgCorners, viewBgCorners, Path.Direction.CW)
        canvas.clipPath(clipPath)
        canvas.drawRect(rectFBg, paint)
        when (tabPosition) {
            0 -> {
                //最左边的图形
                pathLeft.lineTo(textWidth, 0f)
                pathLeft.rCubicTo(
                    arcControlX.toFloat(), arcControlY.toFloat(),
                    arcWidth.toFloat() - arcControlX, viewHeight - arcControlY.toFloat(),
                    arcWidth.toFloat(), viewHeight.toFloat()
                )
                pathLeft.lineTo(textWidth, viewHeight.toFloat())
                pathLeft.lineTo(0f, viewHeight.toFloat())
                paint.color = selectColor
                canvas.drawPath(pathLeft, paint)
            }
            tabNumber - 1 -> {
                //最右边的图形
                pathRight.moveTo(tabPosition * textWidth + (tabPosition - 1) * arcWidth, 0f)
                pathRight.rCubicTo(
                    arcControlX.toFloat(), arcControlY.toFloat(),
                    arcWidth.toFloat() - arcControlX, viewHeight - arcControlY.toFloat(),
                    arcWidth.toFloat(), viewHeight.toFloat()
                )
                pathRight.lineTo(viewWidth.toFloat(), viewHeight.toFloat())
                pathRight.lineTo(viewWidth.toFloat(), 0f)
                paint.color = selectColor
                canvas.drawPath(pathRight, paint)
            }
            else -> {
                pathCenter.reset()
                //中间的图形
                pathCenter.moveTo(tabPosition * textWidth + (tabPosition - 1) * arcWidth, 0f)
                pathCenter.rCubicTo(
                    arcControlX.toFloat(), arcControlY.toFloat(),
                    arcWidth.toFloat() - arcControlX, viewHeight - arcControlY.toFloat(),
                    arcWidth.toFloat(), viewHeight.toFloat()
                )
                pathCenter.lineTo(
                    tabPosition * textWidth + tabPosition * arcWidth + textWidth + arcWidth,
                    viewHeight.toFloat()
                )
                pathCenter.cubicTo(
                    tabPosition * textWidth + tabPosition * arcWidth + textWidth + arcWidth - arcControlX,
                    viewHeight - arcControlY.toFloat(),
                    tabPosition * textWidth + tabPosition * arcWidth + textWidth + arcControlX,
                    arcControlY.toFloat(),
                    tabPosition * textWidth + tabPosition * arcWidth + textWidth,
                    0f
                )
                pathCenter.lineTo(tabPosition * textWidth + tabPosition * arcWidth, 0f)
                paint.color = selectColor
                canvas.drawPath(pathCenter, paint)
            }
        }
        drawTextContent(canvas)
    }

    private fun drawTextContent(canvas: Canvas) {
        for (i in tabTextList.indices) {
            val strTabText = tabTextList[i]
            val rectText = Rect()
            textPaint.getTextBounds(strTabText, 0, strTabText.length, rectText)
            val strWidth = rectText.width()
            val strHeight = rectText.height()
            if (i == tabPosition) { //选中的tab项文本
                textPaint.color = tabSelectTextColor
                indicatorPaint.color = tabSelectTextColor
            } else {
                textPaint.color = tabTextColor
                if (showTabIndicatorSelect) {
                    indicatorPaint.color = Color.TRANSPARENT
                } else {
                    indicatorPaint.color = tabTextColor
                }
            }
            if (i == 0) {
                canvas.drawText(
                    strTabText,
                    (textWidth + arcWidth / 2) / 2 - strWidth / 2,
                    viewHeight / 2 + strHeight / 2.toFloat(),
                    textPaint
                )
                if (showTabIndicator || showTabIndicatorSelect) {
                    val maxSpace = viewHeight / 2 - strHeight / 2.toFloat()
                    if (tabIndicatorSpacing > maxSpace) {
                        tabIndicatorSpacing = maxSpace
                    }
                    if (tabIndicatorWidth > 0) {
                        canvas.drawLine(
                            (textWidth + arcWidth / 2) / 2 - strWidth / 2 + strWidth / 2 - tabIndicatorWidth / 2,
                            viewHeight / 2 + strHeight / 2.toFloat() + tabIndicatorSpacing,
                            (textWidth + arcWidth / 2) / 2 - strWidth / 2 + strWidth / 2 - tabIndicatorWidth / 2 + tabIndicatorWidth,
                            viewHeight / 2 + strHeight / 2.toFloat() + tabIndicatorSpacing,
                            indicatorPaint
                        )
                    } else {
                        canvas.drawLine(
                            (textWidth + arcWidth / 2) / 2 - strWidth / 2,
                            viewHeight / 2 + strHeight / 2.toFloat() + tabIndicatorSpacing,
                            (textWidth + arcWidth / 2) / 2 - strWidth / 2 + strWidth,
                            viewHeight / 2 + strHeight / 2.toFloat() + tabIndicatorSpacing,
                            indicatorPaint
                        )
                    }
                }
            } else if (i == tabTextList.size - 1) {
                canvas.drawText(
                    strTabText,
                    viewWidth - (textWidth + arcWidth / 2) / 2 - strWidth / 2,
                    viewHeight / 2 + strHeight / 2.toFloat(),
                    textPaint
                )
                if (showTabIndicator || showTabIndicatorSelect) {
                    val maxSpace = viewHeight / 2 - strHeight / 2.toFloat()
                    if (tabIndicatorSpacing > maxSpace) {
                        tabIndicatorSpacing = maxSpace
                    }
                    if (tabIndicatorWidth > 0) {
                        canvas.drawLine(
                            viewWidth - (textWidth + arcWidth / 2) / 2 - strWidth / 2 + strWidth / 2 - tabIndicatorWidth / 2,
                            viewHeight / 2 + strHeight / 2.toFloat() + tabIndicatorSpacing,
                            viewWidth - (textWidth + arcWidth / 2) / 2 - strWidth / 2 + strWidth / 2 - tabIndicatorWidth / 2 + tabIndicatorWidth,
                            viewHeight / 2 + strHeight / 2.toFloat() + tabIndicatorSpacing,
                            indicatorPaint
                        )
                    } else {
                        canvas.drawLine(
                            viewWidth - (textWidth + arcWidth / 2) / 2 - strWidth / 2,
                            viewHeight / 2 + strHeight / 2.toFloat() + tabIndicatorSpacing,
                            viewWidth - (textWidth + arcWidth / 2) / 2 - strWidth / 2 + strWidth,
                            viewHeight / 2 + strHeight / 2.toFloat() + tabIndicatorSpacing,
                            indicatorPaint
                        )
                    }
                }
            } else {
                canvas.drawText(
                    strTabText,
                    textWidth * i + arcWidth * (i - 1) + (textWidth + 2 * arcWidth) / 2 - strWidth / 2,
                    viewHeight / 2 + strHeight / 2.toFloat(),
                    textPaint
                )
                if (showTabIndicator || showTabIndicatorSelect) {
                    val maxSpace = viewHeight / 2 - strHeight / 2.toFloat()
                    if (tabIndicatorSpacing > maxSpace) {
                        tabIndicatorSpacing = maxSpace
                    }
                    if (tabIndicatorWidth > 0) {
                        canvas.drawLine(
                            textWidth * i + arcWidth * (i - 1) + (textWidth + 2 * arcWidth) / 2 - strWidth / 2 + strWidth / 2 - tabIndicatorWidth / 2,
                            viewHeight / 2 + strHeight / 2.toFloat() + tabIndicatorSpacing,
                            textWidth * i + arcWidth * (i - 1) + (textWidth + 2 * arcWidth) / 2 - strWidth / 2 + strWidth / 2 - tabIndicatorWidth / 2 + tabIndicatorWidth,
                            viewHeight / 2 + strHeight / 2.toFloat() + tabIndicatorSpacing,
                            indicatorPaint
                        )
                    } else {
                        canvas.drawLine(
                            textWidth * i + arcWidth * (i - 1) + (textWidth + 2 * arcWidth) / 2 - strWidth / 2,
                            viewHeight / 2 + strHeight / 2.toFloat() + tabIndicatorSpacing,
                            textWidth * i + arcWidth * (i - 1) + (textWidth + 2 * arcWidth) / 2 - strWidth / 2 + strWidth,
                            viewHeight / 2 + strHeight / 2.toFloat() + tabIndicatorSpacing,
                            indicatorPaint
                        )
                    }
                }
            }
        }
    }
}