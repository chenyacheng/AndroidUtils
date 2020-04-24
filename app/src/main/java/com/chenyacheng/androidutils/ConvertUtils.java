package com.chenyacheng.androidutils;

import android.content.Context;

/**
 * @author chenyacheng
 * @date 2020/04/21
 */
public class ConvertUtils {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dpToPx(Context context, int dps) {
        return Math.round(context.getApplicationContext().getResources().getDisplayMetrics().density * dps);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp==dip
     */
    public static int pxToDp(Context context, int dps) {
        return Math.round(dps / context.getApplicationContext().getResources().getDisplayMetrics().density);
    }
}
