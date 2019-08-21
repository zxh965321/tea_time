package com.zky.tea_time.app;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class ActivityResourceLifecycleCallback implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        getResources( activity );
        registerFragmentCallbacks( activity );
    }

    /**
     * 设置标准字体
     *
     * @param activity
     * @return
     */
    public Resources getResources(Activity activity) {
        Resources res = activity.getResources();
        Configuration newConfig = new Configuration();
        newConfig.setToDefaults();//设置默认
        res.updateConfiguration( newConfig, res.getDisplayMetrics() );
        return res;
    }

    private void registerFragmentCallbacks(Activity activity) {

        if(activity instanceof FragmentActivity){
        int count = ((FragmentActivity) activity).getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) return;
        ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks( new FragmentResourceLifecycleCallbacks(), true );

        for (int i = 0; i < count; i++) {
            Fragment fragment = (Fragment) ((FragmentActivity) activity).getSupportFragmentManager().getBackStackEntryAt( i );
            int fragmentCount = fragment.getChildFragmentManager().getBackStackEntryCount();
            if (fragmentCount == 0) return;
            for (int j = 0; j < fragmentCount; j++) {
                Fragment childFragment = (Fragment) fragment.getChildFragmentManager().getBackStackEntryAt( j );
                childFragment.getChildFragmentManager().registerFragmentLifecycleCallbacks( new FragmentResourceLifecycleCallbacks(), true );
            }
        }
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
        getResources(activity);
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
    }

}
