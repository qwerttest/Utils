package com.wj.base.utils.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wj.base.util.DensityUtils;

/**
 * Created by 13932 on 2018/3/13.
 */

public class DensityUtilsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_density);
    }

    public void getTextWidth(View view) {
        showToast(DensityUtils.getTextWidth(this, 17) + "");
    }

    public void getViewDimention(View view) {
        showToast(DensityUtils.getViewDimention(findViewById(R.id.text), DensityUtils.WIDTH) + "");
    }

    public void sp2px(View view) {
        showToast(DensityUtils.sp2px(this, 18) + "    18");
    }

    public void px2sp(View view) {
        showToast(DensityUtils.px2sp(this, 18) + "    18");
    }

    public void px2dip(View view) {
        showToast(DensityUtils.px2dip(this, 18) + "    18");
    }

    public void dip2px(View view) {
        showToast(DensityUtils.dip2px(this, 18) + "    18");
    }

    public void scaledDensity(View view) {
        showToast(DensityUtils.scaledDensity(this));
    }

    public void density(View view) {
        showToast(DensityUtils.density(this));
    }

    public void getStatusBarHeight(View view) {
        showToast(DensityUtils.getStatusBarHeight(this, 20));
    }

    public void getScreenHeight(View view) {
        showToast(DensityUtils.getScreenHeight(this));
    }

    public void getScreenWidth(View view) {
        showToast(DensityUtils.getScreenWidth(this));
    }
}
