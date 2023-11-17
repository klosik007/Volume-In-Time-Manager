package com.example.volumeintimemanager.db.network

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.volumeintimemanager.domain.model.Rule
import com.example.volumeintimemanager.db.dao.RuleDao

@Database(entities = [Rule::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun ruleDao(): RuleDao
}