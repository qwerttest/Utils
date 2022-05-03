package com.wj.base.util;


import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

public class ResourceUtils {

    public static int getColor(int id) {
        try {
            return AppUtilContext.getContext().getResources().getColor(id);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return 0x0000;
    }

    public static int getDimen(Context context, int id) {
        try {
            return (int) AppUtilContext.getContext().getResources().getDimension(id);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Drawable getDrawable(int id, int def) {
        try {
            return AppUtilContext.getContext().getResources().getDrawable(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AppUtilContext.getContext().getResources().getDrawable(def);
    }

    public static String getString(int id) {
        return getString(id, "");
    }

    public static String getString(int id, String def) {
        try {
            return AppUtilContext.getContext().getResources().getString(id);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return def;
    }

    public static int getInt(int id) {
        return getInt(id, -1);
    }

    public static int getInt(int id, int def) {
        try {
            return AppUtilContext.getContext().getResources().getInteger(id);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return def;
    }

    /**
     * 动态获取resourceId
     *
     * @param defType 要获取的资源类型：drawable,string,dimen,color等
     *
     * */
    public static int getIdentifier(String defType, String name){
        int result = -1;
        try {
            result = AppUtilContext.getContext().getResources().getIdentifier(name, defType, AppUtilContext.getContext().getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            result = -1;
        }
        return result;
    }

    public static int getIdentifierDrawable(String name){
        return getIdentifier("drawable", name);
    }

    public static int getIdentifierString(String name){
        return getIdentifier("string", name);
    }

    public static int getIdentifierColor(String name){
        return getIdentifier("color", name);
    }

    public static int getIdentifierDimen(String name){
        return getIdentifier("dimen", name);
    }

    /**
     * 根据资源id获取资源路径
     * @param resId 资源id，比如R.drawable.ic_launcher，R.raw.test
     * @param def 可以指定缺省路径
     * */
    public static String getResourcePathById(int resId, String def){
        try {
            return "android.resource://" + AppUtilContext.getContext().getPackageName() + "/" + resId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public static String getResourcePathById(int resId){
        return getResourcePathById(resId, "");
    }

    public static int string2Color(String colorStr){
        return string2Color(colorStr, android.R.color.transparent);
    }

    /**
     * @param colorStr 如BB4C3B
     * @param defColor 默认颜色
     *   返回的是颜色值
     * */
    public static int string2Color(String colorStr, int defColor){
        try {
            String infoOfBg = "#" + colorStr;
            return Color.parseColor(infoOfBg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defColor;
    }
}
