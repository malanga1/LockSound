package com.example.locksound;


import android.content.Context;
import android.content.Intent;

import android.media.AudioManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {




    private static final String TAG = "MainActivity";

    AudioManager audioManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        setVolumeControlStream(AudioManager.STREAM_SYSTEM);

        Intent intent = new Intent(this, DisplayService.class);

        startService(intent);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }






}
