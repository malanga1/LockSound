package com.example.locksound;


import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.hardware.display.DisplayManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;

public class MainActivity extends AppCompatActivity {




    private static final String TAG = "MainActivity";

    AudioManager audioManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SoundKeeper soundKeeper = new SoundKeeper(this);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        setVolumeControlStream(AudioManager.STREAM_SYSTEM);


        Intent intent = new Intent(this, DisplayService.class);
       // intent.putExtra("Sound", soundKeeper);

        startService(intent);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }






}
