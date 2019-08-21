package com.zky.tea_time.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 获得设备重要信息
 */
public class DeviceUtil {

    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获得本地dns
     * @return
     */
    public static String getLocalDNS(){
        Process cmdProcess = null;
        BufferedReader reader = null;
        String dnsIP = "";
        try {
            cmdProcess = Runtime.getRuntime().exec("getprop net.dns1");
            reader = new BufferedReader(new InputStreamReader(cmdProcess.getInputStream()));
            dnsIP = reader.readLine();
            return dnsIP;
        } catch (IOException e) {
            return null;
        } finally{
            try {
                reader.close();
            } catch (IOException e) {
            }
            cmdProcess.destroy();
        }
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static  String getIMEI(Context context) {
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMEI号
            String imei = telephonyManager.getDeviceId();
            //在次做个验证，也不是什么时候都能获取到的啊
            if (imei == null) {
                imei = "";
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 获得可用内存
     * @param activity
     * @return
     */
    public static long getSystemMemory(Activity activity){
        //获得ActivityManager服务的对象
        ActivityManager mActivityManager = (ActivityManager)activity.getSystemService(Context.ACTIVITY_SERVICE);
        //获得MemoryInfo对象
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo() ;
        //获得系统可用内存，保存在MemoryInfo对象上
        mActivityManager.getMemoryInfo(memoryInfo) ;
        long memSize = memoryInfo.availMem ;
        return memSize;
    }

    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }


    /**
     * SD卡空间
     * 总大小，可用空间，非可用空间
     * @return
     */
    public static List<Long> getSDSpace() {
        String state = Environment.getExternalStorageState();
        List<Long> splist = new ArrayList<>(  );
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = (long)sf.getBlockSize();
            long blockCount = (long)sf.getBlockCount();
            long availCount = (long)sf.getAvailableBlocks();
            Log.d("", "block大小:" + blockSize + ",block数目:" + blockCount + ",总大小:" + blockSize * blockCount / (long)1024 + "KB");
            Log.d("", "可用的block数目：:" + availCount + ",剩余空间:" + availCount * blockSize / (long)1024 + "KB");
            long total = blockSize * blockCount;
            long avail = availCount * blockSize;
            splist.add( total );
            splist.add( avail );
            splist.add( total - avail );
            return splist;
        } else {
            splist.add( 0L );
            splist.add( 0L );
            splist.add( 0L );
            return splist;
        }
    }

    public static List<Long> getSystemCardSpace() {
        File root = Environment.getRootDirectory();
        StatFs sf = new StatFs(root.getPath());
        long blockSize = sf.getBlockSize();
        long blockCount = sf.getBlockCount();
        long availCount = sf.getAvailableBlocks();
        Log.d("", "block大小:" + blockSize + ",block数目:" + blockCount + ",总大小:" + blockSize * blockCount / (long)1024 + "KB");
        Log.d("", "可用的block数目：:" + availCount + ",可用大小:" + availCount * blockSize / (long)1024 + "KB");
        long total = blockSize * blockCount;
        long avail = availCount * blockSize;
        List<Long> splist = new ArrayList<>(  );
        splist.add( total );
        splist.add( total - avail );
        splist.add( avail );
        return splist;
    }

}
