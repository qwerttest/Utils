package com.wj.base.util;

import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.Base64;
import android.widget.EditText;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author wjian
 */
public class TextUtils {

    /**判断字符串是否为空，去掉所有空格*/
    public static boolean isEmpty(String str) {
        if (null == str || str.trim().length() == 0){
            return true;
        } else{
            return false;
        }
    }

    /**判断字符串是否为空，去掉所有空格*/
    public static boolean isNotEmpty(String str) {
        return null != str && (str.trim().length() > 0);
    }

    public static final String bytes2String(byte[] bytes) {
        return bytes2String(bytes, "UTF-8");
    }

    /**
     * 字节转String
     * @param encoding 编码格式
     * */
    public static final String bytes2String(byte[] bytes, String encoding) {
        if (null == bytes){
            return null;
        }
        try {
            String string = new String(bytes, encoding);
            return string;
        } catch (Exception e) {
            return null;
        }
    }

    public static final byte[] string2Bytes(String str) {
        return string2Bytes(str, "UTF-8");
    }

    /**
     * String转字节
     * @param encoding 编码格式
     * */
    public static final byte[] string2Bytes(String str, String encoding) {
        try {
            return str.getBytes(encoding);
        } catch (Exception e) {
            return null;
        }
    }

    public static class StringUtils{


        public static final boolean stringToBoolean(String str, boolean def) {
            try {
                return Boolean.parseBoolean(str);
            } catch (Exception e) {
                return def;
            }
        }

        public static final int stringToInt(String str, int def) {
            try {
                return Integer.parseInt(str);
            } catch (Exception e) {
                return def;
            }
        }



        public static final double stringToDouble(String str, double def) {
            try {
                return Double.parseDouble(str);
            } catch (Exception e) {
                return def;
            }
        }

        public static final long stringToLong(String str, long def) {
            try {
                return Long.parseLong(str);
            } catch (Exception e) {
                return def;
            }
        }

        public static final float stringToFloat(String str, float def) {
            try {
                return Float.parseFloat(str);
            } catch (Exception e) {
                return def;
            }
        }

        public static String bytesToBase64String(byte[] bytes, boolean urlSafe) {
            if (isEmpty(bytes)){
                return "";
            }
            return Base64.encodeToString(bytes, (urlSafe ? Base64.URL_SAFE : Base64.DEFAULT));
        }

        public static byte[] base64StringToBytes(String string, boolean urlSafe) {
            return Base64.decode(string, (urlSafe ? Base64.URL_SAFE : Base64.DEFAULT));
        }
    }

    private static final Random sRand = new Random();

    public static final String stringRandomGenerate(char[] tokens, int len) {
        StringBuffer buf = new StringBuffer(len);
        for (int i = 0; i < len; i++)
            buf.append(tokens[sRand.nextInt(tokens.length)]);
        return buf.toString();
    }


    public static final byte[] hexStringToBytes(String string) {
        if (string == null) return null;
        int len = string.length();
        if (len % 2 != 0) return null;
        len = len / 2;
        byte[] res = new byte[len];
        for (int i = 0; i < len; i++) {
            int bt = 0;
            int ch0 = string.charAt(i * 2);
            int ch1 = string.charAt(i * 2 + 1);

            if (ch0 >= '0' && ch0 <= '9') bt += ((ch0 - '0') << 4);
            else if (ch0 >= 'a' && ch0 <= 'f') bt += ((ch0 - 'a' + 10) << 4);
            else if (ch0 >= 'A' && ch0 <= 'F') bt += ((ch0 - 'A' + 10) << 4);
            else return null;

            if (ch1 >= '0' && ch1 <= '9') bt += (ch1 - '0');
            else if (ch1 >= 'a' && ch1 <= 'f') bt += (ch1 - 'a' + 10);
            else if (ch1 >= 'A' && ch1 <= 'F') bt += (ch1 - 'A' + 10);
            else return null;
            res[i] = (byte) bt;
        }
        return res;
    }

