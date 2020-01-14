package com.example.volumeintimemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.volumeintimemanager.MESSAGE";

    public void addRule(View view){
        Intent intent = new Intent(this, AddRule.class);
        startActivity(intent);
    }

    public void editRule(View view){
        Intent intent = new Intent(this, EditRule.class);
        startActivity(intent);
    }

    private void displayRules(String json){
        TextView timeFrom;
        TextView timeTo;
        TextView daysOfWeek;
        CheckBox isRuleInUse;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //file with rules reference
        FilesHandler fileHandler = new FilesHandler();
        //managing JSONs reference
        JSONFilesHandler JSONHandler = new JSONFilesHandler();

        boolean isFileAvailable = fileHandler.isFileAvailable(MainActivity.this, "rules.json");
        if (isFileAvailable){
            String jsonString = fileHandler.readFile(MainActivity.this, "rules.json");
            Log.d("MainActivity", jsonString);
            //read rules from file and display them in main window on start
            //displayRules(jsonString);
        } else{
            //first launch of app - create empty rules.json file
            boolean isFileCreated = fileHandler.createFile(MainActivity.this, "rules.json", "{\"rules\":[]}");
            if (isFileCreated){
                Toast.makeText(MainActivity.this, "File rules.json created", Toast.LENGTH_LONG).show();
            } else{
                throw new RuntimeException(); //throw exception - not sure if in proper way
            }
        }
    }
}
