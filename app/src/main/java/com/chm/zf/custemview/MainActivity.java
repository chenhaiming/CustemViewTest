package com.chm.zf.custemview;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity   {

    private NotificationManager manager;
    private Notification mNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//                - setContentTitle       设置标题
//                - setContentText        设置内容
//                - setLargeIcon          设置通知栏大图标
//                - setSmallIcon          设置通知栏小图标
//                - setContent            设置RemoteViews
//                - setContentIntent      当通知条目被点击，就执行这个被设置的Intent.
//                - setDeleteIntent       当用户点击"Clear All Notifications"按钮区删除所有的通知的时候，这个被设置的Intent被执行
//                - setLights             设置闪光灯
//                - setSound              设置声音
//                - setPriority           设置优先级

        RemoteViews mRemoteViews = new RemoteViews("com.chm.zf.custemview", R.layout.activity_main);

        //2.构建一个打开Activity的PendingIntent
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        PendingIntent mPendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mNotification = new Notification.Builder(this)
                .setContentTitle("this is title ")
                .setContentText("this is content")
                .setContentIntent(mPendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .build();
        //4.获取NotificationManager
        manager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);

//        findViewById(R.id.open).setOnClickListener(view -> manager.notify(1, mNotification));

//        statisticsView = (StatisticsView) findViewById(R.id.stat);
//        this.statisticsView.setBottomStr(new String[]{"星期一","星期二","星期三","星期四","星期五","星期六","星期七"});
//        this.statisticsView.setValues(new float[]{10f,90f,33f,66f,42f,100f,0f});

    }
}