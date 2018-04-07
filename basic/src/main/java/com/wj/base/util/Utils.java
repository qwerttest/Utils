package com.wj.base.util;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.File;

@SuppressLint({"UseSparseArrays", "SimpleDateFormat"})
/**
 * 工具类
 * */
public final class Utils {

    public static final String dumpBundle(Bundle bundle) {
        StringBuffer sb = new StringBuffer('{');
        for (String key : bundle.keySet()) {
            if (sb.length() > 1) sb.append(',');
            sb.append(key).append('=').append(bundle.get(key).toString());
        }
        sb.append('}');
        return sb.toString();
    }

    public static final String androidExternalHome(Context context) {
        File desDir = context.getExternalFilesDir(null);
        if (desDir == null) desDir = context.getFilesDir();
        if (desDir == null) return null;
        desDir = desDir.getParentFile();
        if (desDir == null) return null;
        return desDir.getAbsolutePath();
    }

    public static final LinearLayout.LayoutParams paramsLinear(int width, int height) {
        return paramsLinear(width, height, 0);
    }

    public static RelativeLayout.LayoutParams paramsRelative(int width, int height, int verb) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        params.addRule(verb);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        return params;
    }

    public static final RelativeLayout.LayoutParams paramsRelative(int width, int height, int verb0, int verb1) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        params.addRule(verb0);
        params.addRule(verb1);
        return params;
    }

    public static final RelativeLayout.LayoutParams paramsRelativeAnchor(int width, int height, int[][] anchorVerbs) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
        for (int[] anchorVerb : anchorVerbs) {
            int anchor = anchorVerb[0];
            for (int i = 1; i < anchorVerb.length; i++)
                params.addRule(anchorVerb[i], anchor);
        }
        return params;
    }

    public static final LinearLayout.LayoutParams paramsLinear(int width, int height, float weight) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        params.weight = weight;
        return params;
    }

    public static final LinearLayout.LayoutParams paramsLinear(int width, int height, float weight, int gravity) {
        LinearLayout.LayoutParams params = paramsLinear(width, height, weight);
        params.gravity = gravity;
        return params;
    }

    public static final FrameLayout.LayoutParams paramsFrame(int width, int height, int x, int y) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.leftMargin = x;
        params.topMargin = y;
        return params;
    }

    public static final ViewGroup.LayoutParams paramsGroup(int width, int height) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, height);
        return params;
    }

    public static final FrameLayout.LayoutParams paramsFrame(int width, int height) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        return params;
    }

    public static final FrameLayout.LayoutParams paramsFrame(int width, int height, int gravity) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        params.gravity = gravity;
        return params;
    }

    public static final void notificationSend(Context context, int id, int iconRes, String title, boolean autoCancel, boolean sound, String text, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(iconRes);
        builder.setContentTitle(title);
        builder.setAutoCancel(autoCancel);
        builder.setContentText(text);
        if (sound) builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        if (intent != null) {
            PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentIntent(pi);
        }
        Notification notification = builder.build();
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(id, notification);
    }

    public static final void notificationSend(Context context, int id, int iconRes, String title, boolean autoCancel, boolean sound, int total, int progress, boolean indetermined, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(iconRes);
        builder.setContentTitle(title);
        builder.setAutoCancel(autoCancel);
        builder.setProgress(total, progress, indetermined);
        if (sound) builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        if (intent != null) {
            PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentIntent(pi);
        }
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(id, builder.build());
    }

    public static final void notificationCancel(Context context, int id) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
    }

    /*类型强转*/
    public static <T> T cast(Object obj, Class<T> type) {
        if (type.isInstance(obj) == false) {
            return null;
        }
        return type.cast(obj);
    }

    /**
     * 判断点击是否在某个view内
     * */
    public static boolean inRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if (ev.getX() < x || ev.getX() > (x + view.getWidth()) || ev.getY() < y || ev.getY() > (y + view.getHeight())) {
            return false;
        }
        return true;
    }
}
