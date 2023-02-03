package com.example.volumeintimemanager

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.volumeintimemanager.AddRule
import org.json.JSONException
import org.json.JSONObject
import java.lang.Boolean
import java.util.*
import kotlin.Array
import kotlin.Int
import kotlin.RuntimeException
import kotlin.String

class MainActivity : AppCompatActivity() {
    var layout: RelativeLayout? = null

    //ScrollView scrollView;
    var audioMgr: AudioManager? = null
    var toggle_switch: Switch? = null
    fun addRule(view: View?) {
        val intent = Intent(this, AddRule::class.java)
        startActivity(intent)
    }

    fun editRule(view: View?) {
        if (id != 0) {
            val intent = Intent(this, EditRule::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Select rule to edit!", Toast.LENGTH_LONG).show()
        }
    }

    private fun loadRulesFromJSON(json: String?): List<List<String>>? {
        return try {
            val previousJSONObj = JSONObject(json)
            val obj = previousJSONObj.getJSONArray("rules")
            val objLength = obj.length()
            val listOfLists: MutableList<List<String>> = ArrayList()
            for (i in 0 until objLength) {
                val rule = obj.getJSONObject(i)
                val jsonList: MutableList<String> = ArrayList()
                jsonList.add(Integer.toString(rule.optInt("id")))
                jsonList.add(rule.optString("timeFrom"))
                jsonList.add(rule.optString("timeTo"))
                jsonList.add(rule.optString("daysOfWeek"))
                jsonList.add(Integer.toString(rule.optInt("spinnerChoice")))
                jsonList.add(Boolean.toString(rule.optBoolean("isRuleInUse")))
                listOfLists.add(jsonList)
            }
            listOfLists
        } catch (e: JSONException) {
            e.printStackTrace()
            null
        }
    }

    private fun displayRules(rules: List<List<String>>?) {
        if (!rules!!.isEmpty()) {
            //scrollView = (ScrollView)findViewById(R.id.scrollView2);
            layout = findViewById<View>(R.id.relativeLayout) as RelativeLayout
            layout!!.invalidate()
            val userInterface = UserInterfaceHandler(this@MainActivity)
            val layoutsList: MutableList<LinearLayout> = ArrayList()
            for (rl in rules) {
                layoutsList.add(
                    userInterface.createLinearLayoutForRule(
                        rl[0].toInt(),
                        Boolean.parseBoolean(rl[5]),
                        rl[1],
                        rl[2],
                        rl[3],
                        rl[4].toInt()
                    )
                )
            }
            val params: MutableList<RelativeLayout.LayoutParams> = ArrayList()
            val rulesListSize = rules.size
            for (i in 0 until rulesListSize) {
                params.add(
                    RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                    )
                )
            }
            for (i in 1 until params.size) {
                params[i].addRule(RelativeLayout.BELOW, layoutsList[i - 1].id)
            }
            for (ll in layoutsList) {
                ll.id = id
                ll.setBackgroundColor(Color.TRANSPARENT)
                ll.setOnClickListener {
                    if ((ll.background as ColorDrawable).color == Color.TRANSPARENT) {
                        ll.setBackgroundColor(Color.LTGRAY)
                        id = ll.id
                        Log.d("onClickListener", id.toString())
                    } else ll.setBackgroundColor(Color.TRANSPARENT)
                }
                id++
            }
            val layoutsListSize = layoutsList.size
            for (i in 1 until layoutsListSize) {
                layout!!.addView(layoutsList[i], params[i])
            }
        }
    }

    fun setRingMode(ringerMode: Int, context: Context) {
        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
            && !notificationManager.isNotificationPolicyAccessGranted
        ) {
            val intent = Intent(
                Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS
            )
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
        audioMgr = context.getSystemService(AUDIO_SERVICE) as AudioManager
        var mode = ""
        when (ringerMode) {
            AudioManager.RINGER_MODE_VIBRATE -> mode = "Vibrations On"
            AudioManager.RINGER_MODE_NORMAL -> mode = "Normal Mode On"
        }
        Toast.makeText(
            context, mode,
            Toast.LENGTH_LONG
        ).show()
        audioMgr!!.ringerMode = ringerMode
    }

