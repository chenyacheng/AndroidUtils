package com.chenyacheng.popdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * 自定义订单查看页面的日期选择
 *
 * @author chenyacheng
 * @date 2018/10/26
 */
public class DateNumberPickerDialog implements View.OnClickListener, NumberPicker.OnValueChangeListener {

    private Dialog dialog;
    private ChooseDateInterface chooseDateInterface;
    private NumberPicker numberPickerDay;
    private String[] newDateArray = new String[3];

    void createDialog(Context context, String msg, String[] oldDateArray, ChooseDateInterface chooseDateInterface) {
        this.chooseDateInterface = chooseDateInterface;
        newDateArray[0] = oldDateArray[0];
        newDateArray[1] = oldDateArray[1];
        newDateArray[2] = oldDateArray[2];

        dialog = new Dialog(context, R.style.dateDialog);
        dialog.setContentView(R.layout.dialog_choose_date);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        // 初始化控件
        TextView tvChooseTime = dialog.findViewById(R.id.tv_choose_time);
        tvChooseTime.setText(msg);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnSure = dialog.findViewById(R.id.btn_sure);
        btnCancel.setOnClickListener(this);
        btnSure.setOnClickListener(this);
        NumberPicker numberPickerYear = dialog.findViewById(R.id.number_picker_year);
        NumberPicker numberPickerMonth = dialog.findViewById(R.id.number_picker_month);
        numberPickerDay = dialog.findViewById(R.id.number_picker_day);
        // 设置年份
        String[] yearArray = new String[101];
        for (int i = 0; i <= yearArray.length - 1; ++i) {
            yearArray[i] = 2000 + i + "年";
        }
        numberPickerYear.setMinValue(0);
        numberPickerYear.setMaxValue(yearArray.length - 1);
        numberPickerYear.setDisplayedValues(yearArray);
        numberPickerYear.setValue(Integer.parseInt(newDateArray[0]) - 2000);
        // 设置月份
        String[] monthArray = new String[12];
        for (int i = 0; i <= monthArray.length - 1; ++i) {
            monthArray[i] = 1 + i + "月";
        }
        numberPickerMonth.setMinValue(0);
        numberPickerMonth.setMaxValue(monthArray.length - 1);
        numberPickerMonth.setDisplayedValues(monthArray);
        numberPickerMonth.setValue(Integer.parseInt(newDateArray[1]) - 1);
        // 设置日份
        int days = getNumberOfDays(Integer.parseInt(newDateArray[0]), Integer.parseInt(newDateArray[1]));
        String[] dayArray = new String[31];
        for (int i = 0; i <= dayArray.length - 1; ++i) {
            dayArray[i] = 1 + i + "日";
        }
        int twentyEight = 28;
        int twentyNine = 29;
        int thirty = 30;
        numberPickerDay.setMinValue(0);
        if (twentyEight == days) {
            numberPickerDay.setMaxValue(days - 1);
        } else if (twentyNine == days) {
            numberPickerDay.setMaxValue(days - 1);
        } else if (thirty == days) {
            numberPickerDay.setMaxValue(days - 1);
        } else {
            numberPickerDay.setMaxValue(dayArray.length - 1);
        }
        numberPickerDay.setDisplayedValues(dayArray);
        numberPickerDay.setValue(Integer.parseInt(newDateArray[2]) - 1);
        // 设置监听
        numberPickerYear.setOnValueChangedListener(this);
        numberPickerMonth.setOnValueChangedListener(this);
        numberPickerDay.setOnValueChangedListener(this);
        // 设置分割线颜色
        setNumberPickerDividerColor(numberPickerYear);
        setNumberPickerDividerColor(numberPickerMonth);
        setNumberPickerDividerColor(numberPickerDay);
        // 设置字体颜色
        setNumberPickerTextColor(numberPickerYear, Color.parseColor("#FF393434"));
        setNumberPickerTextColor(numberPickerMonth, Color.parseColor("#FF393434"));
        setNumberPickerTextColor(numberPickerDay, Color.parseColor("#FF393434"));
    }

    /**
     * 获取某年某月的总天数
     *
     * @param year  年份
     * @param month 月份
     * @return 返回天数
     */
    private int getNumberOfDays(int year, int month) {
        switch (month) {
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (isLeapYear(year)) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                return 31;

        }
    }

    private void setNumberPickerDividerColor(NumberPicker numberPicker) {
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            String mSelectionDivider = "mSelectionDivider";
            if (mSelectionDivider.equals(pf.getName())) {
                pf.setAccessible(true);
                try {
                    // 设置分割线的颜色值
                    pf.set(numberPicker, new ColorDrawable(Color.parseColor("#FFFE6D56")));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private void setNumberPickerTextColor(NumberPicker numberPicker, int color) {
        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = numberPicker.getChildAt(i);
            if (child instanceof EditText) {
                try {
                    Field selectorWheelPaintField = numberPicker.getClass().getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    Paint paint = (Paint) selectorWheelPaintField.get(numberPicker);
                    if (null != paint) {
                        paint.setColor(color);
                    }
                    ((EditText) child).setTextColor(color);
                    numberPicker.invalidate();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断是否闰年
     *
     * @param year 年份
     * @return true或false
     */
    private boolean isLeapYear(int year) {
        return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_cancel) {
            if (null != dialog) {
                dialog.dismiss();
            }
        } else if (id == R.id.btn_sure) {
            if (null != dialog) {
                dialog.dismiss();
            }
            chooseDateInterface.sure(newDateArray);
        }
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        int one = 1;
        int id = picker.getId();
        if (id == R.id.number_picker_year) {
            newDateArray[0] = String.valueOf(newVal + 2000);
            int ts1 = getNumberOfDays(Integer.parseInt(newDateArray[0]), Integer.parseInt(newDateArray[1]));
            numberPickerDay.setMaxValue(ts1 - 1);
            newDateArray[2] = String.valueOf(numberPickerDay.getValue() + 1);
            if (newDateArray[2].length() == one) {
                newDateArray[2] = "0" + newDateArray[2];
            }
        } else if (id == R.id.number_picker_month) {
            newDateArray[1] = String.valueOf(newVal + 1);
            if (newDateArray[1].length() == one) {
                newDateArray[1] = "0" + newDateArray[1];
            }
            int ts = getNumberOfDays(Integer.parseInt(newDateArray[0]), Integer.parseInt(newDateArray[1]));
            numberPickerDay.setMaxValue(ts - 1);
            newDateArray[2] = String.valueOf(numberPickerDay.getValue() + 1);
            if (newDateArray[2].length() == one) {
                newDateArray[2] = "0" + newDateArray[2];
            }
        } else if (id == R.id.number_picker_month) {
            newDateArray[2] = String.valueOf(newVal + 1);
            if (newDateArray[2].length() == one) {
                newDateArray[2] = "0" + newDateArray[2];
            }
        }
    }

    /**
     * 接口回调
     */
    public interface ChooseDateInterface {
        /**
         * 返回选择日期的字符串数组
         *
         * @param newDateArray newDateArray
         */
        void sure(String[] newDateArray);
    }

}
