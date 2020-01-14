package com.example.volumeintimemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import java.io.File;
import java.util.Calendar;

public class AddRule extends AppCompatActivity {
    TimePickerDialog timePicker;
    EditText timeFromEditText;
    EditText timeToEditText;
    EditText dayPickerEditText;
    Button addRuleButton;
    String daysPicked;

    public final static String MESSAGE_KEY = "com.example.volumeintimemanager.message_key";

    private View.OnClickListener showTimePicker = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final Calendar cldr = Calendar.getInstance();
            int hour = cldr.get(Calendar.HOUR_OF_DAY);
            int minute = cldr.get(Calendar.MINUTE);

            timePicker = new TimePickerDialog(AddRule.this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            if (timeFromEditText.isFocused()){
                                timeFromEditText.setText(checkDigit(hourOfDay) + ":" + checkDigit(minute));
                            } else {
                                timeToEditText.setText(checkDigit(hourOfDay) + ":" + checkDigit(minute));
                            }
                        }
                    }, hour, minute, true);
            timePicker.show();
        }
    };

//    private void displayRules(String file){
//
//    }

    private String checkDigit(int number){
        return number <= 9 ? "0"+ number : String.valueOf(number);
    }

    public void dayPicker(View view){
        Intent intent = new Intent(getApplicationContext(), DayPicker.class);
        startActivityForResult(intent, 1);
    }

    public void addRuleButton(View view){
        //file with rules reference
        JSONFilesHandler json = new JSONFilesHandler();
        String jsonString = json.readFile(AddRule.this, "rules.json");
        Integer id = json.returnLastIdFromJSON(jsonString) + 1;
        String timeFromText = timeFromEditText.getText().toString();
        String timeToText = timeToEditText.getText().toString();
        String daysPickedText =  dayPickerEditText.getText().toString();

        String rule = json.addRuleToJSON(AddRule.this, "rules.json", id, timeFromText, timeToText, daysPickedText, true);
        json.overwriteJSONFile(AddRule.this, "rules.json", rule);

        String jsonString2 = json.readFile(AddRule.this, "rules.json");
        Log.d("AddRule", jsonString2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rule);

        //attach components to variables defined in the beginning
        timeFromEditText = (EditText) findViewById(R.id.timeFromTextBox);
        timeToEditText = (EditText) findViewById(R.id.timeToTextBox);
        dayPickerEditText = (EditText) findViewById(R.id.daysOfWeekSetup);
        addRuleButton = (Button) findViewById(R.id.addRuleButton);

        //set input types for text boxes
        timeFromEditText.setInputType(InputType.TYPE_NULL);
        timeToEditText.setInputType(InputType.TYPE_NULL);
        dayPickerEditText.setInputType(InputType.TYPE_NULL);

        //set onClick event for text boxes
        timeFromEditText.setOnClickListener(showTimePicker);
        timeToEditText.setOnClickListener(showTimePicker);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                daysPicked = data.getStringExtra(MESSAGE_KEY);
                dayPickerEditText.setText(daysPicked);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}