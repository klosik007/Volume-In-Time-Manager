package com.example.volumeintimemanager

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.volumeintimemanager.DayPicker
import java.util.*

class AddRule : AppCompatActivity(), IAddRule {
    var timePicker: TimePickerDialog? = null
    var timeFromEditText: EditText? = null
    var timeToEditText: EditText? = null
    var dayPickerEditText: EditText? = null
    var addRuleButton: Button? = null
    var daysPicked: String? = null
    var behaviorSpinnerChoice = 0
    private val showTimePicker = View.OnClickListener {
        val cldr = Calendar.getInstance()
        val hour = cldr[Calendar.HOUR_OF_DAY]
        val minute = cldr[Calendar.MINUTE]
        timePicker = TimePickerDialog(this@AddRule,
            { view, hourOfDay, minute ->
                if (timeFromEditText!!.isFocused) {
                    timeFromEditText!!.setText(checkDigit(hourOfDay) + ":" + checkDigit(minute))
                } else {
                    timeToEditText!!.setText(checkDigit(hourOfDay) + ":" + checkDigit(minute))
                }
            }, hour, minute, true
        )
        timePicker!!.show()
    }

    override fun checkDigit(number: Int): String {
        return if (number <= 9) "0$number" else number.toString()
    }

    override fun dayPicker(view: View?) {
        val intent = Intent(applicationContext, DayPicker::class.java)
        startActivityForResult(intent, 1)
    }

    override fun replaceDaysIDsWithDaysNames(daysIds: String?): String? {
        if (daysIds == null) {
            throw Error("Days picked must not be null")
        }
        val days: Dictionary<String, String> = Hashtable()
        days.put("1", "Sunday")
        days.put("2", "Monday")
        days.put("3", "Tuesday")
        days.put("4", "Wednesday")
        days.put("5", "Thursday")
        days.put("6", "Friday")
        days.put("7", "Saturday")
        val daysIDs = daysIds.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val daysNames: MutableList<String> = ArrayList()
        for (day in daysIDs) {
            daysNames.add(days[day] as String)
        }
        val daysNamesSize = daysNames.size
        var daysOutput = ""
        for (i in 0 until daysNamesSize) {
            daysOutput += daysNames[i] + ";"
        }
        return daysOutput
    }

    override fun addRuleButton(view: View?) {
        //file with rules reference
        val json = JSONFilesHandler(this@AddRule)
        val jsonString = json.readFile("rules.json")
        val id = json.returnLastIdFromJSON(jsonString) + 1
        val timeFromText = timeFromEditText!!.text.toString()
        val timeToText = timeToEditText!!.text.toString()
        val daysPickedText = daysPicked
        val spinnerChoice = behaviorSpinnerChoice
        val rule = json.addRuleToJSON("rules.json", id, timeFromText, timeToText, daysPickedText, spinnerChoice, true)
        json.overwriteJSONFile("rules.json", rule)
        val jsonString2 = json.readFile("rules.json")
        Log.d("AddRule", jsonString2!!)
    }

    override fun loadSpinnerItems() {
        val behaviorSpinner = findViewById<View>(R.id.behaviorSpinner) as Spinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.behaviorSpinner_array, android.R.layout.simple_spinner_item
        )
        //which layout to use when list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //apply adapter to a spinner
        behaviorSpinner.adapter = adapter
        //set select listener to a spinner
        behaviorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val positionIdLong = parent.getItemIdAtPosition(position)
                val positionIdInt =
                    if (positionIdLong != null) positionIdLong.toInt() else null //suitable for API >= 19
                when (positionIdInt) {
                    0 -> behaviorSpinnerChoice = 0 // SOUNDS OFF
                    1 -> behaviorSpinnerChoice = 1 //SOUNDS ON
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // TODO Auto-generated method stub
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_rule)

        //attach components to variables defined in the beginning
        timeFromEditText = findViewById<View>(R.id.timeFromTextBox) as EditText
        timeToEditText = findViewById<View>(R.id.timeToTextBox) as EditText
        dayPickerEditText = findViewById<View>(R.id.daysOfWeekSetup) as EditText
        addRuleButton = findViewById<View>(R.id.addRuleButton) as Button

        //set input types for text boxes
        timeFromEditText!!.inputType = InputType.TYPE_NULL
        timeToEditText!!.inputType = InputType.TYPE_NULL
        dayPickerEditText!!.inputType = InputType.TYPE_NULL

        //set onClick event for text boxes
        timeFromEditText!!.setOnClickListener(showTimePicker)
        timeToEditText!!.setOnClickListener(showTimePicker)
        loadSpinnerItems()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                daysPicked = data!!.getStringExtra(MESSAGE_KEY)
                val daysPickedNames = replaceDaysIDsWithDaysNames(daysPicked!!)
                dayPickerEditText!!.setText(daysPickedNames)
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    companion object {
        const val MESSAGE_KEY = "com.example.volumeintimemanager.message_key"
    }
}