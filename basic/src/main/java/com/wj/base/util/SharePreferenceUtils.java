package com.wj.base.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

/**
 * 首选项存储工具类
 * 
 * @author wjian
 * 
 * */
@Deprecated
public final class SharePreferenceUtils {

	/**
	 * @param context
	 * @param key
	 * @param def #default value
	 * @param mode #Context.MODE_
	 * */
	public static boolean getBoolean(Context context, String key, boolean def, int mode)
	{
		try {
			if (canAction(ApplicationInfoUtils.appName(), key))
            {
                SharedPreferences mSettingPreferences = context.getSharedPreferences(ApplicationInfoUtils.appName(), mode);
                return mSettingPreferences.getBoolean(key, def);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return def;
	}
	
	/** 存储Boolean */
	public static void saveBoolean(Context context, String key, boolean info, int mode) {
		try {
			if(canAction(ApplicationInfoUtils.appName(), key)){
                SharedPreferences sp = context.getSharedPreferences(ApplicationInfoUtils.appName(), mode);
                Editor editor = sp.edit();
                editor.putBoolean(key, info);
                editor.commit();
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getString(Context context, String key, String def, int mode)
	{
		try {
			if (canAction(ApplicationInfoUtils.appName(), key))
            {
                SharedPreferences mSettingPreferences = context.getSharedPreferences(ApplicationInfoUtils.appName(), mode);
                return mSettingPreferences.getString(key, def);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return def;
	}
	
	/** 存储String */
	public static void saveString(Context context, String key, String info, int mode)
	{
		try {
			if(canAction(ApplicationInfoUtils.appName(), key))
            {
                SharedPreferences sp = context.getSharedPreferences(ApplicationInfoUtils.appName(), mode);
                Editor editor = sp.edit();
                editor.putString(key, info);
                editor.commit();
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getInt(Context context, String key, int def, int mode)
	{
		try {
			if (canAction(ApplicationInfoUtils.appName(), key))
            {
                SharedPreferences mSettingPreferences = context.getSharedPreferences(ApplicationInfoUtils.appName(), mode);
                return mSettingPreferences.getInt(key, def);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return def;
	}

	/** 存储Int */
	public static void saveInt(Context context, String key, int info, int mode)
	{
		try {
			if(canAction(ApplicationInfoUtils.appName(), key))
            {
                SharedPreferences sp = context.getSharedPreferences(ApplicationInfoUtils.appName(), mode);
                Editor editor = sp.edit();
                editor.putInt(key, info);
                editor.commit();
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 存储long */
	public static void saveLong(Context context, String fileName, String key, long info, int mode)
	{
		try {
			if(canAction(fileName, key))
			{
				SharedPreferences sp = context.getSharedPreferences(fileName, mode);
				Editor editor = sp.edit();
				editor.putLong(key, info);
				editor.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /** 存储long */
    public static void saveLong(Context context, String key, long info, int mode)
    {
        try {
            if(canAction(ApplicationInfoUtils.appName(), key))
            {
                SharedPreferences sp = context.getSharedPreferences(ApplicationInfoUtils.appName(), mode);
                Editor editor = sp.edit();
                editor.putLong(key, info);
                editor.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public static long getLong(Context context, String fileName, String key, long def, int mode)
	{
		try {
			if (canAction(fileName, key))
			{
				SharedPreferences mSettingPreferences = context.getSharedPreferences(fileName, mode);
				return mSettingPreferences.getLong(key, def);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return def;
	}

    public static long getLong(Context context, String key, long def, int mode)
    {
        try {
            if (canAction(ApplicationInfoUtils.appName(), key))
            {
                SharedPreferences mSettingPreferences = context.getSharedPreferences(ApplicationInfoUtils.appName(), mode);
                return mSettingPreferences.getLong(key, def);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

	/** 删除Share中的某一元素*/
	public static void removeKey(Context context, String fileName, String key, int mode)
	{
		try {
			if(canAction(fileName, key))
            {
                SharedPreferences sp = context.getSharedPreferences(fileName, mode);
                Editor editor = sp.edit();
                editor.remove(key);
                editor.commit();
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** 清空Share*/
	public static void clear(Context context, String fileName, int mode)
	{
		try {
			SharedPreferences sp = context.getSharedPreferences(fileName, mode);
			Editor editor = sp.edit();
			editor.clear();
			editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断是否可以继续操作
	 * */
	private static boolean canAction(String fileName, String key)
	{
		if (!TextUtils.isEmpty(fileName) && !TextUtils.isEmpty(key))
			return true;
		return false;
	}
}
