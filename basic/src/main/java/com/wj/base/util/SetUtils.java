package com.wj.base.util;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 13932 on 2018/3/12.
 */

public class SetUtils {

    /*判断list是否为空*/
    public static <T> boolean isEmpty(List<T> list) {
        if (null != list && list.size() > 0) {
            return false;
        }
        return true;
    }

    /**
     * @param list       数据最终会合并到list中．
     * @param list2      　将list2中的数据合并到list中．
     * @param comparator 对标．
     * @param <T>
     */
    public static <T> void margeList(List<T> list, List<T> list2, Comparator<T> comparator) {
        if (list.size() > 0) {
            for (int i = 0, j = list2.size(); i < j; i++) {
                insertList(list, list2.get(i), comparator);
            }
        }
    }

    /**
     * 根据特定规则进行数据替换
     * @param list
     * @param t
     * @param comparable
     * @param <T>
     */
    public static <T> void insertList(List<T> list, T t, Comparator<T> comparable) {
        if (list.size() == 0) {
            list.add(t);
            return;
        }
        for (T x : list) {
            int compare = comparable.compare(x, t);
            if (compare == 0) {
                list.remove(x);
                list.add(t);
                return;
            }
        }
        list.add(t);
    }

    /*list反射，头尾颠倒*/
    public static final <T> void listReverse(List<T> list) {
        for (int i = 0, j = list.size() - 1; i < j; i++, j--) {
            T ti = list.get(i);
            T tj = list.get(j);
            list.set(i, tj);
            list.set(j, ti);
        }
    }

    /*获取特定对象在list中的下标*/
    public static final <T> int listContainsAt(List<T> list, T val) {
        for (int i = 0; i < list.size(); i++) {
            T t = list.get(i);
            if (t.equals(val)){
                return i;
            }
        }
        return -1;
    }

    /*往一个list添加元素*/
    public static final <T> ArrayList<T> listWithValues(@SuppressWarnings("unchecked") T... vals) {
        ArrayList<T> res = new ArrayList<T>();
        for (T val : vals) {
            res.add(val);
        }
        return res;
    }

    /*复制一个list*/
    public static final <T> List<T> listDuplicate(List<T> list) {
        List<T> res = new ArrayList<T>(list.size());
        res.addAll(list);
        return res;
    }

    /*list大小补全和裁剪*/
    public static final <T> void listAssertSize(List<T> list, int assertSize, Class<T> cls) throws RuntimeException {
        int listSize = list.size();
        if (listSize > assertSize) {
            for (int i = assertSize; i < listSize; i++){
                list.remove(list.size() - 1);
            }
        } else if (listSize < assertSize) {
            for (int i = listSize; i < assertSize; i++) {
                try {
                    T t = cls.newInstance();
                    list.add(t);
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
    }

    /*Map转list*/
    public static final <K, V> List<Pair<K, V>> mapToPairList(HashMap<K, V> map) {
        List<Pair<K, V>> list = new ArrayList<>();
        Iterator<K> it = map.keySet().iterator();
        while (it.hasNext()) {
            K key = it.next();
            V value = map.get(key);
            Pair<K, V> pair = new Pair<>(key, value);
            list.add(pair);
        }
        return list;
    }
}
