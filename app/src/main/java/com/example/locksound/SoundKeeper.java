package com.example.locksound;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;


public class SoundKeeper {

    private SoundPool mSoundPool;
    private static final int MAX_STREAMS = 2;
    int soundId;
    private static SoundKeeper instance;

    private SoundKeeper(Context context){
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(getAttributes())
                .setMaxStreams(MAX_STREAMS)
                .build();

        soundId = mSoundPool.load(context, R.raw.lockkkkkkk, 1);
    }

    public static SoundKeeper getInstance(Context context){
        if(instance == null){
            instance = new SoundKeeper(context);
        }
        return instance;
    }

    private AudioAttributes getAttributes() {

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        return audioAttributes;
    }

    public void playSound() {

        //float curVolume = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
        //float maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
        //float leftVolume = curVolume / maxVolume;
        //float rightVolume = curVolume / maxVolume;

        mSoundPool.play(soundId,
                0.1f,
                0.1f,
                1,
                0,
                /*Скорость воспроизведения*/1.0f);
    }

}
