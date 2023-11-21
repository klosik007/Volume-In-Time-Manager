package com.example.volumeintimemanager.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rule_table")
data class Rule(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "APPLY_RULE") var applyRule: Boolean,
    @ColumnInfo(name = "TIME_FROM") var timeFrom: String,
    @ColumnInfo(name = "TIME_TO") var timeTo: String,
    @ColumnInfo(name = "APPLY_ON_MONDAY") var monday: Boolean = false,
    @ColumnInfo(name = "APPLY_ON_TUESDAY") var tuesday: Boolean = false,
    @ColumnInfo(name = "APPLY_ON_WEDNESDAY") var wednesday: Boolean = false,
    @ColumnInfo(name = "APPLY_ON_THURSDAY") var thursday: Boolean = false,
    @ColumnInfo(name = "APPLY_ON_FRIDAY") var friday: Boolean = false,
    @ColumnInfo(name = "APPLY_ON_SATURDAY") var saturday: Boolean = false,
    @ColumnInfo(name = "APPLY_ON_SUNDAY") var sunday: Boolean = false,
    @ColumnInfo(name = "SOUND_ON") var soundOn: Boolean
)
