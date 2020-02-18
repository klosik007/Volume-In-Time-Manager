package com.example.volumeintimemanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;

public class AlarmFireOnEvening extends BroadcastReceiver {
    private int _mode;
//    public AlarmFireOnEvening(int mode){
//        _mode = mode;
//    }
    @Override
    public void onReceive(Context context, Intent intent){
        _mode = MainActivity.passRingerToData();

        MainActivity main = new MainActivity();
        main.setRingMode(_mode);
        Toast.makeText(context, "Evening alarm fired", Toast.LENGTH_LONG).show();
    }
}
