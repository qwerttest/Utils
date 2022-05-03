package com.wj.base.utils.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wj.base.util.ApplicationInfoUtils;

/**
 * Created by 13932 on 2018/3/13.
 */

public class ApplicationInfoUtilsActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicationinfo);
    }

    public void packageName(View view) {
        showToast(ApplicationInfoUtils.packageName());
    }

    public void versionCode(View view) {
        showToast(ApplicationInfoUtils.versionCode()+"");
    }

    public void versionName(View view) {
        showToast(ApplicationInfoUtils.versionName());
    }

    public void isAppExist(View view) {
        showToast(ApplicationInfoUtils.isAppExist(this)+"");
    }

    public void isRunningForeground(View view) {
        showToast(ApplicationInfoUtils.isRunningForeground(this)+"");
    }

    public void androidApplicationInfo(View view) {
        showToast(ApplicationInfoUtils.androidApplicationInfo(this, 0).toString());
    }

    public void androidProcessInfo(View view) {
        showToast(ApplicationInfoUtils.androidProcessInfo(this, ApplicationInfoUtils.pid()).toString());
    }

    public void androidPackageInfo(View view) {
        showToast(ApplicationInfoUtils.androidPackageInfo(this, 0).toString());
    }

    public void isDebug(View view) {
        showToast(ApplicationInfoUtils.isDebug(this)+"");
    }

    public void pid(View view) {
        showToast(ApplicationInfoUtils.pid()+"");
    }

    public void appName(View view) {
        showToast(ApplicationInfoUtils.appName()+"");
    }
}
