package com.bkjk.infra.test.service_8;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.IBinder;

import com.bkjk.infra.test.R;

public class Mine2Service extends Service {
    public Mine2Service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("服务已创建");
        // 8.0以前
        Notification notification = new Notification.Builder(this)
                .setContentTitle("主服务")   //设置标题
                .setContentText("运行中...")   //设置内容
                .setWhen(System.currentTimeMillis())   //设置创建时间
                .setSmallIcon(R.mipmap.ic_launcher)   //设置状态栏图标
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))   //设置通知栏图标
                .build();
        startForeground(1, notification);

        // 8.0及以后
//        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        NotificationChannel Channel = new NotificationChannel("123", "主服务", NotificationManager.IMPORTANCE_HIGH);
//        Channel.enableLights(true);//设置提示灯
//        Channel.setLightColor(Color.RED);//设置提示灯颜色
//        Channel.setImportance(NotificationManager.IMPORTANCE_DEFAULT);
//        Channel.setShowBadge(true);//显示logo
//        Channel.setDescription("ytzn");//设置描述
//        Channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC); //设置锁屏可见 VISIBILITY_PUBLIC=可见
//        manager.createNotificationChannel(Channel);
//
//        Notification notification = new Notification.Builder(this)
//                .setChannelId("123")
//                .setContentTitle("主服务")
//                .setContentText("运行中...")
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
//                .build();
//        startForeground(1, notification);
    }
}
