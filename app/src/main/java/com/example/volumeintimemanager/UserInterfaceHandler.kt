package com.example.volumeintimemanager

import android.content.Context
import android.view.Gravity
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView

class UserInterfaceHandler(private val _context: Context) {
    private fun createCheckbox(id: Int, isRuleInUse: Boolean): CheckBox {
        val checkbox = CheckBox(_context)
        checkbox.id = id
        checkbox.text = "In Use"
        checkbox.isChecked = isRuleInUse
        return checkbox
    }

    private fun replaceSpinnersIDsWithNames(id: String?): String {
        if (id == null) {
            throw Error("Days picked must not be null")
        }
        return if (id == "0") "SOUNDS OFF" else "SOUNDS ON"
    }

    fun createLinearLayoutForRule(
        id: Int,
        isRuleInUse: Boolean,
        timeFrom: String?,
        timeTo: String?,
        daysOfWeek: String?,
        spinnerChoice: Int
    ): LinearLayout {
        val timeFromTextView = TextView(_context)
        timeFromTextView.textSize = 20f
        timeFromTextView.gravity = Gravity.LEFT
        timeFromTextView.text = timeFrom
        val timeToTextView = TextView(_context)
        timeToTextView.textSize = 20f
        timeToTextView.gravity = Gravity.LEFT
        timeToTextView.text = timeTo
        val daysOfWeekTextView = TextView(_context)
        daysOfWeekTextView.textSize = 20f
        daysOfWeekTextView.gravity = Gravity.LEFT
        val ar = AddRule()
        daysOfWeekTextView.text = ar.replaceDaysIDsWithDaysNames(daysOfWeek)
        val spinnerChoiceTextView = TextView(_context)
        spinnerChoiceTextView.textSize = 20f
        spinnerChoiceTextView.gravity = Gravity.LEFT
        spinnerChoiceTextView.text = replaceSpinnersIDsWithNames(spinnerChoice.toString())
        val ll = LinearLayout(_context)
        ll.orientation = LinearLayout.VERTICAL
        ll.layoutParams =
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        ll.gravity = Gravity.LEFT
        val inUse = createCheckbox(id, isRuleInUse)
        ll.addView(inUse)
        ll.addView(timeFromTextView)
        ll.addView(timeToTextView)
        val layoutParams =
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        layoutParams.setMargins(0, 0, 0, 30)
        ll.addView(daysOfWeekTextView, layoutParams)
        ll.addView(spinnerChoiceTextView)
        ll.id = id
        return ll
    } //    public TextView createTextView(int layoutWidth, int layoutHeight, int align, String text, int fontSize, int margin, int padding){
    //        TextView textView = new TextView(_context);
    //
    //        RelativeLayout.LayoutParams _params = new RelativeLayout.LayoutParams(layoutWidth, layoutHeight);
    //
    //        _params.setMargins(margin, margin, margin, margin);
    //        _params.addRule(align);
    //        textView.setLayoutParams(_params);
    //
    //        textView.setText(text);
    //        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
    //        textView.setTextColor(Color.parseColor("#000000"));
    //        textView.setPadding(padding, padding, padding, padding);
    //
    //        return textView;
    //    }
}