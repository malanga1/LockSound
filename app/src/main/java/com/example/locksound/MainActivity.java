package com.example.locksound;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.display.DisplayManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity {

    private SoundPool mSoundPool;
    private static final int MAX_STREAMS = 2;
    int soundId;

    AudioManager audioManager;
    private BroadcastReceiver mReceiver;
    DisplayManager displayManager;
    DisplayManager.DisplayListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mReceiver = new ScreenReciever();

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
        intentFilter.setPriority(999);

        registerReceiver(mReceiver, intentFilter);

        displayManager = (DisplayManager) getSystemService(DISPLAY_SERVICE);

        listener = new DisplayManager.DisplayListener() {

            int displayState;


            @Override
            public void onDisplayAdded(int displayId) {
                Log.e("SCREEN", "onDisplayAdded");
            }

            @Override
            public void onDisplayRemoved(int displayId) {
                Log.e("SCREEN", "onDisplayRemoved");
            }

            @Override
            public void onDisplayChanged(int displayId) {
                int currentState = displayManager.getDisplay(displayId).getState();
                Log.e("SCREEN", "Curr: " + currentState);
                if (currentState != displayState && currentState != Display.STATE_ON) {

                    Log.e("SCREEN", "Screen is off/on");
                    playSound();


                }
                displayState = currentState;
            }
        };

        displayManager.registerDisplayListener(listener, null);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        setVolumeControlStream(AudioManager.STREAM_SYSTEM);

        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(getAttributes())
                .setMaxStreams(MAX_STREAMS)
                .build();

        soundId = mSoundPool.load(this, R.raw.lockkkkkkk, 1);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        displayManager.unregisterDisplayListener(listener);
    }

    public void playSound() {

        float curVolume = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
        float maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
        float leftVolume = curVolume / maxVolume;
        float rightVolume = curVolume / maxVolume;

        mSoundPool.play(soundId,
                leftVolume,
                rightVolume,
                1,
                0,
                /*Скорость воспроизведения*/1.0f);
    }


    private AudioAttributes getAttributes() {

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        return audioAttributes;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e("SCREEN", "onKeyDown");
        if (keyCode == KeyEvent.KEYCODE_POWER) {
            Log.e("SCREEN", "onKeyDown.Power");
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.e("SCREEN", "dispatchKeyEvent");
        if (event.getKeyCode() == KeyEvent.KEYCODE_POWER) {
            Log.e("SCREEN", "dispatchKeyEvent.Power");
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

}
