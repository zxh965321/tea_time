package com.zky.tea_time.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import com.jess.arms.base.BaseApplication;
import com.zky.tea_time.app.MyApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author lixuebo
 * @time 2017/11/16.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    public static final String TAG = "CrashHandler";
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private static CrashHandler INSTANCE = new CrashHandler();
    private Context mContext;
    private Map<String, String> infos = new HashMap<String, String>();
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    public void init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                com.jess.arms.utils.LogUtils.debugInfo("=======crash=======");
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                MyApplication.getInstance().getApplicationContext().startActivity(new Intent(MyApplication.getInstance().getApplicationContext(), BaseApplication.class));
            } catch (Exception e) {
                com.jess.arms.utils.LogUtils.debugInfo("ic_error :" + e);
            }

        }
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        ex.printStackTrace();
        collectDeviceInfo(mContext);
        saveCrashInfo2File(ex);
//        savePlayVideoActivityCrashInfo();
        return true;
    }

//    private void savePlayVideoActivityCrashInfo() {
//        Activity currentActivity = MyApplication.getInstance().getAppComponent().appManager().getCurrentActivity();
//        if(currentActivity instanceof PlayVideoActivity){
//            PlayVideoActivity activity = ((PlayVideoActivity) currentActivity);
//            String ticketNo = activity.ticketNo;
//            String skuId = activity.skuId;
//            String codingStandard = activity.getCodingStandard();
//            String resolution = activity.getResolution();
//            String networkType = activity.getNetworkType();
//            int playMode = activity.getPlayMode();
//            int decodingMode = activity.getDecodingMode();
//            if(activity.videoPlayer!=null){
//                int progress = activity.videoPlayer.getCurrentPositionWhenPlaying();
//                LogUtils.i("ticketNo:"+ticketNo+",progress:"+progress);
//                PlayReportUtil.savePlayReport(activity.playReportDaoUtils,PlayVideoActivity.TYPE_CRASH,progress,ticketNo,skuId,codingStandard,
//                        resolution,playMode,decodingMode,networkType);
//            }
//        }
//    }

    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
            }
        }
    }

    private String saveCrashInfo2File(Throwable ex) {

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File directoryFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/xffq_crash" + "/" + SystemUtils.getAppInfo());
                if (!directoryFile.exists()) {
                    directoryFile.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(directoryFile + File.separator + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            com.jess.arms.utils.LogUtils.debugInfo("ic_error occured =======" + e);
        }
        return null;
    }

}