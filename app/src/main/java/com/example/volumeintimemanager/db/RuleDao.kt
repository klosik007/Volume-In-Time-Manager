package com.example.volumeintimemanager.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RuleDao {
    @Query("SELECT * FROM rule")
    fun getRules(): Array<Rule>

    @Query("SELECT * FROM rule WHERE id = :id")
    fun getRule(id: Int): Rule

    @Insert
    fun addRules(vararg rule: Rule)

    @Update
    fun updateRule(rule: Rule)

    @Delete
    fun deleteRule(rule: Rule)
}