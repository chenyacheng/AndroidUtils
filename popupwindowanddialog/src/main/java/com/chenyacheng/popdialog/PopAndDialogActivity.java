package com.chenyacheng.popdialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PopAndDialogActivity extends AppCompatActivity {

    private TextView tvChoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_and_dialog);

        spinner();
        timeDate();
    }

    private void spinner() {
        tvChoose = findViewById(R.id.tv_choose);
        View spinnerView = View.inflate(this, R.layout.pop_windows, null);
        PopupWindow popupWindow = new PopupWindow(spinnerView, RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
        popupWindow.setOnDismissListener(() -> setTextImage(R.drawable.spinner_pop_down_arrow));
        tvChoose.setOnClickListener(view -> {
            popupWindow.setWidth(tvChoose.getWidth());
            popupWindow.showAsDropDown(tvChoose, 1, 1);
            setTextImage(R.drawable.spinner_pop_up_arrow);
        });
        TextView oneTextView = spinnerView.findViewById(R.id.tv_spinner_order_id);
        oneTextView.setOnClickListener(view -> {
            tvChoose.setText(oneTextView.getText());
            tvChoose.setTextColor(Color.parseColor("#393434"));
            popupWindow.dismiss();
        });
        TextView twoTextView = spinnerView.findViewById(R.id.tv_spinner_property_consultant);
        twoTextView.setOnClickListener(view -> {
            tvChoose.setText(twoTextView.getText());
            tvChoose.setTextColor(Color.parseColor("#393434"));
            popupWindow.dismiss();
        });
        TextView threeTextView = spinnerView.findViewById(R.id.tv_spinner_driver);
        threeTextView.setOnClickListener(view -> {
            tvChoose.setText(threeTextView.getText());
            tvChoose.setTextColor(Color.parseColor("#393434"));
            popupWindow.dismiss();
        });
    }

    /**
     * 给TextView右边设置图片
     *
     * @param resId 图标Id
     */
    private void setTextImage(int resId) {
        Drawable drawable = ContextCompat.getDrawable(this, resId);
        if (null != drawable) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvChoose.setCompoundDrawables(null, null, drawable, null);
        }
    }

    private void timeDate() {
        TextView tvTime = findViewById(R.id.tv_time);
        tvTime.setOnClickListener(view -> {
            final DateNumberPickerDialog dateUtil = new DateNumberPickerDialog();
            String[] oldDateArray;
            String startTime = "选择时间";
            if (startTime.equals(tvTime.getText().toString())) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String str = sdf.format(new Date());
                oldDateArray = str.split("-");
            } else {
                oldDateArray = tvTime.getText().toString().split("-");
            }
            dateUtil.createDialog(this, "请选择时间", oldDateArray, newDateArray -> {
                String date = newDateArray[0] + "-" + newDateArray[1] + "-" + newDateArray[2];
                tvTime.setText(date);
                tvTime.setTextColor(Color.parseColor("#393434"));
            });
        });
    }
}
