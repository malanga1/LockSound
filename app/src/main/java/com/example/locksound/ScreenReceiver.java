package com.example.locksound;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)){
            Log.e("SCREEN", "User unlock the phone!");
        }
        }

    }

