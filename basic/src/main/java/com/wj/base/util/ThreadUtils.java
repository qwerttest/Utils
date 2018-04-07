package com.wj.base.util;

import android.os.Looper;

/**
 * Created by 13932 on 2018/3/12.
 */

public class ThreadUtils {

    public static final boolean threadSleep(int sleepInMs) {
        try {
            Thread.sleep(sleepInMs);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static final boolean threadJoin(Thread thread) {
        try {
            thread.join();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*判断当前线程是否是主线程*/
    public static final boolean threadInMain() {
        if (Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId()){
            return true;
        }
        return false;
    }
}