    private fun scheduleRule(
        dayOfWeek: Int, hourFrom: Int, minuteFrom: Int,
        hourTo: Int, minuteTo: Int, ringerModeFrom: Int, ringerModeTo: Int, requestCode: Int
    ) {
        val alarmMgr = this.getSystemService(ALARM_SERVICE) as AlarmManager

        //AlarmFireOnMorning alrmOnMorning = new AlarmFireOnMorning(ringerModeFrom);
        setRingerFromData(ringerModeFrom)
        val morningIntent = Intent(this, AlarmFireOnMorning::class.java)
        val morningAlarm = PendingIntent.getBroadcast(this, requestCode, morningIntent, 0)
        val calendarTimeFrom = Calendar.getInstance()
        calendarTimeFrom[Calendar.HOUR_OF_DAY] = hourFrom
        calendarTimeFrom[Calendar.MINUTE] = minuteFrom
        calendarTimeFrom[Calendar.SECOND] = 0
        calendarTimeFrom[Calendar.DAY_OF_WEEK] = dayOfWeek
        val timeNow = Calendar.getInstance()
        if (timeNow.after(calendarTimeFrom)) {
            calendarTimeFrom.add(Calendar.HOUR_OF_DAY, 24)
        }
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendarTimeFrom.timeInMillis, 604800000, morningAlarm)

