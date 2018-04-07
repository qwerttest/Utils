package com.wj.base.util;

import android.content.Context;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

/**
 * Created by 13932 on 2018/3/12.
 */

public class CookiesUtils {
    /**
     * 设置Cookies
     */
    public static void setCookies(Context context, String url, String... cookies) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        for (String cookie : cookies) {
            cookieManager.setCookie(url, cookie);
        }
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.getInstance().sync();
        } else {
            CookieManager.getInstance().flush();
        }
    }

    /**
     * 清除Cookies
     */
    public static void removeCookies(Context context) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeSessionCookie();
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.getInstance().sync();
        } else {
            CookieManager.getInstance().flush();
        }
    }

    /**获取某个url的cookie*/
    public static String getCookies(Context context, String url){
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        return cookieManager.getCookie(url);
    }
}
