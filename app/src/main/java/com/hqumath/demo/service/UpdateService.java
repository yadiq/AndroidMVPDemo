package com.hqumath.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 版本更新 服务
 */

public class UpdateService extends Service {

    public UpdateService() {
    }

    @Override
    public void onCreate() {
        /*manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Log.i("Myservice","onCreate");
        //启动运行该服务的线程。因为默认情况下服务通常运行在进程的主线程中，我们不希望阻塞主线程。所以创建了一个单独的线程，
        // 我们还将其设置为后台优先级，这样cpu密集型工作就不会破坏我们的UI。
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);*/
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 调用下载
        //initDownManager();
        //请求你的服务在前台运行

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent,
                        PendingIntent.FLAG_IMMUTABLE);

        Notification notification =
                null;

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        // 注销下载广播
//        if (receiver != null)
//            unregisterReceiver(receiver);
        super.onDestroy();
    }

}