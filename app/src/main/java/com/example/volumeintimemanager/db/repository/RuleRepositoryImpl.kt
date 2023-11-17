package com.example.volumeintimemanager.db.repository

import com.example.volumeintimemanager.db.dao.RuleDao
import com.example.volumeintimemanager.domain.model.Rule
import com.example.volumeintimemanager.domain.repository.RuleRepository

class RuleRepositoryImpl (private val ruleDao: RuleDao): RuleRepository {
    override fun getRulesFromRoom() = ruleDao.getRules()

    override suspend fun getRuleFromRoom(id: Int) = ruleDao.getRule(id)

    override suspend fun addRuleToRoom(rule: Rule) = ruleDao.addRule(rule)

    override suspend fun updateRuleInRoom(rule: Rule) = ruleDao.updateRule(rule)

    override suspend fun deleteRuleFromRoom(rule: Rule) = ruleDao.deleteRule(rule)
}