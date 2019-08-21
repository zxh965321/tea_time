package com.zky.tea_time.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.zky.tea_time.R;
//import com.zky.tea_time.mvp.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lixuebo on 2018/5/1.
 */

public class NotificationUtils extends ContextWrapper {

    private NotificationManager manager;
    public static final String id = "channel_1";
    public static final String name = "channel_name_1";
    private Bitmap bigPicture = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher_round);
    private PendingIntent pi;
    private List<Integer> notifyIds = new ArrayList<>();
    private int temp = 1001;

    public NotificationUtils(Context context) {
        super(context);
//        Intent intent = new Intent(this, MainActivity.class).putExtra("intentFlag", "NotificationClick");
//        pi = PendingIntent.getActivity(this, 0, intent,
//                PendingIntent.FLAG_CANCEL_CURRENT);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    private NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getChannelNotification(String title, String content) {
        return new Notification.Builder(getApplicationContext(), id)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(bigPicture)
                .setContentIntent(pi)
                .setAutoCancel(true);
    }

    public NotificationCompat.Builder getNotification_25(String title, String content) {
        return new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(bigPicture)
                .setContentIntent(pi)
                .setAutoCancel(true);
    }

    public void sendNotification(String title, String content) {
        if (Build.VERSION.SDK_INT >= 26) {
            createNotificationChannel();
            Notification notification = getChannelNotification
                    (title, content).build();
            getManager().notify(1, notification);
        } else {
            Notification notification = getNotification_25(title, content).build();
            getManager().notify(1, notification);
        }
    }

    public void sendSmallNotification(String title, String content) {
        if (Build.VERSION.SDK_INT >= 26) {
            createNotificationChannel();
            Notification notification = getChannelNotification
                    (title, content).build();
            //notification.largeIcon = null;
            getManager().notify(1, notification);
        } else {
            Notification notification = getNotification_25(title, content).build();
            notification.largeIcon = null;
            getManager().notify(1, notification);
        }
    }



}
