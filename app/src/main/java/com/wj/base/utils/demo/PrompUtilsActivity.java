package com.wj.base.utils.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wj.base.util.PromptUtils;

/**
 * Created by 13932 on 2018/3/13.
 */

public class PrompUtilsActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promp);
    }

    public void prompCall(View view) {
        PromptUtils.prompCall(this, "18201587456");
    }

    public void promptExternalBrowser(View view) {
        PromptUtils.promptExternalBrowser(this, "http://www.xiu8.com");
    }

    public void promptSms(View view) {
        PromptUtils.promptSms(this, "", "this is a test");
    }
}
