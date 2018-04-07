package com.wj.base.util;

import java.util.Random;

/**
 * Created by 13932 on 2018/3/12.
 */

public class RandomUtils {

    public static final int random(int maxExclude, long seed) {
        Random rand = new Random(seed);
        return rand.nextInt(maxExclude);
    }

    public static final String randomText(int minLength, int maxLength, String tokens) {
        StringBuffer sb = new StringBuffer();
        Random rand = new Random(System.currentTimeMillis());
        int len = minLength + rand.nextInt(maxLength - minLength);
        int tokenLen = tokens.length();
        for (int i = 0; i < len; i++) {
            char ch = tokens.charAt(rand.nextInt(tokenLen));
            sb.append(ch);
        }
        return sb.toString();
    }

    public static final int randomInt(int[] source, int[] weights) {
        int total = 0;
        for (int weight : weights){
            total += weight;
        }
        int rand = random(total, System.currentTimeMillis());
        int acc = 0;
        for (int i = 0; i < weights.length; i++) {
            acc += weights[i];
            if (acc > rand){
                return source[i];
            }
        }
        return source[weights.length - 1];
    }

    /*从区间中随机获取一个数*/
    public static final int getInt(int min, int max) {
        return (int) (min + Math.random() * (max - min + 1));
    }
}
