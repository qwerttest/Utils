package com.wj.base.util;


import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

public class ResourceUtils {

    public static int getColor(Context context, int id) {
        try {
            return context.getResources().getColor(id);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return 0x0000;
    }

    public static int getDimen(Context context, int id) {
        try {
            return (int) context.getResources().getDimension(id);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Drawable getDrawable(Context context, int id, int def) {
        try {
            return context.getResources().getDrawable(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return context.getResources().getDrawable(def);
    }

    public static String getString(Context context, int id) {
        return getString(context, id, "");
    }

    public static String getString(Context context, int id, String def) {
        try {
            return context.getResources().getString(id);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return def;
    }

    public static int getInt(Context context, int id) {
        return getInt(context, id, -1);
    }

    public static int getInt(Context context, int id, int def) {
        try {
            return context.getResources().getInteger(id);
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
    public static int getIdentifier(Context context, String defType, String name){
        int result = -1;
        try {
            result = context.getResources().getIdentifier(name, defType, context.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            result = -1;
        }
        return result;
    }

    public static int getIdentifierDrawable(Context context, String name){
        return getIdentifier(context, "drawable", name);
    }

    public static int getIdentifierString(Context context, String name){
        return getIdentifier(context, "string", name);
    }

    public static int getIdentifierColor(Context context, String name){
        return getIdentifier(context, "color", name);
    }

    public static int getIdentifierDimen(Context context, String name){
        return getIdentifier(context, "dimen", name);
    }

    /**
     * 根据资源id获取资源路径
     * @param resId 资源id，比如R.drawable.ic_launcher，R.raw.test
     * @param def 可以指定缺省路径
     * */
    public static String getResourcePathById(Context context, int resId, String def){
        try {
            return "android.resource://" + context.getPackageName() + "/" + resId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public static String getResourcePathById(Context context, int resId){
        return getResourcePathById(context, resId, "");
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
