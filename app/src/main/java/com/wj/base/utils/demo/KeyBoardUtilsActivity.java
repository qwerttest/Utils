package com.wj.base.utils.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wj.base.util.KeyBoardUtils;

/**
 * Created by 13932 on 2018/3/13.
 */

public class KeyBoardUtilsActivity extends BaseActivity{
    private View mEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);
        mEdit = findViewById(R.id.edit);
    }

    public void showKeyboard(View view) {
        KeyBoardUtils.showKeyboard(this, mEdit);
    }

    public void hideKeyboard(View view) {
        KeyBoardUtils.hideKeyboard(this);
    }

    public void hideKeyboardView(View view) {
        KeyBoardUtils.hideKeyboard(view);
    }

    public void hideKeyboardEditView(View view) {
        KeyBoardUtils.hideKeyboard(mEdit);
    }

    public void showKeyboardView(View view) {
        KeyBoardUtils.showKeyboard(this, view);
    }
}
