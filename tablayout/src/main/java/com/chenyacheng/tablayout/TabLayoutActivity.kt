package com.chenyacheng.tablayout

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chenyacheng.tablayout.databinding.ActivityTabLayoutBinding

/**
 * @author BD
 * @date 2022/10/10 17:34
 */
class TabLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityTabLayoutBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val tabTextList: MutableList<String> = ArrayList()
        tabTextList.add("前端")
        tabTextList.add("移动端")
        tabTextList.add("后端")
        binding.tabLayout.setTabTextList(tabTextList)
        binding.tabLayout.addTabSelectedListener(object : OnTabClickListener {
            override fun tabSelectedListener(tabPosition: Int) {
                val makeText =
                    Toast.makeText(
                        this@TabLayoutActivity,
                        "点击了第" + tabPosition + "项",
                        Toast.LENGTH_SHORT
                    )
                makeText.setGravity(Gravity.CENTER, 0, 0)
                makeText.show()
            }
        })

        val tabTextList1: MutableList<String> = ArrayList()
        tabTextList1.add("语文")
        tabTextList1.add("数学")
        tabTextList1.add("英语")
        tabTextList1.add("化学")
        binding.tabLayout1.setTabTextList(tabTextList1)
        binding.tabLayout1.addTabSelectedListener(object : OnTabClickListener {
            override fun tabSelectedListener(tabPosition: Int) {
                val makeText =
                    Toast.makeText(
                        this@TabLayoutActivity,
                        "点击了第" + tabPosition + "项",
                        Toast.LENGTH_SHORT
                    )
                makeText.setGravity(Gravity.CENTER, 0, 0)
                makeText.show()
            }
        })
    }
}