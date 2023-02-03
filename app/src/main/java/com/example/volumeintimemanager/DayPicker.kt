package com.example.volumeintimemanager

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

//import com.dpro.widgets.OnWeekdaysChangeListener;
//import com.dpro.widgets.WeekdaysPicker;
class DayPicker : AppCompatActivity() {
    var _daysOfWeekPicked: List<Int>? = null
    var _days: List<Int> = mutableListOf()
    var _dayPickerDaysText = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_picker)

//        final WeekdaysPicker widget = (WeekdaysPicker)findViewById(R.id.weekdays);
//        widget.setSelectedDays(_days); //provide no days are selected by default
//        widget.setOnWeekdaysChangeListener(new OnWeekdaysChangeListener() {
//            @Override
//            public void onChange(View view, int clickedDayOfWeek, List<Integer> selectedDays) {
//                _daysOfWeekPicked = widget.getSelectedDays();
//            }
//        });
    }

    fun selectButton(view: View?) {
        val returnData = Intent()
        for (day in _daysOfWeekPicked!!) {
            _dayPickerDaysText = "$_dayPickerDaysText$day;"
        }
        returnData.putExtra(MESSAGE_KEY, _dayPickerDaysText)
        setResult(RESULT_OK, returnData)
        finish()
    }

    companion object {
        const val MESSAGE_KEY = "com.example.volumeintimemanager.message_key"
    }
}