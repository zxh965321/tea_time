package com.zky.tea_time.app;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.hjq.toast.ToastUtils;
import com.jess.arms.base.BaseApplication;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.crashreport.CrashReport;
import com.zky.tea_time.R;

import static com.zky.tea_time.app.ConstantsUtils.BUGLY_APPID;

public class MyApplication extends BaseApplication {
    private static MyApplication instance;
    private static String apkPath;
    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.color_DDE0E2, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }
    @Override
    public void onCreate() {
        super.onCreate();
        apkPath = getExternalFilesDir(null) + "/download" + "/hexin.apk";
        instance = this;
        ToastUtils.init(this);
        //页面堆栈管理
        ActivityStackManager.getInstance().init( this );
        //bugly崩溃
        CrashReport.initCrashReport(getApplicationContext(), BUGLY_APPID, false);
//        JPushUtil.init(this);
//        UmengUtil.init( this );
//        X5Util.initX5( this );
//        BlockCanary.install(this, new AppBlockCanaryContext()).start();
        //耗时初始化启动
//        InitializeService.start( this );

    }

    @Override
    protected void attachBaseContext(Context base) {
        MultiDex.install(base);
        super.attachBaseContext(base);
    }

    public  String getApkPath() {
        return apkPath;
    }

    public synchronized static MyApplication getInstance() {
        return instance;
    }
}
