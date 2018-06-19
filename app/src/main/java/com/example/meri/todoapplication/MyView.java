package com.example.meri.todoapplication;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MyView {

    static String getTextViewValue(TextView textView){
        return textView.getText().toString();
    }

    static String getEditTextValue(EditText textView){
        return textView.getText().toString();
    }

    static void setTextViewValue(TextView textView, String text){
        textView.setText(text);
    }

    static void setEditTextValue(EditText textView, String text){
        textView.setText(text);
    }

    static boolean getCheckBox(CheckBox checkBox){
        return checkBox.isChecked();
    }

    static int getRadioButtonId(RadioGroup radioGroup){
        return radioGroup.getCheckedRadioButtonId();
    }

    static void setCheckBox(CheckBox checkBox, boolean flag){
        checkBox.setChecked(flag);
    }

    static void setRadioGroup(RadioGroup radioGroup, int id){
        radioGroup.check(id);
    }

    static void setViewNumber(TextView textView, int number){
        textView.setText(Integer.toString(number));
    }

    static void disableView(View view){
        view.setEnabled(false);
    }

    static void enableView(View view){
        view.setEnabled(true);
    }
}