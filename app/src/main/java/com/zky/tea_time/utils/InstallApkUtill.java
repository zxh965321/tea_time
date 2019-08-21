package com.zky.tea_time.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.zky.tea_time.BuildConfig;

import java.io.File;

import static com.jess.arms.utils.ArmsUtils.startActivity;


/**
 * aok 安装工具类
 * creat by karson
 *
 * 兼容7.0以上，以下安装apk
 */
public class InstallApkUtill {

    public static void install(Context context,String apkPath){

        if (context == null || TextUtils.isEmpty(apkPath)) {
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        File file = new File(apkPath);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 24) {//大于7.0使用此方法
            Uri apkUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID+".fileprovider", file);///-----ide文件提供者名
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }else {//小于7.0就简单了
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
        }
        startActivity(intent);

    }
}
