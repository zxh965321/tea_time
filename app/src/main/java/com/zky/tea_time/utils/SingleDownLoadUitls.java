package com.zky.tea_time.utils;

import android.Manifest;
import android.content.Context;

import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.zky.tea_time.app.aop.annotation.aspect.Permission;

public class SingleDownLoadUitls {
    public static final int TYPE_apk = 2;
    public static final int TYPE_file = 3;
    public static final int TYPE_png = 1;

    @Permission(value = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE})
    public static void dowload(Context mContext, String url, String path, FileDownloadListener downloadListener){
        FileDownloader.setup(mContext);
        FileDownloader.getImpl().create(url)
                .setPath(path,false)
                .setCallbackProgressTimes(300)//设置下载最大回调progress次数
                .setCallbackProgressMinInterval(1000)//设置每次请求progress时间间隔
                .setAutoRetryTimes(3)//下载失败后重试3次
                .setMinIntervalUpdateSpeed(1000)
                .setListener(downloadListener)
                .start();
    }

}
