package com.wj.base.utils.demo;

import android.os.Bundle;
import android.view.View;

import com.wj.base.util.ResourceUtils;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startAppInfo(View view) {
        startActivity(ApplicationInfoUtilsActivity.class);
    }

    public void CookiesUtils(View view) {
        startActivity(CookiesUtilsActivity.class);
    }

    public void DensityUtils(View view) {
        startActivity(DensityUtilsActivity.class);
    }

    public void DeviceUtils(View view) {
        startActivity(DeviceUtilsActivity.class);
    }

    public void KeyBoardUtils(View view) {
        startActivity(KeyBoardUtilsActivity.class);
    }

    public void PromptUtils(View view) {
        startActivity(PrompUtilsActivity.class);
    }

    public void RegularUtils(View view) {
        startActivity(RegularUtilsActivity.class);
    }

    public void ResouceUtils(View view) {
        startActivity(ResourceUtilsActivity.class);
    }

    public void String2Color(View view) {
        view.setBackgroundColor(ResourceUtils.string2Color("你是一个"));
    }
}
