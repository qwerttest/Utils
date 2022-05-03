package com.wj.base.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Process;

import java.util.List;

/**
 * 只返回了应用基础信息，渠道信息未在此添加
 * Created by wangjian on 2018/3/3.
 */

public class ApplicationInfoUtils {
    private static String mPackageName = "";
    private static int mVersionCode = 0;
    private static String mVersionName = "";
    private static String mAppName = "";

    /**
     * app包名
     * */
    public static final String packageName() {
        if(TextUtils.isEmpty(mPackageName)){
            mPackageName = AppUtilContext.getContext().getPackageName();
        }
        return mPackageName;
    }

    /**
     * app 版本号
     * */
    public static final int versionCode() {
        if(mVersionCode <= 0){
            mVersionCode = androidPackageInfo(AppUtilContext.getContext(), 0).versionCode;
        }
        return mVersionCode;
    }

    /*应用版本名*/
    public static final String versionName() {
        if(TextUtils.isEmpty(mVersionName)){
            mVersionName = androidPackageInfo(AppUtilContext.getContext(), 0).versionName;
        }
        return mVersionName;
    }

    /*应用名称*/
    public static final String appName() {
        if(TextUtils.isEmpty(mAppName)){
            mAppName = AppUtilContext.getContext().getApplicationInfo().loadLabel(AppUtilContext.getContext().getPackageManager()).toString();
        }
        return mAppName;
    }

    /*当前进程id*/
    public static final int pid() {
        return Process.myPid();
    }

    /*是否是Debug包*/
    public static final boolean isDebug(Context context){
        return (androidPackageInfo(context, 0).applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE)!=0;
    }

    /*应用包信息*/
    public static final PackageInfo androidPackageInfo(Context context, int flags) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName(), flags);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packageInfo;
    }

    public static final ActivityManager.RunningAppProcessInfo androidProcessInfo(Context context, int pid) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appInfos = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appInfo : appInfos) {
            if (appInfo.pid == pid) return appInfo;
        }
        return null;
    }

    public static final ApplicationInfo androidApplicationInfo(Context context, int flag) {
        try {
            return context.getPackageManager().getApplicationInfo(packageName(), flag);
        } catch (Exception e) {
            return null;
        }
    }

    //判断应用是否处于前台
    public static boolean isRunningForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
        //添加判空操作.
        if (appProcessInfos == null) {
            return false;
        }
        // 枚举进程
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfos) {
            if (appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                if (appProcessInfo.processName.equals(context.getApplicationInfo().processName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断应用是否存在
     *
     * @return
     */
    public static boolean isAppExist(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = am.getRunningTasks(20);
        int numActivities = 0;
        for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTasks) {

            if (runningTaskInfo.topActivity.getPackageName().contains(context.getPackageName())) {
                numActivities += runningTaskInfo.numActivities;
            }
        }
        return numActivities > 0;
    }

    public static final String getStringValue(Context context, String key){
        return getStringValue(context, key, "");
    }

    public static final String getStringValue(Context context, String key, String def){
        try {
            Bundle metaData = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData;
            if(null != metaData){
                return metaData.getString(key, def);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return def;
    }

    public static final int getIntValue(Context context, String key){
        return getIntValue(context, key, -1);
    }

    public static final int getIntValue(Context context, String key, int def){
        try {
            Bundle metaData = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData;
            if(null != metaData){
                return metaData.getInt(key, def);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return def;
    }
}
