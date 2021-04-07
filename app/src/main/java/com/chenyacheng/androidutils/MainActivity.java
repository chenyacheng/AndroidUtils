package com.chenyacheng.androidutils;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chenyacheng.SlideChangeActivity;
import com.chenyacheng.popdialog.PopAndDialogActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void snackBar(View v){
        startActivity(new Intent(MainActivity.this, SnackBarActivity.class));
    }

    public void popAndDialog(View v){
        startActivity(new Intent(MainActivity.this, PopAndDialogActivity.class));
    }

    public void topBarSlideChange(View v){
        startActivity(new Intent(MainActivity.this, SlideChangeActivity.class));
    }
}