    public static final String stringLastToken(String string, char divider) {
        if (string == null) return null;
        int index = string.lastIndexOf(divider);
        return (index < 0 ? string : string.substring(index + 1));
    }

    public static final String stringArrayEncode(String[] strings, char seperator) {
        if (strings == null) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            if (i > 0) sb.append(seperator);
            sb.append(strings[i]);
        }
        return sb.toString();
    }



    public static final String[] stringArrayDecode(String string, char seperator) {
        String[] res = string.split(String.valueOf(seperator));
        if (res == null || (res.length == 1 && res[0].length() == 0)) return new String[0];
        return res;
    }

    public static final String stringListEncode(List<String> strings, char seperator) {
        if (strings == null) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            if (i > 0) sb.append(seperator);
            sb.append(strings.get(i));
        }
        return sb.toString();
    }

    public static final ArrayList<String> stringListDecode(String string, char seperator) {
        ArrayList<String> res = new ArrayList<String>();
        String[] strs = string.split(String.valueOf(seperator));
        if (strs == null || (strs.length == 1 && strs[0].length() == 0)) return res;
        for (String str : strs) res.add(str);
        return res;
    }

    public static final boolean isEmpty(byte[] bytes) {
        if (null == bytes || bytes.length == 0)
            return true;
        else
            return false;
    }

    public static final boolean isEmpty2(String str) {
        if (null == str || str.trim().length() == 0 || "null".equals(str))
            return true;
        else
            return false;
    }

    public static final String stringUnicodeEncode(String String) {
        char aChar;
        int len = String.length();
        StringBuffer outBuffer = new StringBuffer(len);

        for (int x = 0; x < len; ) {
            aChar = String.charAt(x++);
            if (aChar == '\\') {
                aChar = String.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;

                    for (int i = 0; i < 4; i++) {
                        aChar = String.charAt(x++);

                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';

                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }

        return outBuffer.toString();
    }

    public static String stringUnicodeEncode2(String theString) {

        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);

        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '%') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;

                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);

                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    else
                        outBuffer.append('%');

                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }

        return outBuffer.toString();
    }

    public static final String stringUnicodeDecode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);

        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '%') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;

                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);

                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';

                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }

        return outBuffer.toString();
    }

    public static class LengthUtils {
        public static boolean isLetter(char c) {
            int k = 0x80;
            return c / k == 0 ? true : false;
        }

        /**
         * 判断字符串是否为空
         *
         * @param str
         * @return
         */
        public static boolean isNull(String str) {
            if (str == null || str.trim().equals("") || str.trim().equalsIgnoreCase("null")) {
                return true;
            } else {
                return false;
            }
        }

        /**
         * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
         *
         * @param s 需要得到长度的字符串
         * @return int 得到的字符串长度
         */

        public static int length(String s) {
            if (s == null)
                return 0;
            char[] c = s.toCharArray();
            int len = 0;
            for (int i = 0; i < c.length; i++) {
                len++;
                if (!isLetter(c[i])) {
                    len++;
                }
            }
            return len;
        }


        /**
         * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为1,英文字符长度为0.5
         *
         * @param s 需要得到长度的字符串
         * @return int 得到的字符串长度
         */
        public static double getLength(String s) {
            double valueLength = 0;
            String chinese = "[\u4e00-\u9fa5]";
            // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < s.length(); i++) {
                // 获取一个字符
                String temp = s.substring(i, i + 1);
                // 判断是否为中文字符
                if (temp.matches(chinese)) {
                    // 中文字符长度为1
                    valueLength += 1;
                } else {
                    // 其他字符长度为0.5
                    valueLength += 0.5;
                }
            }
            //进位取整
            return Math.ceil(valueLength);
        }

    }

    /**
     * 得到指定长度的短字符串,超过则 末尾显示  ...
     * by zhangchenzhou
     *
     * @param s
     * @param len
     * @return
     */
    public static String getShortStr(String s, int len) {
        double valueLength = 0;
        String tempStr = "";
        String chinese = "[\u4e00-\u9fa5]";
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < s.length(); i++) {
            // 获取一个字符
            if (valueLength >= len) {
                return tempStr + "...";
            }
            String temp = s.substring(i, i + 1);
            tempStr = tempStr + temp;
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                // 中文字符长度为1
                valueLength += 1;
            } else {
                // 其他字符长度为0.5
                valueLength += 0.5;
            }
        }
        //进位取整
        return tempStr;
    }

    /**
     * @param str   源字符串
     * @param count 要截取的个数
     * @return 被截取后的字符串
     */
    public static String getStrWithEllipsis(String str, int count) {
        String strBack = "";
        if (str.length() > count) {
            strBack = str.substring(0, count) + "...";
        } else {
            strBack = str;
        }
        return strBack;
    }

    public interface ContentListener {
        void changeedContent(String text);
    }

    /**
     * 设置EditText,光标位置到最后
     */
    public static void setEditTextCursorLocation(final EditText editText, final ContentListener contentListener) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CharSequence text = editText.getText();
                if (contentListener != null) contentListener.changeedContent(text.toString());
                if (isEmpty(text.toString())) return;
                if (text instanceof Spannable) {
                    Spannable spanText = (Spannable) text;
                    Selection.setSelection(spanText, text.length());
                }
            }
        });

    }

    /**
     * 添加一个字符占一行方法．
     */
    public static String getCharOccupyOneLine(String content) {

        if (isEmpty(content)) {
            return "";
        }
        String split[] = content.split("");
        StringBuffer sb = new StringBuffer();
        sb.append(split[0] + "\n");
        for (int i = 1; i < split.length; i++) {
            sb.append(split[i] + "\n");
        }
        return sb.toString();

    }

    public static long calculateLength(CharSequence c) {
        double len = 0;
        for (int i = 0; i < c.length(); i++) {
            int tmp = (int) c.charAt(i);
            if (tmp > 0 && tmp < 127) {
                len += 0.5;
            } else {
                len++;
            }
        }
        return Math.round(len);
    }

    //用于拼装字符串  added by wangjian
    public static final String strAppend(String base, String... params) {
        StringBuffer sb = new StringBuffer(base);
        for (String param : params) {
            sb.append(isEmpty(param) ? "" : param);
        }
        return sb.toString();
    }

    //用于检测名字是否过长
    public static String checkName(String name, int length) {
        if (name != null && name.length() > length) {
            return name.substring(0, length) + "...";
        } else {
            return name;
        }
    }

    public static String sha1(String data){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(data.getBytes());
            StringBuffer buf = new StringBuffer();
            byte[] bits = md.digest();
            for(int i=0;i<bits.length;i++){
                int a = bits[i];
                if(a<0) a+=256;
                if(a<16) buf.append("0");
                buf.append(Integer.toHexString(a));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * map排序，按字母从小到大升序
     * @param exclude 排除某个key
     * */
    public static String map2SortString(Map<String, String> params, String exclude) {
        List<String> keys = new ArrayList(params.keySet());
        Collections.sort(keys);
        StringBuffer prestr = new StringBuffer();

        for(int i = 0; i < keys.size(); ++i) {
            String key = keys.get(i);
            if(!exclude.equals(key) && params.get(key) != null && params.get(key).length()>0) {
                prestr.append("&" + key + "=" + params.get(key));
            }
        }

        return prestr.substring(1);
    }

    /**
     * map排序，按字母从小到大升序
     * @param exclude 排除某些key
     * */
    public static String map2SortString(Map<String, String> params, String... exclude) {
        List<String> keys = new ArrayList(params.keySet());
        Collections.sort(keys);
        StringBuffer prestr = new StringBuffer();
        List<String> excludes = new ArrayList<>();
        for (String str : exclude){
            excludes.add(str);
        }
        for(int i = 0; i < keys.size(); ++i) {
            String key = keys.get(i);
            if(!excludes.contains(key) && params.get(key) != null && params.get(key).length()>0) {
                prestr.append("&" + key + "=" + params.get(key));
            }
        }
        return prestr.substring(1);
    }
}
