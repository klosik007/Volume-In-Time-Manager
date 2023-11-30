package com.example.volumeintimemanager.alarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager

class SoundOffReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        val audioManager = p0?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.ringerMode = AudioManager.RINGER_MODE_VIBRATE

        showNotification(p0, "soundOff_alarm", "Sounds Off Alarms", 1000, "Sounds mode OFF (vibrations ON)")
    }
}