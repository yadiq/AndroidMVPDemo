package com.hqumath.demo.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import static android.content.Context.TELEPHONY_SERVICE;

public class DeviceUtil {

    /**
     * 获取imei SIM卡槽
     */
    public static String getIMEI() {
        Context context = CommonUtil.getContext();
        String imei = "";
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            imei = tm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imei;
    }

    /**
     * 获取wifi网卡的mac地址，6.0以上特殊处理
     * 要求targetSdkVersion<=29, 否则获取不到mac地址
     * @return
     */
    public static String getMac() {
        Context context = CommonUtil.getContext();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String str = getMacMoreThanM();
                if (!TextUtils.isEmpty(str))
                    return str;
            } else {
                @SuppressLint("WifiManagerLeak")
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                if (wifiInfo != null)
                    return wifiInfo.getMacAddress();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * android 6.0+获取wifi的mac地址
     *
     * @return
     */
    private static String getMacMoreThanM() {
        String wlan0MAC = null;
        String eth0MAC = null;
        try {
            Enumeration enumeration = NetworkInterface.getNetworkInterfaces();
            while (enumeration.hasMoreElements()) {//本机器所有的网络接口
                NetworkInterface networkInterface = (NetworkInterface) enumeration.nextElement();
                // wlan0:无线网卡 eth0：以太网卡
                String name = networkInterface.getName();
                //获取硬件地址，一般是MAC
                byte[] arrayOfByte = networkInterface.getHardwareAddress();
                if (arrayOfByte == null || arrayOfByte.length == 0) continue;
                if (name.equals("wlan0")) {
                    wlan0MAC = ByteUtil.bytesToHex(arrayOfByte).toUpperCase();
                } else if (name.equals("eth0")) {
                    eth0MAC = ByteUtil.bytesToHex(arrayOfByte).toUpperCase();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(eth0MAC)) return eth0MAC;
        if (!TextUtils.isEmpty(wlan0MAC)) return wlan0MAC;
        return null;
    }

    /**
     * 获得IP地址，分为三种情况，一是wifi下，二是移动网络下，三是有线网络, 得到的ip地址是不一样的
     */
    public static String getIPAddress() {
        Context context = CommonUtil.getContext();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                //当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                //调用方法将int转换为地址字符串
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            } else {//当前使用2G/3G/4G网络 使用有线网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            }
        } else {
            CommonUtil.toast("当前无网络连接,请在设置中打开网络");
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    /**
     * 设备首次运行的时候，系统会随机生成一64位的数字，恢复出厂设置后改变
     * 支持获取oaid的，优先获取oaid，需要集成aar https://github.com/haoguibao/OaidDemo/tree/master
     */
    public static String getAndroidId() {
        return Settings.System.getString(CommonUtil.getContext().getContentResolver(), Settings.System.ANDROID_ID);
    }

    /**
     * 设备序列号，有些手机上会出现垃圾数据，比如红米手机返回的就是连续的非随机数
     */
    public static String getSN() {
        return android.os.Build.SERIAL;
    }
}
