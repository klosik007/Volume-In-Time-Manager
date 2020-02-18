package com.example.volumeintimemanager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmFireOnDeviceBoot extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            Toast.makeText(context, "Alarm on boot fired", Toast.LENGTH_LONG).show();
        }
    }
}
