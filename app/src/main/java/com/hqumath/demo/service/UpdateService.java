package com.hqumath.demo.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.IBinder;

import com.hqumath.demo.R;

/**
 * 版本更新 服务
 */

public class UpdateService extends Service {

    public UpdateService() {
    }

    @Override
    public void onCreate() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //构建Notification
        Notification.Builder notificationBuilder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder = new Notification.Builder(this, "channelId1");
        } else {
            notificationBuilder = new Notification.Builder(this);
        }
        Notification notification = notificationBuilder
                .setContentTitle("title")
                .setContentText("message")
                .setSmallIcon(R.mipmap.ic_launcher)
                //.setLargeIcon() //设置下拉列表中的图标(大图标)
                //.setWhen(System.currentTimeMillis()) // 设置该通知发生的时间
                //.setContentIntent(pendingIntent) //点击跳转
                //.setTicker("ticker")
                .build();
        //提升为前台服务
        int NOTIFICATION_ID = 100;//唯一的通知标识, 不能为0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(NOTIFICATION_ID, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_REMOTE_MESSAGING);
        } else {
            startForeground(NOTIFICATION_ID, notification);
        }
        //return START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        //return null;
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        stopForeground(true);//取消前台运行状态
        super.onDestroy();
    }

}