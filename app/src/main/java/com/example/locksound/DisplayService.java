package com.example.locksound;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Display;


public class DisplayService extends Service {

    private static final String TAG = "DisplayService";

    DisplayManager displayManager;
    DisplayManager.DisplayListener listener;
    KeyguardManager mKeyguardManager;
    ScreenReceiver mScreenReceiver;

    SoundKeeper mSoundKeeper;


    public DisplayService() {
        Log.e(TAG, "onTaskRemoved");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mScreenReceiver = new ScreenReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_USER_PRESENT);
        registerReceiver(mScreenReceiver, intentFilter);

        displayManager = (DisplayManager) getSystemService(DISPLAY_SERVICE);

        mKeyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

        listener = new DisplayManager.DisplayListener() {

            int displayState;

            @Override
            public void onDisplayAdded(int displayId) {

            }

            @Override
            public void onDisplayRemoved(int displayId) {

            }

            @Override
            public void onDisplayChanged(int displayId) {
                int currentState = displayManager.getDisplay(displayId).getState();

                if (currentState != displayState && currentState != Display.STATE_ON) {
                    if(!mKeyguardManager.inKeyguardRestrictedInputMode()){
                        Log.e(TAG, "Screen was locked");
                        mSoundKeeper.playSound();
                    }



                }
                displayState = currentState;
            }
        };


        displayManager.registerDisplayListener(listener, null);

        mSoundKeeper = SoundKeeper.getInstance(this);

        startForeground(777, getServiceNotification());

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
       // mSoundKeeper =  intent.getParcelableExtra("Sound");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
        displayManager.unregisterDisplayListener(listener);
        unregisterReceiver(mScreenReceiver);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.e(TAG, "onTaskRemoved");
    }

    public Notification getServiceNotification(){

        Log.i(TAG, "getServiceNotification");

        NotificationChannel channel;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "Default";
            String description = "Just default";
            int importance = NotificationManager.IMPORTANCE_MIN;
            channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Notification.Builder builder;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(this, "1");
        } else {
          builder = new Notification.Builder(this);

        }
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        Notification notification;
            notification = builder.build();
        return notification;
    }



}
