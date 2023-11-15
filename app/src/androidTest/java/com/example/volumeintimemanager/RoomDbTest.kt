package com.example.volumeintimemanager

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.volumeintimemanager.db.AppDatabase
import com.example.volumeintimemanager.db.Rule
import com.example.volumeintimemanager.db.RuleDao
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RoomDbTest {
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        ruleDao = db.ruleDao()

        ruleDao.addRules(rule1, rule2)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun rule1CheckSoundOn() {
        val ruleById = ruleDao.getRule(1)
        Assert.assertEquals(ruleById.soundOn, true)
    }

    @Test
    @Throws(Exception::class)
    fun getAllRulesAndCheckCount() {
        val rulesCount = ruleDao.getRules().size
        Assert.assertEquals(rulesCount, 2)
    }

    @Test
    @Throws(Exception::class)
    fun updateRule() {
        val newRule1Data = Rule(1, "8:20", "16:00", monday = true,
            tuesday = true,
            wednesday = true,
            thursday = true,
            friday = true,
            saturday = false,
            sunday = false, true)
        ruleDao.updateRule(newRule1Data)

        val updatedRule = ruleDao.getRule(1)
        Assert.assertEquals(updatedRule.timeFrom, "8:20")
    }

    @Test
    @Throws(Exception::class)
    fun deleteRule() {
        ruleDao.deleteRule(rule1)
        val rulesCount = ruleDao.getRules().size
        
        Assert.assertEquals(rulesCount, 1)
    }

    private lateinit var ruleDao: RuleDao
    private lateinit var db: AppDatabase

    private val rule1 = Rule(1, "8:00", "16:00",
        monday = true,
        tuesday = true,
        wednesday = true,
        thursday = true,
        friday = true,
        saturday = true,
        sunday = true, true)
    private val rule2 = Rule(2, "16:01", "7:59",
        monday = true,
        tuesday = true,
        wednesday = true,
        thursday = true,
        friday = true,
        saturday = true,
        sunday = true, false)
}