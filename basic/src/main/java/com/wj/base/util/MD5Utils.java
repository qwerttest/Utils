package com.wj.base.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 13932 on 2018/3/12.
 */

public class MD5Utils {

    public static final String bytesMd5(byte[] data) {
        try {
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(data);
            byte[] result = mDigest.digest();
            return bytesToHexString(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final String bytesToHexString(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (byte b : bytes) {
            int val = b & 0xff;
            if (val < 0x10) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(val));
        }
        return sb.toString();
    }

    public static final String stringMd5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(s.getBytes());
            byte b[] = md.digest();

            StringBuffer buf = new StringBuffer("");
            int i;
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            String str = buf.toString();
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
