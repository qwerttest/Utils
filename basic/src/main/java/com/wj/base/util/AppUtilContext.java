package com.wj.base.util;

import android.content.Context;

/**
 * Des
 *
 * @author WangJian on 2022/5/3 18
 */
public class AppUtilContext {
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static Context getContext() {
        return mContext;
    }
}
