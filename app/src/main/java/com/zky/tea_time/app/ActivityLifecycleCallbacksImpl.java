package com.zky.tea_time.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.gyf.barlibrary.ImmersionBar;
import com.jess.arms.base.BaseActivity;
import com.zky.tea_time.R;

import timber.log.Timber;

/**
 * ================================================
 * 展示 {@link Application.ActivityLifecycleCallbacks} 的用法
 * <p>
 * Created by MVPArmsTemplate on 01/09/2019 14:35
 */
public class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Timber.i(activity + " - onActivityCreated");
    }

    @Override
    public void onActivityStarted(Activity activity) {

        //父类不是baseActivity 不会设置为侵入式
        if ((isEqualSuperClass(activity.getClass(), BaseActivity.class))) {
            //沉浸式状态栏
            ImmersionBar.with(activity)
                    .statusBarDarkFont(true, 0.2f)
//                    .titleBar(activity.findViewById(R.id.toolbar))
                    .init();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Timber.i(activity + " - onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Timber.i(activity + " - onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Timber.i(activity + " - onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Timber.i(activity + " - onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {

        //父类不是baseActivity 不会设置为侵入式
        if (isEqualSuperClass(activity.getClass(), BaseActivity.class)) {
            ImmersionBar.with(activity).destroy();
            Timber.i(activity + " - onActivityDestroyed");
            //横竖屏切换或配置改变时, Activity 会被重新创建实例, 但 Bundle 中的基础数据会被保存下来,移除该数据是为了保证重新创建的实例可以正常工作
            activity.getIntent().removeExtra("isInitToolbar");
        }
    }

    /**
     * 该类是此父类
     *
     * @param childClazz
     * @param superClass
     * @return
     */
    private boolean isEqualSuperClass(Class childClazz, Class superClass) {
        Class clazz = childClazz.getSuperclass();
        if (clazz.getName().equals(superClass.getName())) {
            return true;
        }
        return false;
    }

}
