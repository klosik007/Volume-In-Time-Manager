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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class AddRule extends AppCompatActivity {
    TimePickerDialog timePicker;
    EditText timeFromEditText;
    EditText timeToEditText;
    EditText dayPickerEditText;
    Button addRuleButton;
    String daysPicked;
    int behaviorSpinnerChoice;

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

    private String checkDigit(int number){
        return number <= 9 ? "0"+ number : String.valueOf(number);
    }

    public void dayPicker(View view){
        Intent intent = new Intent(getApplicationContext(), DayPicker.class);
        startActivityForResult(intent, 1);
    }

    public String replaceDaysIDsWithDaysNames(String daysIds){
        if (daysIds == null){
            throw new Error("Days picked must not be null");
        }

        Dictionary days = new Hashtable();
        days.put("1", "Sunday");
        days.put("2", "Monday");
        days.put("3", "Tuesday");
        days.put("4", "Wednesday");
        days.put("5", "Thursday");
        days.put("6", "Friday");
        days.put("7", "Saturday");

        String[] daysIDs = daysIds.split(";");
        List<String> daysNames = new ArrayList<>();

        for (String day : daysIDs){
            daysNames.add((String) days.get(day));
        }

        int daysNamesSize = daysNames.size();
        String daysOutput = "";
        for (int i = 0; i < daysNamesSize; i++){
            daysOutput += daysNames.get(i) + ";";
        }

        return daysOutput;
    }

    public void addRuleButton(View view){
        //file with rules reference
        JSONFilesHandler json = new JSONFilesHandler(AddRule.this);
        String jsonString = json.readFile("rules.json");
        Integer id = json.returnLastIdFromJSON(jsonString) + 1;
        String timeFromText = timeFromEditText.getText().toString();
        String timeToText = timeToEditText.getText().toString();
        String daysPickedText = daysPicked;
        int spinnerChoice = behaviorSpinnerChoice;

        String rule = json.addRuleToJSON("rules.json", id, timeFromText, timeToText, daysPickedText, spinnerChoice,true);
        json.overwriteJSONFile("rules.json", rule);

        String jsonString2 = json.readFile("rules.json");
        Log.d("AddRule", jsonString2);
    }

    private void loadSpinnerItems(){
        Spinner behaviorSpinner = (Spinner)findViewById(R.id.behaviorSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.behaviorSpinner_array, android.R.layout.simple_spinner_item);
        //which layout to use when list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //apply adapter to a spinner
        behaviorSpinner.setAdapter(adapter);
        //set select listener to a spinner
        behaviorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Long positionIdLong = parent.getItemIdAtPosition(position);
                Integer positionIdInt = positionIdLong != null ? positionIdLong.intValue() : null; //suitable for API >= 19
                //Integer positionIdInt = positionIdLong == null ? null : Math.toIntExact(positionIdLong);  //min API 24
                switch (positionIdInt) {
                    case 0:
                        behaviorSpinnerChoice = 0; // SOUNDS OFF
                        break;
                    case 1:
                        behaviorSpinnerChoice = 1; //SOUNDS ON
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
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

        loadSpinnerItems();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                daysPicked = data.getStringExtra(MESSAGE_KEY);
                String daysPickedNames = replaceDaysIDsWithDaysNames(daysPicked);
                dayPickerEditText.setText(daysPickedNames);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}