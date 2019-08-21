package com.zky.tea_time.app.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.zky.tea_time.app.MyApplication;

/**
 * @author chentong
 * @date 2019/2/14
 * 第三方sdk启动
 */
public class InitializeService extends IntentService {

    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.zky.tea_time.app.start";
    private static String TAG = InitializeService.class.getSimpleName();

    public InitializeService() {
        super(TAG);
    }

    public InitializeService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT_WHEN_APP_CREATE.equals(action)) {
                performInit();
            }
        }
    }

    private void performInit() {
        //此处进行第三方sdk初始化
        //融360
        Context applicationContext = MyApplication.getInstance();


    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }
}
