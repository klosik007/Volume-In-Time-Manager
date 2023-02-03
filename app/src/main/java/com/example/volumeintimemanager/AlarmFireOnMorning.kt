package com.example.volumeintimemanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmFireOnMorning : BroadcastReceiver() {
    private var _mode = 0

    //    public AlarmFireOnMorning(int mode){
    //        _mode = mode;
    //    }
    override fun onReceive(context: Context, intent: Intent) {
        _mode = MainActivity.passRingerFromData()
        val main = MainActivity()
        main.setRingMode(_mode, context)
        Toast.makeText(context, "Morning alarm fired", Toast.LENGTH_LONG).show()
    }
}