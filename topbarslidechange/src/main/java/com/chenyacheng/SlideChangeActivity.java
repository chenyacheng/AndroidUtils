package com.chenyacheng;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;

public class SlideChangeActivity extends AppCompatActivity implements CustomScrollView.TranslucentListener {

    private HeadToolBar headToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_change);

        CustomScrollView customScrollView = this.findViewById(R.id.scroll_view_home_house_details);
        customScrollView.setTranslucentListener(this);

        headToolBar = findViewById(R.id.head_toolbar);
        headToolBar.getBackground().setAlpha(0);
        headToolBar.setMiddleTitleColor(ContextCompat.getColor(this, R.color.common_ff333333));
        headToolBar.setLeftDrawable(R.drawable.white_shadow_back_arrow_icon);
        headToolBar.setLeftDrawableClickListener(v -> finish());
        headToolBar.setRightDrawable(R.drawable.home_white_shadow_share_icon);

    }

    @Override
    public void onTranslucent(float alpha) {
        double var = 0.5;
        int i = Float.valueOf(alpha * 255).intValue();
        if (alpha > var) {
            headToolBar.setMiddleTitle("显示标题");
            headToolBar.setLeftDrawable(R.drawable.gray_back_arrow_icon);
            headToolBar.setRightDrawable(R.drawable.home_gray_share_icon);
        } else {
            headToolBar.setMiddleTitle("");
            headToolBar.setLeftDrawable(R.drawable.white_shadow_back_arrow_icon);
            headToolBar.setRightDrawable(R.drawable.home_white_shadow_share_icon);
        }
        headToolBar.getBackground().setAlpha(i);
    }
}
