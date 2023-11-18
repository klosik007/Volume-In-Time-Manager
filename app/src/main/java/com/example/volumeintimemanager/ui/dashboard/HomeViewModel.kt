package com.example.volumeintimemanager.ui.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.volumeintimemanager.domain.model.Rule
import com.example.volumeintimemanager.domain.repository.RuleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: RuleRepository): ViewModel() {
    var rule by mutableStateOf(
        Rule(
            id = 0, timeFrom = "", timeTo = "",
            monday = false,  tuesday = false,
            wednesday = false,  thursday = false,
            friday = false,  saturday = false,
            sunday = false,  soundOn = false
        )
    ); private set

    var openDialog by mutableStateOf(false)

    val rules = repo.getRulesFromRoom()

    fun getRule(id: Int) = viewModelScope.launch {
        rule = repo.getRuleFromRoom(id)
    }

    fun addRule(rule: Rule) = viewModelScope.launch {
        repo.addRuleToRoom(rule)
    }

    fun updateRule(rule: Rule) = viewModelScope.launch {
        repo.updateRuleInRoom(rule)
    }

    fun deleteRule(rule: Rule) = viewModelScope.launch {
        repo.deleteRuleFromRoom(rule)
    }

    fun openDialog() {
        openDialog = true
    }

    fun closeDialog() {
        openDialog = false
    }
}