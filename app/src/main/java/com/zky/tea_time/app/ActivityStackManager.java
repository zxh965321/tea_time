package com.zky.tea_time.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面栈管理
 *
 * @author chentong
 */
public class ActivityStackManager {

    public static final String SOURCE = "source";
    public static final String TARGET = "target";
    public static final String NORMAL = "normal";

    @SuppressLint("StaticFieldLeak")
    private static volatile ActivityStackManager sAppManager;
    private Application mApplication;
    private List<Activity> mActivityList = new ArrayList<>();
    private List<Activity> mSourceActivityList = new ArrayList<>();
    private volatile boolean sourceFlag = false;
    private volatile String sourcePage = null;
    private volatile String targetPage = null;
    private volatile Activity mCurrentActivity = null;

    private ActivityStackManager() {
    }

    public static ActivityStackManager getInstance() {
        if (sAppManager == null) {
            synchronized (ActivityStackManager.class) {
                if (sAppManager == null) {
                    sAppManager = new ActivityStackManager();
                }
            }
        }
        return sAppManager;
    }

    public void init(Application application) {
        this.mApplication = application;
    }

    private void startTargetActivity(Intent intent) {
        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        mApplication.startActivity( intent );
    }

    //调往指定页面
    public void startTargetActivity(Class activityClass) {
        if (activityClass == null) return;
        targetPage = null;
        startTargetActivity( new Intent( mApplication, activityClass ) );
    }

    public String getSourcePage() {
        return sourcePage;
    }

    public void setSourcePage(String sourcePage) {
        this.sourcePage = sourcePage;
    }

    //计数栈开始
    public void push() {
        sourceFlag = true;
    }

    //弹出计数栈
    public void pop() {
        sourceFlag = false;
        targetPage = null;
        sourcePage = null;
        for (Activity activity : mSourceActivityList) {
            finish( activity );
        }
        //关闭当前页面
        removeActivity( mCurrentActivity );
        mCurrentActivity = null;
    }

    //清空所有页面除当前页面以外
    public void clearExceptTop() {
        for (Activity activity : mSourceActivityList){
            if (activity.getClass().getName().equals( mCurrentActivity.getClass().getName() )){
                continue;
            }
            finish( activity );
        }
        for (Activity activity :mActivityList){
            if (activity.getClass().getName().equals( mCurrentActivity.getClass().getName() )){
                continue;
            }
            finish( activity );
        }
    }

    public void clear() {
        for (Activity activity : mSourceActivityList){
            finish( activity );
        }
        for (Activity activity :mActivityList){
            finish( activity );
        }
    }

    public String getTargetPage() {
        return targetPage;
    }

    public void setTargetPage(String targetPage) {
        this.targetPage = targetPage;
    }

    public void addActivity(Activity activity) {
        mCurrentActivity = activity;
        synchronized (ActivityStackManager.class) {
            if (sourceFlag) {
                mSourceActivityList.add( activity );
            } else {
                mActivityList.add( activity );
            }
        }
    }

    /**
     * 移除activity
     */
    public void removeActivity(Activity activity) {
        synchronized (ActivityStackManager.class) {
            if (mSourceActivityList.contains( activity )) {
                mSourceActivityList.remove( activity );
                finish( activity );
            }
            if (mSourceActivityList.isEmpty()) {
                sourceFlag = false;
                targetPage = null;
                sourcePage = null;
            }
            if (mActivityList.contains( activity )) {
                mActivityList.remove( activity );
                finish( activity );
            }
        }
    }

    //关闭所有页面
    public void appExit() {
        for (Activity activity : mSourceActivityList) {
            finish( activity );
        }
        for (Activity activity : mActivityList) {
            finish( activity );
        }
        release();
        try {
            android.os.Process.killProcess( android.os.Process.myPid() );
            System.exit( 0 );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void finish(Activity activity) {
        if (activity == null) return;
        if (!activity.isFinishing()) {
            activity.finish();
        }
    }

    public AppCompatActivity getCurrentActivity(){
        return (AppCompatActivity) mCurrentActivity;
    }

    private boolean isNotEmptyList(List list) {
        return list != null && list.size() != 0;
    }

    /**
     * 释放资源
     */
    private void release() {
        mActivityList.clear();
        mActivityList = null;
        mSourceActivityList.clear();
        mCurrentActivity = null;
        mSourceActivityList = null;
        mApplication = null;
    }
}