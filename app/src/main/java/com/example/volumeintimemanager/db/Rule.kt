package com.example.volumeintimemanager.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime
import java.util.Date

@Entity(tableName = "rule")
data class Rule(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "TIME_FROM") val timeFrom: String,
    @ColumnInfo(name = "TIME_TO") val timeTo: String,
    @ColumnInfo(name = "WEEK_DAYS") val weekDays: String,
    @ColumnInfo(name = "SOUND_ON") val soundOn: Boolean
)