        //AlarmFireOnEvening alrmOnEvening = new AlarmFireOnEvening(ringerModeTo);
        setRingerToData(ringerModeTo)
        val eveningIntent = Intent(this, AlarmFireOnEvening::class.java)
        val eveningAlarm = PendingIntent.getBroadcast(this, requestCode + 1, eveningIntent, 0)
        val calendarTimeTo = Calendar.getInstance()
        calendarTimeTo[Calendar.HOUR_OF_DAY] = hourTo
        calendarTimeTo[Calendar.MINUTE] = minuteTo
        calendarTimeTo[Calendar.SECOND] = 0
        calendarTimeTo[Calendar.DAY_OF_WEEK] = dayOfWeek
        if (timeNow.after(calendarTimeTo)) {
            calendarTimeTo.add(Calendar.HOUR_OF_DAY, 24)
        }
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendarTimeTo.timeInMillis, 604800000, eveningAlarm)
    }

    private fun fireRules(rules: List<List<String>>?) {
//        scheduleRule(3, 22, 38, 22, 39, AudioManager.RINGER_MODE_NORMAL, AudioManager.RINGER_MODE_SILENT,0);
        //scheduleRule(3, 22, 1, 51, AudioManager.RINGER_MODE_NORMAL, AudioManager.RINGER_MODE_SILENT,0);
//        scheduleRule(3, 18, 32, AudioManager.RINGER_MODE_SILENT, 1);

        //if in use - scheduleRule
        //if not use - cancel rule
        //get hour, minute, day and type of ringer mode from each rule -> save them to list (?)
        //dynamically schedule rules
        var daysOfWeek: Array<String>
        var timeFrom: Array<String>
        var timeTo: Array<String>
        var dayOfWeek: Int
        var hourFrom: Int
        var minuteFrom: Int
        var hourTo: Int
        var minuteTo: Int
        var mode: Int
        var reqCode = 0
        val rulesListSize = rules!!.size
        for (i in 1 until rulesListSize) {
            val underList = rules[i]
            daysOfWeek = underList[3].split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val daysOfWeekLen = daysOfWeek.size
            for (j in 0 until daysOfWeekLen) {
                timeFrom = underList[1].split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                timeTo = underList[2].split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                hourFrom = timeFrom[0].toInt()
                minuteFrom = timeFrom[1].toInt()
                hourTo = timeTo[0].toInt()
                minuteTo = timeTo[1].toInt()
                dayOfWeek = daysOfWeek[j].toInt()
                mode = underList[4].toInt()
                if (mode == 0) {
                    scheduleRule(
                        dayOfWeek, hourFrom, minuteFrom, hourTo, minuteTo,
                        AudioManager.RINGER_MODE_VIBRATE, AudioManager.RINGER_MODE_NORMAL, reqCode
                    )
                } else if (mode == 1) {
                    scheduleRule(
                        dayOfWeek, hourFrom, minuteFrom, hourTo, minuteTo,
                        AudioManager.RINGER_MODE_NORMAL, AudioManager.RINGER_MODE_VIBRATE, reqCode
                    )
                }
                reqCodeList.add(reqCode)
                reqCodeList.add(reqCode + 1)
                reqCode += 2
            }
        }
    }

    private fun cancelFiringRule(json: String, index: Int) {
        //detect if inUse is false - if so, cancel scheduling a rule
//        try{
//            JSONObject previousJSONObj = new JSONObject(json);
//            JSONArray obj = previousJSONObj.getJSONArray("rules");
//            int objLength = obj.length();
//
//                JSONObject rule = obj.getJSONObject(index);
//                List<String> jsonList = new ArrayList<>();
//                jsonList.add(Integer.toString(rule.optInt("id")));
//                jsonList.add(rule.optString("timeFrom"));
//                jsonList.add(rule.optString("timeTo"));
//                jsonList.add(rule.optString("daysOfWeek"));
//                jsonList.add(Integer.toString(rule.optInt("spinnerChoice")));
//                jsonList.add(Boolean.toString((rule.optBoolean("isRuleInUse"))));
//                listOfLists.add(jsonList);
//        }catch (JSONException e){
//            e.printStackTrace();
//        }
    }

    //    private void detectRingMode(){
    //        int mod=audioMgr.getRingerMode();
    //        if(mod==AudioManager.RINGER_MODE_VIBRATE){
    //            Toast.makeText(MainActivity.this,"Now in Vibrate Mode",
    //                    Toast.LENGTH_LONG).show();
    //        } else if(mod==AudioManager.RINGER_MODE_NORMAL){
    //            Toast.makeText(MainActivity.this,"Now in Ringing Mode",
    //                    Toast.LENGTH_LONG).show();
    //        } else {
    //            Toast.makeText(MainActivity.this,"Now in Vibrate Mode",
    //                    Toast.LENGTH_LONG).show();
    //        }
    //    }
    private fun cancelAllRules() {
        val alarmMgr = this.getSystemService(ALARM_SERVICE) as AlarmManager
        if (reqCodeList.isEmpty()) {
            Toast.makeText(applicationContext, "No rules to cancel!", Toast.LENGTH_LONG).show()
        } else {
            for (reqCode in reqCodeList) {
                if (reqCode / 2 == 0) {
                    val morningIntent2 = Intent(this, AlarmFireOnMorning::class.java)
                    val morningAlarm2 = PendingIntent.getBroadcast(this, reqCode, morningIntent2, 0)
                    alarmMgr.cancel(morningAlarm2)
                    Toast.makeText(applicationContext, "Alarm Cancelled - $reqCode", Toast.LENGTH_LONG).show()
                } else {
                    val eveningIntent2 = Intent(this, AlarmFireOnEvening::class.java)
                    val eveningAlarm2 = PendingIntent.getBroadcast(this, reqCode, eveningIntent2, 0)
                    alarmMgr.cancel(eveningAlarm2)
                    Toast.makeText(applicationContext, "Alarm Cancelled - $reqCode", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun loadFileIfIsAvailable() {
        val fileHandler = FilesHandler(this@MainActivity)
        //managing JSONs reference
        //JSONFilesHandler JSONHandler = new JSONFilesHandler(MainActivity.this);
        val jsonString = fileHandler.readFile("rules.json")
        Log.d("MainActivity", jsonString!!)
        //read rules from file and display them in main window on start
        val rls = loadRulesFromJSON(jsonString)
        displayRules(rls)
        fireRules(rls)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        //file with rules reference
        val fileHandler = FilesHandler(this@MainActivity)
        //        //managing JSONs reference
//        //JSONFilesHandler JSONHandler = new JSONFilesHandler(MainActivity.this);
        val switchListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                cancelAllRules()
            } else {
                loadFileIfIsAvailable()
            }
        }
        val isFileAvailable = fileHandler.isFileAvailable("rules.json")
        if (isFileAvailable) {
            loadFileIfIsAvailable()
        } else {
            //first launch of app - create empty rules.json file
            val isFileCreated = fileHandler.createFile(
                "rules.json",
                "{\"rules\":[{\"id\":0,\"timeFrom\":\"\",\"timeTo\":\"\",\"daysOfWeek\":\"\",\"spinnerChoice\":-1,\"isRuleInUse\":false}]}"
            )
            if (isFileCreated) {
                Toast.makeText(this@MainActivity, "File rules.json created", Toast.LENGTH_LONG).show()
            } else {
                throw RuntimeException() //throw exception - not sure if in proper way
            }
        }
        toggle_switch = findViewById<View>(R.id.switch1) as Switch
        toggle_switch!!.setOnCheckedChangeListener(switchListener)
    } //onActivityResult()

    companion object {
        private var id = 0
        private var ringerFromData = 0
        private var ringerToData = 0
        private val reqCodeList: MutableList<Int> = ArrayList()
        fun passIdData(): Int {
            return id
        }

        fun passRingerFromData(): Int {
            return ringerFromData
        }

        fun passRingerToData(): Int {
            return ringerToData
        }

        fun setRingerFromData(ringer: Int) {
            ringerFromData = ringer
        }

        fun setRingerToData(ringer: Int) {
            ringerToData = ringer
        }
    }
}