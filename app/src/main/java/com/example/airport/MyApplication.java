package com.example.airport;


import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class MyApplication extends Application {

    private BeaconManager beaconManager;
    private DBAdapter dbAdapter;

    @Override
    public void onCreate() {
        super.onCreate();

        dbAdapter = new DBAdapter(this);

//
//        System.out.println(data.get("id"));
//        System.out.println(data.get("title"));
//        System.out.println(data.get("message"));

        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {

            Random random = new Random();
            int randomNum = random.nextInt(10) + 1;
            Map<String, String> data= dbAdapter.getUser(randomNum);

            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                showNotification(
                        data.get("title"),
                        data.get("message"));
            }
            @Override
            public void onExitedRegion(Region region) {
                // could add an "exit" notification too if you want (-:
            }
        });
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new Region("monitored region",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null));
            }
        });
    }

    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        final Notification.Builder builder = new Notification.Builder(this);
        builder.setStyle(new Notification.BigTextStyle(builder)
                .bigText(message)
                .setBigContentTitle(title))
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message);
//                .setAutoCancel(true)
//                .setContentIntent(pendingIntent)
//        builder.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
