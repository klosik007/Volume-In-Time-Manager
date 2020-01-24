package com.example.volumeintimemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.EditText;
import android.widget.TextView;

import com.dpro.widgets.OnWeekdaysChangeListener;
import com.dpro.widgets.WeekdaysPicker;

import java.util.Arrays;
import java.util.List;

public class DayPicker extends AppCompatActivity {
    List<Integer> _daysOfWeekPicked;
    List<Integer> _days = Arrays.asList();
    String _dayPickerDaysText = "";
    public final static String MESSAGE_KEY ="com.example.volumeintimemanager.message_key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_picker);

        final WeekdaysPicker widget = (WeekdaysPicker)findViewById(R.id.weekdays);
        widget.setSelectedDays(_days); //provide no days are selected by default
        widget.setOnWeekdaysChangeListener(new OnWeekdaysChangeListener() {
            @Override
            public void onChange(View view, int clickedDayOfWeek, List<Integer> selectedDays) {
                _daysOfWeekPicked = widget.getSelectedDays();
            }
        });
    }

    public void selectButton(View view){
        Intent returnData = new Intent();
        for (int day : _daysOfWeekPicked){
            _dayPickerDaysText = _dayPickerDaysText + day + ";";
        }
        returnData.putExtra(MESSAGE_KEY, _dayPickerDaysText);
        setResult(RESULT_OK, returnData);
        finish();
    }
}