package com.example.volumeintimemanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmFireOnDeviceBoot : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            Toast.makeText(context, "Alarm on boot fired", Toast.LENGTH_LONG).show()
        }
    }
}