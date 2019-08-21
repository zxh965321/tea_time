package com.zky.tea_time.utils;

import android.app.Dialog;
import android.content.Context;

/**
 * 全局loading框
 * dialog不能用applicationContext
 * 后面找机会优化
 *
 */
public class LoadingUtils {

    private static volatile Dialog loadingDialog;

    public static Dialog createDialog(Context context, String msg) {

        return loadingDialog;
    }

    public static void show(Context context){
        show( context,null );
    }

    public static synchronized void show(Context context, String msg) {
        if(StringUtils.isEmpty( msg )){
            msg = "加载中";
        }

        if(loadingDialog == null ){
            loadingDialog = createDialog( context,msg );
        }

        if (!loadingDialog.isShowing()){
            loadingDialog.show();
        }
    }

    public static void hide() {
        if (loadingDialog!=null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

}
