package com.chenyacheng.androidutils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.chenyacheng.SlideChangeActivity;
import com.chenyacheng.animation.AnimationActivity;
import com.chenyacheng.popdialog.PopAndDialogActivity;
import com.chenyacheng.tablayout.TabLayoutActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void snackBar(View v) {
        startActivity(new Intent(MainActivity.this, SnackBarActivity.class));
    }

    public void popAndDialog(View v) {
        startActivity(new Intent(MainActivity.this, PopAndDialogActivity.class));
    }

    public void topBarSlideChange(View v) {
        startActivity(new Intent(MainActivity.this, SlideChangeActivity.class));
    }

    public void tabLayout(View v) {
        startActivity(new Intent(MainActivity.this, TabLayoutActivity.class));
    }

    public void animation(View v) {
        startActivity(new Intent(MainActivity.this, AnimationActivity.class));
    }

    public void countDown(View v) {
        startActivity(new Intent(MainActivity.this, CountDownActivity.class));
    }
}
