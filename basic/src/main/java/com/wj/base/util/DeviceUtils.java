package com.wj.base.util;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.wj.base.util.uuid.Installation;

/**
 * Created by 13932 on 2018/3/5.
 */

public class DeviceUtils {
    private static String mImei = "";
    private static String mMicAddress = "";

    @SuppressLint("MissingPermission")
    public static final String imei(Context context) {
        if(!imeiValid(mImei)){
            try {
                mImei = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            } catch (Exception e) {
            } finally {
                if (!imeiValid(mImei)) {
                    mImei = getUUID(context);
                }
            }
        }
        return mImei;
    }

    /**无缓存*/
    @SuppressLint("MissingPermission")
    public static final String deviceId(Context context){
        String deviceId = null;
        try {
            deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        } catch (Exception e) {
        } finally {
            if (!imeiValid(deviceId)) {
                deviceId = getUUID(context);
            }
        }
        return deviceId;
    }

    private static boolean imeiValid(String imei){
        if (!TextUtils.isEmpty(imei) && !"000000000000000".equals(imei)) {
            return true;
        }
        return false;
    }

    public static String getUUID(Context context){
        String  uuid = SharePreferenceUtils.getString(context, "uuid", "", Context.MODE_MULTI_PROCESS);
        if(TextUtils.isEmpty(uuid)){
            uuid = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            if (null == uuid || "9774d56d682e549c".equals(uuid) || uuid.length() < 15 ) {
                //if ANDROID_ID is null, or it's equals to the GalaxyTab generic ANDROID_ID or bad, generates a new one
                uuid = null;
            }
            if(TextUtils.isEmpty(uuid)) {
                uuid = appInstallationId(context);
            }
            SharePreferenceUtils.saveString(context, "uuid", uuid, Context.MODE_MULTI_PROCESS);
        }
        return uuid;
    }

    //获取应用安装id，每次安装不同
    public static final String appInstallationId(Context context) {
        return Installation.id(context);
    }

    /**
     * 获取设备mac地址
     *
     * @return
     */
    public static String getDeviceMac(Context context) {
        if(TextUtils.isEmpty(mMicAddress)){
            WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (wm != null) {
                mMicAddress = wm.getConnectionInfo().getMacAddress();
            }
        }
        return mMicAddress;
    }

    /**
     * 得到当前手机 sdk版本号
     *
     * @return
     */
    public static String getAndroidSDKVersion() {
        return Build.VERSION.RELEASE + "";
    }

    /**
     * 得到当前手机 sdk版本号
     *
     * @return
     */
    public static String getAndroidSDKVersionCode() {
        return android.os.Build.VERSION.SDK_INT + "";
    }

    /**
     * 得到设备型号
     *
     * @param
     * @return
     */
    public static String getDeviceName() {
        return android.os.Build.MODEL;
    }

    /**
     * 得到设备厂商
     *
     * @param
     * @return
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取手机的名字
     *
     * @return
     */
    public static final String phoneName() {
        return BluetoothAdapter.getDefaultAdapter().getName();
    }

    /*手机屏幕是否已经锁定 锁定时返回true*/
    public static boolean isScreenLocked(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        return !pm.isScreenOn();
    }
}
