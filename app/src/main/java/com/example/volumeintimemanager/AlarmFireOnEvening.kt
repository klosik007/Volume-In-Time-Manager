package com.example.volumeintimemanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmFireOnEvening : BroadcastReceiver() {
    private var _mode = 0

    //    public AlarmFireOnEvening(int mode){
    //        _mode = mode;
    //    }
    override fun onReceive(context: Context, intent: Intent) {
        _mode = MainActivity.passRingerToData()
        val main = MainActivity()
        main.setRingMode(_mode, context)
        Toast.makeText(context, "Evening alarm fired", Toast.LENGTH_LONG).show()
    }
}