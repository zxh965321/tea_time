package com.zky.tea_time.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import org.jetbrains.annotations.NotNull;

/**
 * @author chentong
 * 权限判断与权限提示框
 *
 */
public class PermissionUtils {

    //是否有该权限
    public static boolean isOpenPermisson(@NotNull Context context, @NotNull String permisson) {
        return ActivityCompat.checkSelfPermission(context, permisson) == 0;
    }

    //打开权限
    public static void openPermisson(@NotNull Activity context, @NotNull String[] permisson) {
        ActivityCompat.requestPermissions(context, permisson, 100);
    }

    //校验是否含有权限
    public static boolean checkSelfPermission(@NonNull Context context, @NonNull String permission) {
        if (ContextCompat.checkSelfPermission( context, permission ) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    //没有权限
    public static boolean checkNoSelfPermission(@NonNull Context context, @NonNull String permission) {
        return !checkSelfPermission(context,permission);
    }

    //显示权限提示
    public static void showPermissions(Context mContext, String msg) {
//        Dialog dialog = new AlertDialog.Builder( mContext ).create();
//        View view = LayoutInflater.from( mContext ).inflate( R.layout.dialog_permissions, null );
//        dialog.show();
//        dialog.setContentView( view );
//        Button addBtn = (Button) view.findViewById( R.id.btn_add );
//        Button dissBtn = (Button) view.findViewById( R.id.btn_diss );
//        TextView hintTv = (TextView) view.findViewById( R.id.tv_hint );
//        hintTv.setText( msg );
//
//        addBtn.setOnClickListener( v -> {
//            dialog.dismiss();
//            Intent intent = new Intent();
//            intent.setAction( Settings.ACTION_APPLICATION_DETAILS_SETTINGS );
//            intent.addCategory( Intent.CATEGORY_DEFAULT );
//            intent.setData( Uri.parse( "package:" + mContext.getPackageName() ) );
//            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
//            intent.addFlags( Intent.FLAG_ACTIVITY_NO_HISTORY );
//            intent.addFlags( Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS );
//            mContext.startActivity( intent );
//        } );
//
//        dissBtn.setOnClickListener( v -> {
//            dialog.dismiss();
//        } );

    }

}