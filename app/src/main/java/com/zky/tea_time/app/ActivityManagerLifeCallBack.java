package com.zky.tea_time.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.zky.tea_time.utils.StringUtils;

import static com.zky.tea_time.app.ActivityStackManager.SOURCE;
import static com.zky.tea_time.app.ActivityStackManager.TARGET;

public class ActivityManagerLifeCallBack implements Application.ActivityLifecycleCallbacks {

    //后期考虑用拦截器编写
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        String source = activity.getIntent().getStringExtra( SOURCE );
        String target = activity.getIntent().getStringExtra( TARGET );
        //计数栈开始
        if (StringUtils.isNotEmpty( source )){
            ActivityStackManager.getInstance().push();
            ActivityStackManager.getInstance().setSourcePage( source );
        }
        //跳转页面
        if (StringUtils.isNotEmpty( target )){
            ActivityStackManager.getInstance().setTargetPage( target );
        }
        ActivityStackManager.getInstance().addActivity( activity );
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ActivityStackManager.getInstance().removeActivity( activity );
    }
}
