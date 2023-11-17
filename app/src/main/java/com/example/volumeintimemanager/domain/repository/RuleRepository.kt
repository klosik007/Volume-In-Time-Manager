package com.example.volumeintimemanager.domain.repository

import com.example.volumeintimemanager.domain.model.Rule
import kotlinx.coroutines.flow.Flow

typealias Rules = List<Rule>

interface RuleRepository {
    fun getRulesFromRoom(): Flow<Rules>

    suspend fun getRuleFromRoom(id: Int): Rule

    suspend fun addRuleToRoom(rule: Rule)

    suspend fun updateRuleInRoom(rule: Rule)

    suspend fun deleteRuleFromRoom(rule: Rule)
}