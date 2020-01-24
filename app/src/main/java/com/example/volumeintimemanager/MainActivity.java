package com.example.volumeintimemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RelativeLayout layout;
    public static final String EXTRA_MESSAGE = "com.example.volumeintimemanager.MESSAGE";

    public void addRule(View view){
        Intent intent = new Intent(this, AddRule.class);
        startActivity(intent);
    }

    public void editRule(View view){
        Intent intent = new Intent(this, EditRule.class);
        startActivity(intent);
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

            for (int i = 1; i < params.size(); i++){
                params.get(i).addRule(RelativeLayout.BELOW, layoutsList.get(i-1).getId());
            }

            int layoutsListSize = layoutsList.size();
            for (int i = 0; i < layoutsListSize; i++){
                layout.addView(layoutsList.get(i), params.get(i));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //file with rules reference
        FilesHandler fileHandler = new FilesHandler(MainActivity.this);
        //managing JSONs reference
        //JSONFilesHandler JSONHandler = new JSONFilesHandler(MainActivity.this);

        boolean isFileAvailable = fileHandler.isFileAvailable("rules.json");
        if (isFileAvailable){
            String jsonString = fileHandler.readFile("rules.json");
            Log.d("MainActivity", jsonString);
            //read rules from file and display them in main window on start
            List<List<String>> rls = loadRulesFromJSON(jsonString);
            displayRules(rls);
        } else{
            //first launch of app - create empty rules.json file
            boolean isFileCreated = fileHandler.createFile("rules.json", "{\"rules\":[]}");
            if (isFileCreated){
                Toast.makeText(MainActivity.this, "File rules.json created", Toast.LENGTH_LONG).show();
            } else{
                throw new RuntimeException(); //throw exception - not sure if in proper way
            }
        }
    }

    //onActivityResult()
}
