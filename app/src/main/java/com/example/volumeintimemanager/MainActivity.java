package com.example.volumeintimemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RelativeLayout layout;
    AudioManager audioMgr;
    Switch toggle_switch;
    private static int id = 0;
    private static int ringerFromData = 0;
    private static int ringerToData = 0;
    private static List<Integer> reqCodeList = new ArrayList<>();

    public static int passIdData(){
        return id;
    }
    public static int passRingerFromData(){ return ringerFromData; }
    public static int passRingerToData(){ return ringerToData; }

    public static void setRingerFromData(int ringer){ ringerFromData = ringer;}
    public static void setRingerToData(int ringer){ ringerToData = ringer;}


    public void addRule(View view){
        Intent intent = new Intent(this, AddRule.class);
        startActivity(intent);
    }

    public void editRule(View view){
        if (id != 0){
            Intent intent = new Intent(this, EditRule.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Select rule to edit!", Toast.LENGTH_LONG).show();
        }
    }

    private List<List<String>> loadRulesFromJSON(String json){
        try{
            JSONObject previousJSONObj = new JSONObject(json);
            JSONArray obj = previousJSONObj.getJSONArray("rules");
            int objLength = obj.length();

            List<List<String>> listOfLists = new ArrayList<>();
            for (int i = 0; i < objLength; i++){
                JSONObject rule = obj.getJSONObject(i);
                List<String> jsonList = new ArrayList<>();
                jsonList.add(Integer.toString(rule.optInt("id")));
                jsonList.add(rule.optString("timeFrom"));
                jsonList.add(rule.optString("timeTo"));
                jsonList.add(rule.optString("daysOfWeek"));
                jsonList.add(Integer.toString(rule.optInt("spinnerChoice")));
                jsonList.add(Boolean.toString((rule.optBoolean("isRuleInUse"))));
                listOfLists.add(jsonList);
            }

            return listOfLists;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    private void displayRules(List<List<String>> rules){
        if (!rules.isEmpty()){
            layout = (RelativeLayout)findViewById(R.id.relativeLayout);
            layout.invalidate();

            UserInterfaceHandler userInterface = new UserInterfaceHandler(MainActivity.this);
            List<LinearLayout> layoutsList = new ArrayList<>();

            for (List<String> rl : rules){
                layoutsList.add(userInterface.createLinearLayoutForRule(Integer.parseInt(rl.get(0)), Boolean.parseBoolean(rl.get(5)), rl.get(1), rl.get(2), rl.get(3), Integer.parseInt(rl.get(4))));
            }

            List<RelativeLayout.LayoutParams> params = new ArrayList<>();
            int rulesListSize = rules.size();
            for (int i = 0; i < rulesListSize; i++){
                params.add(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            }

            for (int i = 1; i < params.size(); i++) {
                params.get(i).addRule(RelativeLayout.BELOW, layoutsList.get(i-1).getId());
            }

            for (final LinearLayout ll : layoutsList){
                ll.setId(id);
                ll.setBackgroundColor(Color.TRANSPARENT);
                ll.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        if(((ColorDrawable) ll.getBackground()).getColor() == Color.TRANSPARENT){
                            ll.setBackgroundColor(Color.LTGRAY);
                            id = ll.getId();
                            Log.d("onClickListener", String.valueOf(id));
                        } else
                            ll.setBackgroundColor(Color.TRANSPARENT);
                    }
                });
                id++;
            }

            int layoutsListSize = layoutsList.size();
            for (int i = 1; i < layoutsListSize; i++){
                layout.addView(layoutsList.get(i), params.get(i));
            }
        }
    }

    public void setRingMode(int ringerMode){
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {

            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

            startActivity(intent);
        }

        audioMgr = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        String mode = "";
        switch (ringerMode){
            case AudioManager.RINGER_MODE_VIBRATE:
                mode = "Vibrations On";
                break;
            case AudioManager.RINGER_MODE_NORMAL:
                mode = "Normal Mode On";
                break;
        }
        Toast.makeText(MainActivity.this, mode,
                Toast.LENGTH_LONG).show();
        audioMgr.setRingerMode(ringerMode);
    }

    private void scheduleRule(int dayOfWeek, int hourFrom, int minuteFrom,
                              int hourTo, int minuteTo, int ringerModeFrom, int ringerModeTo, int requestCode){
        AlarmManager alarmMgr = (AlarmManager)(this.getSystemService(Context.ALARM_SERVICE));

        //AlarmFireOnMorning alrmOnMorning = new AlarmFireOnMorning(ringerModeFrom);
        setRingerFromData(ringerModeFrom);
        Intent morningIntent = new Intent(this, AlarmFireOnMorning.class);
        PendingIntent morningAlarm = PendingIntent.getBroadcast(this, requestCode, morningIntent, 0);

        Calendar calendarTimeFrom = Calendar.getInstance();
        calendarTimeFrom.set(Calendar.HOUR_OF_DAY, hourFrom);
        calendarTimeFrom.set(Calendar.MINUTE, minuteFrom);
        calendarTimeFrom.set(Calendar.SECOND, 0);
        calendarTimeFrom.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        Calendar timeNow = Calendar.getInstance();

        if(timeNow.after(calendarTimeFrom)){
            calendarTimeFrom.add(Calendar.HOUR_OF_DAY, 24);
        }

        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendarTimeFrom.getTimeInMillis(), 604800000, morningAlarm);

        //AlarmFireOnEvening alrmOnEvening = new AlarmFireOnEvening(ringerModeTo);
        setRingerToData(ringerModeTo);
        Intent eveningIntent = new Intent(this, AlarmFireOnEvening.class);
        PendingIntent eveningAlarm = PendingIntent.getBroadcast(this, requestCode + 1, eveningIntent, 0);
        Calendar calendarTimeTo = Calendar.getInstance();
        calendarTimeTo.set(Calendar.HOUR_OF_DAY, hourTo);
        calendarTimeTo.set(Calendar.MINUTE, minuteTo);
        calendarTimeTo.set(Calendar.SECOND, 0);
        calendarTimeTo.set(Calendar.DAY_OF_WEEK, dayOfWeek);

        if(timeNow.after(calendarTimeTo)){
            calendarTimeTo.add(Calendar.HOUR_OF_DAY, 24);
        }

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendarTimeTo.getTimeInMillis(), 604800000, eveningAlarm);
    }

    private void fireRules(List<List<String>> rules){
//        scheduleRule(3, 22, 38, 22, 39, AudioManager.RINGER_MODE_NORMAL, AudioManager.RINGER_MODE_SILENT,0);
        //scheduleRule(3, 22, 1, 51, AudioManager.RINGER_MODE_NORMAL, AudioManager.RINGER_MODE_SILENT,0);
//        scheduleRule(3, 18, 32, AudioManager.RINGER_MODE_SILENT, 1);

        //if in use - scheduleRule
        //if not use - cancel rule
        //get hour, minute, day and type of ringer mode from each rule -> save them to list (?)
        //dynamically schedule rules

        String[] daysOfWeek, timeFrom, timeTo;
        int dayOfWeek, hourFrom, minuteFrom, hourTo, minuteTo, mode;
        int reqCode = 0;

        int rulesListSize = rules.size();
        for (int i = 1; i < rulesListSize; i++){
            List<String> underList = rules.get(i);
            daysOfWeek = underList.get(3).split(";");
            int daysOfWeekLen = daysOfWeek.length;
            for (int j = 0; j < daysOfWeekLen; j++){
                timeFrom = underList.get(1).split(":");
                timeTo = underList.get(2).split(":");

                hourFrom = Integer.parseInt(timeFrom[0]);
                minuteFrom = Integer.parseInt(timeFrom[1]);
                hourTo = Integer.parseInt(timeTo[0]);
                minuteTo = Integer.parseInt(timeTo[1]);
                dayOfWeek = Integer.parseInt(daysOfWeek[j]);
                mode = Integer.parseInt(underList.get(4));

                if (mode == 0){
                    scheduleRule(dayOfWeek, hourFrom, minuteFrom, hourTo, minuteTo,
                            AudioManager.RINGER_MODE_SILENT, AudioManager.RINGER_MODE_NORMAL, reqCode);
                }
                else if (mode == 1) {
                    scheduleRule(dayOfWeek, hourFrom, minuteFrom, hourTo, minuteTo,
                            AudioManager.RINGER_MODE_NORMAL, AudioManager.RINGER_MODE_SILENT, reqCode);
                }
                reqCodeList.add(reqCode);
                reqCodeList.add(reqCode+1);
                reqCode += 2;
            }
        }
    }

    private void cancelFiringRule(String json, int index){
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

    private void cancelAllRules(){
        AlarmManager alarmMgr = (AlarmManager)(this.getSystemService(Context.ALARM_SERVICE));

        if (reqCodeList.isEmpty()){
            Toast.makeText(getApplicationContext(), "No rules to cancel!", Toast.LENGTH_LONG).show();
        } else {
            for (Integer reqCode : reqCodeList){
                if (reqCode / 2 == 0){
                    Intent morningIntent2 = new Intent(this, AlarmFireOnMorning.class);
                    PendingIntent morningAlarm2 = PendingIntent.getBroadcast(this, reqCode, morningIntent2, 0);
                    alarmMgr.cancel(morningAlarm2);
                    Toast.makeText(getApplicationContext(), "Alarm Cancelled - "+ reqCode, Toast.LENGTH_LONG).show();
                }else{
                    Intent eveningIntent2 = new Intent(this, AlarmFireOnEvening.class);
                    PendingIntent eveningAlarm2 = PendingIntent.getBroadcast(this, reqCode, eveningIntent2, 0);
                    alarmMgr.cancel(eveningAlarm2);
                    Toast.makeText(getApplicationContext(), "Alarm Cancelled - "+ reqCode, Toast.LENGTH_LONG).show();
                }
            }
        }
    }



    private void loadFileIfIsAvailable(){
        FilesHandler fileHandler = new FilesHandler(MainActivity.this);
        //managing JSONs reference
        //JSONFilesHandler JSONHandler = new JSONFilesHandler(MainActivity.this);

        String jsonString = fileHandler.readFile("rules.json");
        Log.d("MainActivity", jsonString);
        //read rules from file and display them in main window on start
        List<List<String>> rls = loadRulesFromJSON(jsonString);
        displayRules(rls);
        fireRules(rls);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        //file with rules reference
        FilesHandler fileHandler = new FilesHandler(MainActivity.this);
//        //managing JSONs reference
//        //JSONFilesHandler JSONHandler = new JSONFilesHandler(MainActivity.this);
        CompoundButton.OnCheckedChangeListener switchListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    cancelAllRules();
                }
                else{
                    loadFileIfIsAvailable();
                }
            }
        };

        boolean isFileAvailable = fileHandler.isFileAvailable("rules.json");
        if (isFileAvailable){
            loadFileIfIsAvailable();
        } else{
            //first launch of app - create empty rules.json file
            boolean isFileCreated = fileHandler.createFile("rules.json", "{\"rules\":[{\"id\":0,\"timeFrom\":\"\",\"timeTo\":\"\",\"daysOfWeek\":\"\",\"spinnerChoice\":-1,\"isRuleInUse\":false}]}");
            if (isFileCreated){
                Toast.makeText(MainActivity.this, "File rules.json created", Toast.LENGTH_LONG).show();
            } else{
                throw new RuntimeException(); //throw exception - not sure if in proper way
            }
        }

        toggle_switch = (Switch)findViewById(R.id.switch1);
        toggle_switch.setOnCheckedChangeListener(switchListener);
    }
    //onActivityResult()
}