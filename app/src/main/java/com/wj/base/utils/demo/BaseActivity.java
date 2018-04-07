package com.wj.base.utils.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by 13932 on 2018/3/13.
 */

public abstract class BaseActivity extends AppCompatActivity{
    public void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int msg){
        Toast.makeText(this, msg+"", Toast.LENGTH_SHORT).show();
    }

    public void showToast(float msg){
        Toast.makeText(this, msg+"", Toast.LENGTH_SHORT).show();
    }

    public void showToast(boolean msg){
        Toast.makeText(this, msg+"", Toast.LENGTH_SHORT).show();
    }

    public void startActivity(Class cls){
        startActivity(new Intent(this, cls));
    }
}
