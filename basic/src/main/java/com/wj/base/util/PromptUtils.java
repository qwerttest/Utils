package com.wj.base.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by 13932 on 2018/3/12.
 */

public class PromptUtils {

    /*启动短信息 不指定联联系人传null*/
    public static final void promptSms(Context context, String receiver, String content) {
        Uri uri = Uri.parse("smsto:" + (TextUtils.isEmpty(receiver) ? "" : receiver));
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", content);
        context.startActivity(intent);
    }

    /*启动外部浏览器*/
    public static final void promptExternalBrowser(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);
    }

    /*调用打电话界面，但不主动拨出电话*/
    public static final void prompCall(Context context, String phoneNum) {
        Uri uri = Uri.parse("tel:" + phoneNum);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
