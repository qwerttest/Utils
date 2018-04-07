package com.wj.base.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * Created by 13932 on 2018/3/12.
 */

public class ReflexUtils {

    public static final <T> T classNew(String className) {
        try {
            Class<?> cls = Class.forName(className);
            @SuppressWarnings("unchecked")
            T t = (T) cls.newInstance();
            return t;
        } catch (Exception e) {
            return null;
        }
    }

    public static final <T> T classNew(Class<T> cls) {
        try {
            T t = cls.newInstance();
            return t;
        } catch (Exception e) {
            return null;
        }
    }

    public static final <F> F getPrivateFieldValue(Object obj, String propertyName) {
        try {
            Class clazz = obj.getClass();
            Field field = clazz.getDeclaredField(propertyName);
            field.setAccessible(true);
            Object result = field.get(obj);
            return (F) result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setNotAccessibleProperty(Object obj, String propertyName, Object value) {
        try {
            Class<?> clazz = obj.getClass();
            Field field = clazz.getDeclaredField(propertyName);
            //赋值前将该成员变量的访问权限打开
            field.setAccessible(true);
            field.set(obj, value);
            //赋值后将该成员变量的访问权限关闭
            field.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void callMethod(Object obj, String methodName) {
        try {
            Class<?> clazz = obj.getClass();
            Method method = clazz.getDeclaredMethod(methodName);
            method.setAccessible(true);
            method.invoke(obj);
            method.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
