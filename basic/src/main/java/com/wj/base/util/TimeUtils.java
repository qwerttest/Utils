package com.wj.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zhou on 2016/5/12.
 */
public class TimeUtils {
    /**
     * 将播放进度的毫秒数转换成时间格式  zhou
     * 如 3000 --> 00:03
     *
     * @param progress
     * @return
     */
    public static String formatTimeFromProgress(long progress) {
        //总的秒数
        long msecTotal = progress / 1000;
        return formatTimeFromLeftTime(msecTotal);
    }

    //将字符串时间转换成long类型时间
    public static long formatStringToLong(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long l = 0;
        if (date != null) {
            l = date.getTime();
        }

        return l;
    }

    //将字符串时间转换成long类型时间
    public static long formatStringToLong2(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long l = 0;
        if (date != null) {
            l = date.getTime();
        }

        return l;
    }

    //将long类型转换成字符串类型
    public static String formatLongToString(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(time);
    }

    //将long类型转换成字符串类型(年月日)
    public static String formatLongToDateString(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(time);
    }

    /**
     * 将头条的秒数转换成时间格式   zhou
     * 如 3000 --> 00:03
     *
     * @return
     */
    public static String formatTimeFromLeftTime(long msecTotal) {
        long hour = msecTotal / 3600;
        long min = msecTotal % 3600 / 60;
        long msec = msecTotal % 60;
        String hourStr = hour > 0 ? ((hour < 10 ? ("0" + hour) : hour) + ":") : "";
        String minStr = min < 10 ? "0" + min : "" + min;
        String msecStr = msec < 10 ? "0" + msec : "" + msec;
        return hourStr + minStr + ":" + msecStr;
    }

    //秒数转成11:50:36
    public static String formatTimeFromHoursTime(int msecTotal) {
        int hours = msecTotal / 3600;
        int min = msecTotal % 3600 / 60;
        String hoursStr = hours < 10 ? "0" + hours : "" + hours;
        String minStr = min < 10 ? "0" + min : "" + min;
        return hoursStr + ":" + minStr;
    }

    public static String formatTimeFromLong(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(time);
    }


    public static String formatYearTimeFromLong(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        return format.format(time);
    }

    public static String formatTimeToStr(long time) {
        //总的秒数
        long msecTotal = time / 1000;
        long day = msecTotal / (24 * 3600);
        long hour = (msecTotal - day * 24 * 3600) / 3600;
        long min = (msecTotal - day * 24 * 3600 - hour * 3600) / 60;
        long msec = msecTotal % 60;
        String str = "";
        if (day > 0) {
            str += day + "天";
        }
        if (hour > 0) {
            str += hour + "小时";
        }
        if (min > 0) {
            str += min + "分钟";
        }
        if (msec > 0) {
            str += msec + "秒";
        }
        return str;
    }

    /**
     * 转换固定字符串   xx天xx小时xx分钟
     */
    public static String formatTimeToStrForMin(long time) {
        //总的秒数
        long msecTotal = time / 1000;
        long day = msecTotal / (24 * 3600);
        long hour = (msecTotal - day * 24 * 3600) / 3600;
        long min = (msecTotal - day * 24 * 3600 - hour * 3600) / 60;
        String str = "";
        if (day > 0) {
            str += day + "天";
        }
        if (hour > 0) {
            str += hour + "小时";
        }
        if (min > 0) {
            str += min + "分钟";
        }

        return str;
    }

    /**
     * 获取距离本日结束的剩余时间
     */
    public static int getTodaySurplusTime() {
        long todaySurplusTime = 0;//本日剩余时间 保存数据有效期
        try {
            SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
            SimpleDateFormat formater2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.CHINA);
            Date date = formater2.parse(formater.format(new Date()) + " 23:59:59");
            todaySurplusTime = date.getTime() - System.currentTimeMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) (todaySurplusTime / 1000);
    }

    /**
     * 靓号中心倒计时
     *
     * @param time 毫秒数
     * @return string x天x小时x分
     */
    public static String millisecond2DateString(long time) {
        long days = time / (1000 * 60 * 60 * 24);
        long hours = (time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (time % (1000 * 60 * 60)) / (1000 * 60);

        return days + "天" + hours + "小时" + minutes + "分";
    }

    /**
     * 未超过1小时，则展示n分钟前，超过小时则显示n小时之前，超过天则展示n天前。 + 月前 年前
     *
     * @param millisecond 时间戳
     * @return
     */
    public static String formatMillisecond(long millisecond) {

        long current = System.currentTimeMillis();
        long differ = current - millisecond;
        long second = differ / 1000;
        long minute = second / 60;
        if (minute < 60) {
            if (minute <= 0) {
                return "1分钟内";
            }
            return minute + "分钟前";
        }
        long hour = minute / 60;
        if (hour < 24) {
            return hour + "小时前";
        }
        long day = hour / 24;
        if (day < 30) {
            return day + "天前";
        }
        long month = day / 30;
        if (month < 12) {
            return month + "个月前";
        }
        long year = month / 12;
        return year + "年前";
    }
}
