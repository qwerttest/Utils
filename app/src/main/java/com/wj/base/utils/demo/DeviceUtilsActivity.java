package com.wj.base.utils.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wj.base.util.DeviceUtils;
import com.wj.base.util.ThreadUtils;

/**
 * Created by 13932 on 2018/3/13.
 */

public class DeviceUtilsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
    }

    public void isScreenLocked(View view) {
        showToast(DeviceUtils.isScreenLocked(this));
        new Thread(new Runnable() {
            @Override
            public void run() {
                ThreadUtils.threadSleep(4000);
                System.out.println("isScreenLocked " + DeviceUtils.isScreenLocked(DeviceUtilsActivity.this));
            }
        }).start();
    }

    public void phoneName(View view) {
        showToast(DeviceUtils.phoneName());
    }

    public void getDeviceName(View view) {
        showToast(DeviceUtils.getDeviceName());
    }

    public void getAndroidSDKVersion(View view) {
        showToast(DeviceUtils.getAndroidSDKVersion());
    }

    public void getDeviceMac(View view) {
        showToast(DeviceUtils.getDeviceMac(this));
    }

    public void appInstallationId(View view) {
        showToast(DeviceUtils.appInstallationId(this));
    }

    public void getUUID(View view) {
        showToast(DeviceUtils.getUUID(this));
    }

    public void imei(View view) {
        showToast(DeviceUtils.imei(this));
    }
}
