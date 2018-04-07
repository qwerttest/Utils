package com.wj.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * 正则工具类
 * Created by 13932 on 2018/3/12.
 */

public class RegularUtils {
    private static final String CHECK_MOBILE_NUM_PATTERN = "^1(3[0-9]|4[0-9]|5[0-9]|7[0-9]|8[0-9])\\d{8}$";
    private static final String CHECK_BANK_CARD_NUM = "^(\\d{16}|\\d{19})$";
    private static final String CHECK_CARD_PATTERN = "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65)[0-9]{4})" +
            "(([1|2][0-9]{3}[0|1][0-9][0-3][0-9][0-9]{3}" +
            "[Xx0-9])|([0-9]{2}[0|1][0-9][0-3][0-9][0-9]{3}))";

    /**
     * 匹配是否符合正则表达式pattern 匹配返回true
     *
     * @param pattern 匹配模式
     * @return boolean
     */
    public static boolean pattern(String checkString, String pattern) {
        if (null == checkString || checkString.trim().length() <= 0){
            return false;
        }
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(checkString);
        return m.matches();
    }

    /*判断是否是身份证号*/
    public static boolean isIdentityCard(String num) {
        if (TextUtils.isEmpty(num)){
            return false;
        }
        return pattern(num, CHECK_CARD_PATTERN);
    }

    /*判断是否是银行卡号*/
    public static boolean isBankCard(String num) {
        if (TextUtils.isEmpty(num)) {
            return false;
        }
        return pattern(num, CHECK_BANK_CARD_NUM);
    }

    /*判断字符串是否是一个电话号码*/
    public static boolean isPhoneNO(String mobiles) {
        if (TextUtils.isEmpty(mobiles)){
            return false;
        }
        return pattern(mobiles, CHECK_MOBILE_NUM_PATTERN);
    }
}
