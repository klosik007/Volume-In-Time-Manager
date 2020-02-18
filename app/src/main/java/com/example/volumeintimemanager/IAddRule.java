package com.example.volumeintimemanager;

import android.view.View;

public interface IAddRule {
    String checkDigit(int number);
    void dayPicker(View view);
    String replaceDaysIDsWithDaysNames(String daysIds);
    void addRuleButton(View view);
    void loadSpinnerItems();
}
