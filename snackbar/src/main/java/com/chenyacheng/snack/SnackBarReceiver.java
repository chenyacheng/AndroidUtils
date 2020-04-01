package com.chenyacheng.snack;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.chenyacheng.snackbar.R;

import java.lang.ref.WeakReference;

/**
 * SnackBar广播
 *
 * @author chenyacheng
 * @date 2019/07/12
 */
public class SnackBarReceiver extends BroadcastReceiver {

    private WeakReference<Activity> activityWeakReference;

    public SnackBarReceiver(Activity activity) {
        activityWeakReference = new WeakReference<>(activity);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String className = activityWeakReference.get().getClass().getSimpleName();
        String content = intent.getStringExtra(className);
        SnackBarBuilder.getInstance().builder(activityWeakReference.get(), R.layout.snack_bar, 0, content, SnackBar.LENGTH_SHORT);
        // 注销广播
        context.unregisterReceiver(this);
    }
}
