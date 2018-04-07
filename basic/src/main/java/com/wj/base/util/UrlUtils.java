package com.wj.base.util;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by 13932 on 2018/3/5.
 */
public class UrlUtils {

    /*url拼装*/
    public static final void urlAddParam(StringBuffer url, String name, String value) {
        urlAddParam(url, name, value, true);
    }

    /*@param needEncode  是否需要utf-8 Encode*/
    public static final void urlAddParam(StringBuffer url, String name, String value, boolean needEncode) {
        if (url.indexOf("?") >= 0) url.append('&');
        else url.append('?');
        url.append(stringUrlEncode(name, "UTF-8"));
        url.append('=');
        url.append(needEncode ? stringUrlEncode(value, "UTF-8") : value);
    }

    public static final String stringUrlEncode(String string, String encoding) {
        try {
            return URLEncoder.encode(string, encoding);
        } catch (Exception e) {
            return null;
        }
    }

    public static final String stringUrlDecode(String string, String encoding) {
        try {
            return URLDecoder.decode(string, encoding);
        } catch (Exception e) {
            return null;
        }
    }
}
