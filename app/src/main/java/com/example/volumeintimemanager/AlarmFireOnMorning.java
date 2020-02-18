package com.example.volumeintimemanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;

public class AlarmFireOnMorning extends BroadcastReceiver {
    private int _mode;
//    public AlarmFireOnMorning(int mode){
//        _mode = mode;
//    }
    @Override
    public void onReceive(Context context, Intent intent){
        _mode = MainActivity.passRingerFromData();

        MainActivity main = new MainActivity();
        main.setRingMode(_mode, context);
        Toast.makeText(context, "Morning alarm fired", Toast.LENGTH_LONG).show();
    }
}
