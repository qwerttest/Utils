package com.wj.base.utils.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wj.base.util.CookiesUtils;

/**
 * Created by 13932 on 2018/3/13.
 */

public class CookiesUtilsActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookies);
    }


    public void setCookies(View view) {
        CookiesUtils.setCookies(this, "aaaa", "bbbb=1","cccc=2");
    }

    public void getCookie(View view) {
        showToast(CookiesUtils.getCookies(this, "aaaa"));
    }

    public void removeCookies(View view) {
        CookiesUtils.removeCookies(this);
    }
}
