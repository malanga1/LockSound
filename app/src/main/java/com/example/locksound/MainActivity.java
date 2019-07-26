package com.example.locksound;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity {

    private SoundPool mSoundPool;

    private static final int MAX_STREAMS = 2;

    int soundId;

    AudioManager audioManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        setVolumeControlStream(AudioManager.STREAM_SYSTEM);

        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(getAttributes())
                .setMaxStreams(MAX_STREAMS)
                .build();

        soundId = mSoundPool.load(this, R.raw.lockkkkkkk, 1);
    }

    public void playSound(View view){


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



    private AudioAttributes getAttributes(){

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        return  audioAttributes;
    }

}
