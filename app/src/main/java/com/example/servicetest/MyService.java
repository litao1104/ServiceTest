package com.example.servicetest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class MyService extends Service {

    private static final String CHANNEL_NOTIFICATION = "channel_notification";

    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder {

        public void startDownload() {
            Log.d("litao", "MyService DownloadBinder startDownload");
        }

        public int getProgress() {
            Log.d("litao", "MyService DownloadBinder getProgress");
            return 0;
        }
    }

    public MyService() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("litao", "MyService onCreate");

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.deleteNotificationChannel(CHANNEL_NOTIFICATION);

        NotificationChannel channel = new NotificationChannel(CHANNEL_NOTIFICATION, "NotificationTestChannel", NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Descprtion of CHANNEL_NOTIFICATION");
        channel.enableLights(true);
        channel.setLightColor(Color.WHITE);
        channel.enableVibration(true);
        channel.setVibrationPattern(new long[] {0, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000});
        channel.enableLights(true);
        channel.setLightColor(Color.rgb(0,0,0));

        manager.createNotificationChannel(channel);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_NOTIFICATION)
                .setContentTitle("This is content title 1")
                .setContentText("This is content text 1")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pi)
                //.setSound(Uri.fromFile(new File("/system/media/audio/ringtones/ToyRobot.ogg")))
                //.setVibrate(new long[] {0, 3000, 0, 3000, 0, 3000})
                //.setAutoCancel(true)
                .setLights(Color.rgb(0,0,0), 1000, 1000)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Stack Overflow is an open community for anyone that codes. We help you get answers to your toughest coding questions, share knowledge with your coworkers in private, and find your next dream job."))
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("litao", "MyService onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onCreate();
        Log.d("litao", "MyService onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");

        return mBinder;
    }
}