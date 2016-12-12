package com.yulin.horizontaltimeline;

import android.content.Context;

/**
 * Created by Administrator on 2016/12/12 0012.
 */

public class DimensUtils {

    public static int dp2px(Context context, float dpValue) {
        return (int)(dpValue * context.getResources().getDisplayMetrics().density + 0.5F);
    }

    public static int px2dp(Context context, float pxValue) {
        return (int)(pxValue / context.getResources().getDisplayMetrics().density + 0.5F);
    }
}
