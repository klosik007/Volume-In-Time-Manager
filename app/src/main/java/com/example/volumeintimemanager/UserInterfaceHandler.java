package com.example.volumeintimemanager;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class UserInterfaceHandler {
    private Context _context;

    public UserInterfaceHandler(Context context){
        _context = context;
    }

    private CheckBox createCheckbox(int id, boolean isRuleInUse){
        CheckBox checkbox = new CheckBox(_context);

        checkbox.setId(id);
        checkbox.setText("In Use");
        checkbox.setChecked(isRuleInUse);

        return checkbox;
    }

    private String replaceSpinnersIDsWithNames(String id){
        if (id == null){
            throw new Error("Days picked must not be null");
        }
        return id.equals("0") ? "SOUNDS OFF" : "SOUNDS ON" ;
    }

    public LinearLayout createLinearLayoutForRule(int id, boolean isRuleInUse, String timeFrom, String timeTo, String daysOfWeek, int spinnerChoice){
        TextView timeFromTextView = new TextView(_context);
        timeFromTextView.setTextSize(20);
        timeFromTextView.setGravity(Gravity.LEFT);
        timeFromTextView.setText(timeFrom);

        TextView timeToTextView = new TextView(_context);
        timeToTextView.setTextSize(20);
        timeToTextView.setGravity(Gravity.LEFT);
        timeToTextView.setText(timeTo);

        TextView daysOfWeekTextView = new TextView(_context);
        daysOfWeekTextView.setTextSize(20);
        daysOfWeekTextView.setGravity(Gravity.LEFT);
        AddRule ar = new AddRule();
        daysOfWeekTextView.setText(ar.replaceDaysIDsWithDaysNames(daysOfWeek));

        TextView spinnerChoiceTextView = new TextView(_context);
        spinnerChoiceTextView.setTextSize(20);
        spinnerChoiceTextView.setGravity(Gravity.LEFT);
        spinnerChoiceTextView.setText(replaceSpinnersIDsWithNames(String.valueOf(spinnerChoice)));

        LinearLayout ll = new LinearLayout(_context);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        ll.setGravity(Gravity.LEFT);
        CheckBox inUse = createCheckbox(id, isRuleInUse);
        ll.addView(inUse);
        ll.addView(timeFromTextView);
        ll.addView(timeToTextView);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, 0, 0, 30);
        ll.addView(daysOfWeekTextView, layoutParams);
        ll.addView(spinnerChoiceTextView);
        ll.setId(id);

        return ll;
    }

//    public TextView createTextView(int layoutWidth, int layoutHeight, int align, String text, int fontSize, int margin, int padding){
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
