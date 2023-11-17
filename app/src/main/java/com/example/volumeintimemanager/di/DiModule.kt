package com.example.volumeintimemanager.di

import android.content.Context
import androidx.room.Room
import com.example.volumeintimemanager.db.dao.RuleDao
import com.example.volumeintimemanager.db.network.AppDatabase
import com.example.volumeintimemanager.db.repository.RuleRepositoryImpl
import com.example.volumeintimemanager.domain.repository.RuleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DiModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "rule_table").build()

    @Provides
    fun provideRuleDao(ruleDb: AppDatabase) = ruleDb.ruleDao()

    @Provides
    fun provideRuleRepository(ruleDao: RuleDao): RuleRepository = RuleRepositoryImpl(ruleDao)
}