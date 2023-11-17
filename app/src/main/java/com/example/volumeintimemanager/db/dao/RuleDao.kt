package com.example.volumeintimemanager.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
import com.example.volumeintimemanager.domain.model.Rule
import com.example.volumeintimemanager.domain.repository.Rules
import kotlinx.coroutines.flow.Flow

@Dao
interface RuleDao {
    @Query("SELECT * FROM rule_table")
    fun getRules(): Flow<Rules>

    @Query("SELECT * FROM rule_table WHERE id = :id")
    suspend fun getRule(id: Int): Rule

    @Insert(onConflict = IGNORE)
    suspend fun addRule(rule: Rule)

    @Update
    suspend fun updateRule(rule: Rule)

    @Delete
    suspend fun deleteRule(rule: Rule)
}