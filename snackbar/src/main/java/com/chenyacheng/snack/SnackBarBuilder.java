package com.chenyacheng.snack;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.PopupWindow;

import com.chenyacheng.snackbar.R;

/**
 * 使用SnackBar入口之一，另一个入口是SnackBarHelper
 *
 * @author chenyacheng
 * @date 2019/07/12
 */
public final class SnackBarBuilder {

    private SnackBar snackBar = new SnackBar();

    private SnackBarBuilder() {
    }

    public static SnackBarBuilder getInstance() {
        return Holder.INSTANCE;
    }

    public void builderShort(Activity activity, CharSequence text) {
        builder(activity.getWindow().getDecorView().findViewById(android.R.id.content).getRootView(), R.layout.snack_bar, 0, text, SnackBar.LENGTH_SHORT);
    }

    public void builderLong(Activity activity, CharSequence text) {
        builder(activity.getWindow().getDecorView().findViewById(android.R.id.content).getRootView(), R.layout.snack_bar, 0, text, SnackBar.LENGTH_LONG);
    }

    public void builderShort(View view, CharSequence text) {
        builder(view, R.layout.snack_bar, 0, text, SnackBar.LENGTH_SHORT);
    }

    public void builderLong(View view, CharSequence text) {
        builder(view, R.layout.snack_bar, 0, text, SnackBar.LENGTH_LONG);
    }

    public void builderShort(Dialog dialog, CharSequence text) {
        if (null != dialog.getWindow()) {
            builder(dialog.getWindow().getDecorView(), R.layout.snack_bar, 0, text, SnackBar.LENGTH_SHORT);
        }
    }

    public void builderLong(Dialog dialog, CharSequence text) {
        if (null != dialog.getWindow()) {
            builder(dialog.getWindow().getDecorView(), R.layout.snack_bar, 0, text, SnackBar.LENGTH_LONG);
        }
    }

    public void builderShort(PopupWindow popupWindow, CharSequence text) {
        if (null != popupWindow.getContentView()) {
            builder(popupWindow.getContentView(), R.layout.snack_bar, 0, text, SnackBar.LENGTH_SHORT);
        }
    }

    public void builderLong(PopupWindow popupWindow, CharSequence text) {
        if (null != popupWindow.getContentView()) {
            builder(popupWindow.getContentView(), R.layout.snack_bar, 0, text, SnackBar.LENGTH_LONG);
        }
    }

    public void builder(View view, int layout, int resId, CharSequence text, int duration) {
        snackBar.make(view, layout, resId, text, duration);
        snackBar.show();
    }

    public void builder(Activity activity, int layout, int resId, CharSequence text, int duration) {
        builder(activity.getWindow().getDecorView().findViewById(android.R.id.content).getRootView(), layout, resId, text, duration);
    }

    public void builder(Dialog dialog, int layout, int resId, CharSequence text, int duration) {
        if (null != dialog.getWindow()) {
            builder(dialog.getWindow().getDecorView(), layout, resId, text, duration);
        }
    }

    public void builder(PopupWindow popupWindow, int layout, int resId, CharSequence text, int duration) {
        if (null != popupWindow.getContentView()) {
            builder(popupWindow.getContentView(), layout, resId, text, duration);
        }
    }

    public void hideView() {
        if (null != snackBar) {
            snackBar.hideView();
        }
    }

    /**
     * 外部类初始化的时候不会初始化该内部类
     * 只有当调用getInstance方法时候才会初始化
     */
    private static class Holder {
        private static final SnackBarBuilder INSTANCE = new SnackBarBuilder();
    }
}
