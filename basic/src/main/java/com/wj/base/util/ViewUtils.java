package com.wj.base.util;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * 描述：视图工具类
 * created by WangJin on 2020-06-28 14:08.
 */
public class ViewUtils {

    /**
     * 从父 view 中移除自己
     * @param child
     */
    public static void removeSelfFromParent(View child){
        if (child != null){
            ViewGroup parent = (ViewGroup)child.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                parent.removeView(child);
            }
        }
    }

    /***/
    public static void stopScroll(View scrollView){
        if(scrollView != null){
            scrollView.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 0, 0, 0));
        }
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
