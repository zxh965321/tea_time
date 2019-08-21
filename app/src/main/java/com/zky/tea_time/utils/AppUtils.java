package com.zky.tea_time.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hjq.toast.ToastUtils;
import com.zky.tea_time.R;
import com.zky.tea_time.app.ConstantsUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Pattern;

import me.jessyan.autosize.utils.LogUtils;

public class AppUtils {
    private static long lastClickTime;
    private static final long FAST_CLICK_TIME = 600;
    private static  String umengChannel = "";

    /**
     * 获取当前本地apk的版本
     *
     * @param mContext
     * @return
     */
    public static Integer getVersionCode(Context mContext) {
        int versionCode = 0;
        try { //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().getPackageInfo( mContext.getPackageName(), 0 ).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;
    }

    public static String getUmengChannel(Context context){
        if (StringUtils.isNotEmpty( umengChannel )) return umengChannel;
        umengChannel = getMetaValue( context,"UMENG_CHANNEL" );
        return umengChannel;
    }

    public static String getMetaValue(Context context, String name){
        ApplicationInfo info;
        String value;
        try {
            info = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            value =info.metaData.getString(name);
        } catch (PackageManager.NameNotFoundException e) {
            return "hx";
        }
        return value;
    }

    /**
     * 获取版本名称
     * @param mContext
     * @return
     */
    public static String getVersionName(Context mContext) {
        try {
            return mContext.getPackageManager().getPackageInfo( mContext.getPackageName(), 0 ).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getDeviceId(Context context) {
        try {
            if (ActivityCompat.checkSelfPermission( context, Manifest.permission.READ_PHONE_STATE ) == PackageManager.PERMISSION_GRANTED) {
                return ((TelephonyManager) context.getSystemService( Context.TELEPHONY_SERVICE )).getDeviceId();
            }
        }catch (Exception ignored){

        }
        return "";
    }

    public static String getAndroidId(Context context){
        return  Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    @SuppressLint("WrongConstant")
    @Nullable
    public static String getIPAddress(@NotNull Context context) {
        @SuppressLint("WrongConstant") Object var10000 = context.getSystemService("connectivity");
        if (var10000 == null) {
        } else {
            NetworkInfo info = ((ConnectivityManager) var10000).getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getType() == 0) {
                    try {
                        Enumeration en = NetworkInterface.getNetworkInterfaces();

                        while (en.hasMoreElements()) {
                            NetworkInterface intf = (NetworkInterface) en.nextElement();
                            Enumeration enumIpAddr = intf.getInetAddresses();

                            while (enumIpAddr.hasMoreElements()) {
                                InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                                if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                    return inetAddress.getHostAddress();
                                }
                            }
                        }
                    } catch (SocketException var7) {
                        var7.printStackTrace();
                    }
                } else if (info.getType() == 1) {
                    var10000 = context.getSystemService("wifi");
                    if (var10000 == null) {
                    }

                    WifiManager wifiManager = (WifiManager) var10000;
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    return intIP2StringIP(wifiInfo.getIpAddress());
                }
            }

            return "";
        }
        return "";
    }

    private static String intIP2StringIP(int ip) {
        return (ip & 255) + "." + (ip >> 8 & 255) + "." + (ip >> 16 & 255) + "." + (ip >> 24 & 255);
    }

    /**
     * 给一个textview开启倒计时
     * <p>
     * 第一个参数表示总时间，第二个参数表示间隔时间。
     */
    public static CountDownTimer startTimeCurrent(TextView textView) {
        return new CountDownTimer(ConstantsUtils.TIMECURRENT, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setEnabled(false);
                textView.setText(millisUntilFinished / 1000 + "秒");
                Resources resources = textView.getContext().getResources();
                textView.setTextColor(resources.getColor(R.color.colorPrimary));
                textView.setBackgroundColor(resources.getColor(R.color.white));
            }

            @Override
            public void onFinish() {
                Resources resources = textView.getContext().getResources();
                LogUtils.e("结束");
                textView.setText(resources.getString(R.string.skip));
                textView.setTextColor(resources.getColor(R.color.colorPrimary));
//                textView.setBackgroundColor(resources.getColor(R.color.appmainbg));
                textView.setBackgroundColor(resources.getColor(R.color.white));
                textView.setEnabled(true);

            }

        };
    }

    //避免快速双击
    public static synchronized boolean isFastClick() {
        return isFastClick(FAST_CLICK_TIME);
    }

    public static synchronized boolean isFastClick(long timeCurrent) {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < timeCurrent) {
            return true;
        } else {
            lastClickTime = time;
            return false;
        }
    }

    public static Boolean inputIsRight(EditText editText) {
        if (editText.getText().toString().length() > 5
                && isContainLetter(editText.getText().toString())
                && isContainNumber(editText.getText().toString())) {
            return true;
        }
        ToastUtils.show(editText.getContext().getResources().getString(R.string.imputpassword));
        return false;
    }

    public static final boolean isContainLetter(@NotNull String str) {
        return Pattern.compile(".*[a-zA-Z]+.*").matcher((CharSequence) str).matches();
    }

    public static final boolean isContainNumber(@NotNull String company) {
        return Pattern.compile("[0-9]").matcher((CharSequence) company).find();
    }

    @NotNull
    public static final String getSystemModel() {
        String var10000 = Build.MODEL;
        if (Build.MODEL == null) {
            return "";
        }

        return var10000;
    }
}
