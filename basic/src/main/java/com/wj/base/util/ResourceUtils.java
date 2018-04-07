package com.wj.base.util;


import android.content.Context;
import android.content.res.Resources.NotFoundException;
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
}
