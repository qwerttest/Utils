package com.wj.base.utils.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.wj.base.util.RegularUtils;

/**
 * Created by 13932 on 2018/3/13.
 */

public class RegularUtilsActivity extends BaseActivity{
    private EditText mEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular);
        mEdit = (EditText) findViewById(R.id.edit);

    }

    public void isIdentityCard(View view) {
        showToast(RegularUtils.isIdentityCard(mEdit.getText().toString()));
    }

    public void isBankCard(View view) {
        showToast(RegularUtils.isBankCard(mEdit.getText().toString()));
    }

    public void isPhoneNO(View view) {
        showToast(RegularUtils.isPhoneNO(mEdit.getText().toString()));
    }
}
