package com.zky.tea_time.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.jess.arms.base.BaseApplication;
import com.zky.tea_time.app.MyApplication;
import com.zky.tea_time.app.aop.annotation.aspect.Permission;

import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

public class SystemUtils {

    static Context context = MyApplication.getInstance();
    static PackageManager pm = context.getPackageManager();
    static String packname = context.getPackageName();

    /**
     * 获取ip地址
     *
     * @return
     */
    public static String getIpAddressString() {
        try {
            for (Enumeration<NetworkInterface> enNetI = NetworkInterface.getNetworkInterfaces(); enNetI.hasMoreElements(); ) {
                NetworkInterface netI = enNetI.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = netI.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取安装版本号
     *
     * @return
     */

    public static String getAppInfo() {
        try {
            String pkName = MyApplication.getInstance().getPackageName();
            String versionName = MyApplication.getInstance().getPackageManager().getPackageInfo(pkName, 0).versionName;
            int versionCode = MyApplication.getInstance().getPackageManager().getPackageInfo(pkName, 0).versionCode;
            return versionName;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 判断手机是否安装某个应用
     *
     * @param context
     * @param appPackageName 应用包名
     * @return true：安装，false：未安装
     */
    public static boolean isApplicationAvilible(Context context, String appPackageName) {
        try {
            // 获取packagemanager
            PackageManager packageManager = context.getPackageManager();
            // 获取所有已安装程序的包信息
            List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
            if (pinfo != null) {
                for (int i = 0; i < pinfo.size(); i++) {
                    String pn = pinfo.get(i).packageName;
                    if (appPackageName.equals(pn)) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * 实现文本复制功能
     *
     * @param content 复制的文本
     */
    public static void copy(String content) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) MyApplication.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
        if (cmb != null) {
            cmb.setText(content.trim());
        }
    }

    /**
     * 使用浏览器打开链接
     */
    public static void openLink(Context context, String content) {
        if (!TextUtils.isEmpty(content) && content.startsWith("http")) {
            Uri issuesUrl = Uri.parse(content);
            Intent intent = new Intent(Intent.ACTION_VIEW, issuesUrl);
            context.startActivity(intent);
        }
    }

    /**
     * 分享
     */
//    public static void share(Context context, String extraText) {
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("text/plain");
//        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.action_share));
//        intent.putExtra(Intent.EXTRA_TEXT, extraText);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(Intent.createChooser(intent, context.getString(R.string.action_share)));
//    }


    /**
     * 获取程序的签名
     */
    public static String AppSignature() {
        try {
            PackageInfo packinfo = pm.getPackageInfo(packname,
                    PackageManager.GET_SIGNATURES);
            // 获取到所有的权限
            return packinfo.signatures[0].toCharsString();

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
        return "No Search";
    }

    /**
     * 获得程序图标
     */
    public static Drawable AppIcon() {
        try {
            ApplicationInfo info = pm.getApplicationInfo(
                    context.getPackageName(), 0);
            return info.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     * 获得程序名称
     */
    public static String AppName() {
        try {
            ApplicationInfo info = pm.getApplicationInfo(packname, 0);
            return info.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "No Search";
    }

    /**
     * 获得软件版本号
     */
    public static int VersionCode() {
        int versioncode = 0;
        try {
            versioncode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versioncode;
    }

    /**
     * 获得软件版本名
     */
    public static String VersionName() {
        String versionname = "unknow";
        try {
            versionname = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionname;
    }

    /**
     * 得到软件包名
     */
    public static String PackgeName() {
        String packgename = "unknow";
        try {
            packgename = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packgename;
    }

    /**
     * 获得imei号
     */
//    @Permission(value = Manifest.permission.MODIFY_PHONE_STATE)
//    public static String IMEI() {
//        String imei = "NO Search";
//        TelephonyManager telephonyManager = (TelephonyManager) context
//                .getSystemService(Context.TELEPHONY_SERVICE);
//        imei = telephonyManager.getDeviceId();
//        return imei;
//    }

    /**
     * 获得imsi号
     */
//    public static String IMSI() {
//        String imsi = "NO Search";
//        TelephonyManager telephonyManager = (TelephonyManager) context
//                .getSystemService(Context.TELEPHONY_SERVICE);
//        imsi = telephonyManager.getSubscriberId();
//        return imsi;
//    }

    /**
     * 返回本机电话号码
     */
//    public static String Num() {
//        String num = "NO Search";
//        TelephonyManager telephonyManager = (TelephonyManager) context
//                .getSystemService(Context.TELEPHONY_SERVICE);
//        num = telephonyManager.getLine1Number();
//        return num;
//    }

    /**
     * 得到手机产品序列号
     */
    public static String SN() {
        String sn = "NO Search";
        String serial = android.os.Build.SERIAL;// 第二种得到序列号的方法
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            sn = (String) get.invoke(c, "ro.serialno");
        } catch (Exception e) {

            e.printStackTrace();
        }
        return sn;
    }

    /**
     * 获得手机sim号
     */
//    public static String SIM() {
//        String sim = "NO Search";
//
//        TelephonyManager telephonyManager = (TelephonyManager) context
//                .getSystemService(Context.TELEPHONY_SERVICE);
//        sim = telephonyManager.getSimSerialNumber();
//
//        return sim;
//    }

    /**
     * 返回安卓设备ID
     */
    public static String ID() {
        String id = "NO Search";
        id = android.provider.Settings.Secure.getString(
                context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

        return id;
    }

    /**
     * 得到设备mac地址
     */
    public static String MAC() {
        String mac = "NO Search";
        WifiManager manager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        mac = info.getMacAddress();
        return mac;
    }

    /**
     * 得到当前系统国家和地区
     */
    public static String Country() {
        String country = "NO Search";
        country = context.getResources().getConfiguration().locale.getCountry();
        return country;
    }

    /**
     * 得到当前系统语言
     */
    public static String Language() {
        String language = "NO Search";
        String country = context.getResources().getConfiguration().locale
                .getCountry();
        language = context.getResources().getConfiguration().locale
                .getLanguage();
        // 区分简体和繁体中文
        if (language.equals("zh")) {
            if (country.equals("CN")) {
                language = "Simplified Chinese";
            } else {
                language = "Traditional Chinese";
            }
        }
        return language;
    }

    /**
     * 返回系统屏幕的高度（像素单位）
     */
    public static int Height() {
        int height = 0;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        height = dm.heightPixels;
        return height;
    }

    /**
     * 返回系统屏幕的宽度（像素单位）
     */
    public static int Width() {
        int width = 0;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        width = dm.widthPixels;
        return width;
    }
}
