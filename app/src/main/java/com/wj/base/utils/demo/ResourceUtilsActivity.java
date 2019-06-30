package com.wj.base.utils.demo;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.TextView;

import com.wj.base.util.ResourceUtils;

public class ResourceUtilsActivity extends Activity {

    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);
        tv = findViewById(R.id.tv);
    }

    public void gainDrawable(View view) {
        tv.setBackgroundResource(ResourceUtils.getIdentifierDrawable(this, "ic_launcher"));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void gainColor(View view) {
        tv.setTextColor(getColor(ResourceUtils.getIdentifierColor(this, "colorAccent")));
    }

    public void gainString(View view) {
        tv.setText(ResourceUtils.getIdentifierString(this, "app_name"));
    }

    public void gainDrawablePath(View view) {
        tv.setText(ResourceUtils.getResourcePathById(this, R.drawable.ic_launcher));
    }

    public void gainDimen(View view) {
        tv.setTextSize(getResources().getDimension(ResourceUtils.getIdentifierDimen(this, "text_size")));
    }
}
