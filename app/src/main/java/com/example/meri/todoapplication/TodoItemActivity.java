package com.example.meri.todoapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TodoItemActivity extends AppCompatActivity
        implements View.OnClickListener{

    private final String titleText = "Title is empty";
    private final String dateText = "Date is empty";
    private final String descrText = "Description is not full";

    private final String TODO_ITEM = "ToDo Item";
    private final String SEND = "send";

    private final String buttonTextSave = "Save";
    private final String buttonTextEdit = "Edit";

    private Button mButtonSave;

    private EditText mEditTextTitle;
    private EditText mEditTextDescription;

    private TextView mTextViewDate;
    private TextView mTextPriorityNumber;

    private ImageView mImageUpPriority;
    private ImageView mImageDownPriority;
    private ImageView mBack;

    private CheckBox mCheckReminder;
    private CheckBox mCheckRepeat;

    private RadioGroup mRadioGroup;
    private RadioButton mRadioDaily;
    private RadioButton mRadioWeekly;
    private RadioButton mRadioMonthly;


    private Calendar mCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;
    TimePickerDialog.OnTimeSetListener time;

    private int mod = 0;
    private int mPriorityNumber = 0;

    private String mTitle;
    private String mDescription;
    private Date mDate;

    private boolean isCheckedReminder;
    private boolean isCheckedRepeat;

    private int mCheckedRadioButtonId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_item);

        mButtonSave = findViewById(R.id.buttonSave);

        mEditTextTitle = findViewById(R.id.editTextTitle);
        mEditTextDescription = findViewById(R.id.editTextDescription);

        mTextViewDate = findViewById(R.id.textViewDate);
        mTextPriorityNumber = findViewById(R.id.textPriorityNumber);

        mBack = findViewById(R.id.back);
        mImageUpPriority = findViewById(R.id.textUpPriority);
        mImageDownPriority = findViewById(R.id.textDownPriority);

        mCheckReminder = findViewById(R.id.checkReminder);
        mCheckRepeat = findViewById(R.id.checkRepeat);

        mRadioGroup = findViewById(R.id.radioGroup);
        mRadioDaily = findViewById(R.id.radioDaily);
        mRadioWeekly = findViewById(R.id.radioWeekly);
        mRadioMonthly = findViewById(R.id.radioMonthly);

        mButtonSave.setOnClickListener(this);
        mTextViewDate.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mImageUpPriority.setOnClickListener(this);
        mImageDownPriority.setOnClickListener(this);
        mCheckRepeat.setOnClickListener(this);
        mCheckReminder.setOnClickListener(this);

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year,
                                  int month, int day){

                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, month);
                mCalendar.set(Calendar.DAY_OF_MONTH, day);
                setDate();
            }
        };

        time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mCalendar.set(Calendar.MINUTE, minute);
                setDate();
            }
        };

        mEditTextDescription.addTextChangedListener(new TextWatcher() {
            String text;

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mEditTextDescription.getLineCount() > 3){
                    mEditTextDescription.setText(text);
                    mEditTextDescription.setSelection(mEditTextDescription.
                            getText().length());
                } else {
                    text = mEditTextDescription.getText().toString();
                }
            }
        });

        getReceivedIntent();
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.buttonSave:
                if(mod == 1){
                    mButtonSave.setText(buttonTextSave);
                    enable();
                } else {
                    saveData();
                }
                break;
            case R.id.textViewDate:
                new DatePickerDialog(this, date, mCalendar.
                        get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH)).show();
                new TimePickerDialog(this, time, mCalendar.
                        get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE),
                        false).show();
                break;
            case R.id.textUpPriority:
                if(mPriorityNumber < 5){
                    mPriorityNumber++;
                }
                MyView.setViewNumber(mTextPriorityNumber, mPriorityNumber);
                break;
            case R.id.textDownPriority:
                if(mPriorityNumber > 0){
                    mPriorityNumber--;
                }
                MyView.setViewNumber(mTextPriorityNumber, mPriorityNumber);
                break;
            case R.id.checkRepeat:
                isCheckedRepeat = mCheckRepeat.isChecked();
                showOrHide(isCheckedRepeat, mRadioGroup);
                break;
            case R.id.back:
                break;
        }
    }

    private void setDate(){
        String dateFormat = "MM/dd/yy hh:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                dateFormat, Locale.US);

        MyView.setTextViewValue(mTextViewDate, simpleDateFormat
                .format(mCalendar.getTime()));
    }

    private void saveData() {
        mTitle = MyView.getEditTextValue(mEditTextTitle);
        mDescription = MyView.getEditTextValue(mEditTextDescription);
        mDate = mCalendar.getTime();

        isCheckedReminder = MyView.getCheckBox(mCheckReminder);
        isCheckedRepeat = MyView.getCheckBox(mCheckRepeat);

        mCheckedRadioButtonId = MyView.getRadioButtonId(mRadioGroup);

        if (isEmptyData(mTitle)) {
            Toast.makeText(this, titleText, Toast.
                    LENGTH_SHORT).show();
        } else {
            if (countLines(mDescription) < 3) {
                Toast.makeText(this, descrText, Toast.
                        LENGTH_SHORT).show();
            } else {
                if (isEmptyDate(mDate)) {
                    Toast.makeText(this, dateText, Toast.
                            LENGTH_SHORT).show();
                } else {
                    sendData();
                }
            }
        }
    }

    private boolean isEmptyData(String text){
        if(text.matches("")){
            return true;
        }
        return false;
    }

    private boolean isEmptyDate(Date date){
        if(date == null){
            return true;
        }
        return false;
    }

    private void sendData(){
        newIntent();
    }

    private void newIntent(){
        Intent intent = new Intent();

        TodoItem item = new TodoItem(mTitle, mDescription, mCalendar.getTimeInMillis(),
                isCheckedReminder, isCheckedRepeat, mCheckedRadioButtonId,
                mPriorityNumber);
        intent.putExtra(TODO_ITEM, item);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void getReceivedIntent(){
        Intent intent = getIntent();

        String buttonClick = "On button click";
        String itemClick = "On item click";

        String data = intent.getStringExtra(SEND);
        if(data.equals(itemClick)) {
            mButtonSave.setText(buttonTextEdit);
            getItem(intent);
            disable();
        }
    }

    private void disable(){
        mod = 1;

        MyView.disableView(mEditTextTitle);
        MyView.disableView(mEditTextDescription);
        MyView.disableView(mTextViewDate);
        MyView.disableView(mCheckReminder);
        MyView.disableView(mCheckRepeat);
        MyView.disableView(mRadioGroup);
        MyView.disableView(mImageUpPriority);
        MyView.disableView(mImageDownPriority);
        MyView.disableView(mTextPriorityNumber);
        MyView.disableView(mRadioDaily);
        MyView.disableView(mRadioWeekly);
        MyView.disableView(mRadioMonthly);
    }

    private void enable(){
        mod = 0;

        MyView.enableView(mEditTextTitle);
        MyView.enableView(mEditTextDescription);
        MyView.enableView(mTextViewDate);
        MyView.enableView(mCheckReminder);
        MyView.enableView(mCheckRepeat);
        MyView.enableView(mRadioGroup);
        MyView.enableView(mImageUpPriority);
        MyView.enableView(mImageDownPriority);
        MyView.enableView(mTextPriorityNumber);
        MyView.enableView(mRadioDaily);
        MyView.enableView(mRadioWeekly);
        MyView.enableView(mRadioMonthly);
    }

    private void getItem(Intent intent){
        TodoItem todoItem = (TodoItem)intent
                .getSerializableExtra(TODO_ITEM);

        getItemValues(todoItem);
        setViewValues();
    }

    private void getItemValues(TodoItem todoItem){
        mTitle = todoItem.getTitle();
        mDescription = todoItem.getDescription();
        mCalendar.setTimeInMillis(todoItem.getDate());
        isCheckedReminder = todoItem.isCheckedReminder();
        isCheckedRepeat = todoItem.isCheckedRepeat();
        mCheckedRadioButtonId = todoItem.getCheckedRadioId();
        mPriorityNumber = todoItem.getPriority();
    }

    private void setViewValues(){
        MyView.setEditTextValue(mEditTextTitle, mTitle);
        MyView.setEditTextValue(mEditTextDescription, mDescription);

        setDate();

        MyView.setCheckBox(mCheckReminder, isCheckedReminder);
        MyView.setCheckBox(mCheckRepeat, isCheckedRepeat);

        MyView.setRadioGroup(mRadioGroup, mCheckedRadioButtonId);
        MyView.setViewNumber(mTextPriorityNumber, mPriorityNumber);

        showOrHide(isCheckedRepeat, mRadioGroup);
    }

    void showOrHide(boolean isChecked, RadioGroup rGroup){
        if(isChecked){
            rGroup.setVisibility(View.VISIBLE);
        } else {
            rGroup.setVisibility(View.GONE);
        }
    }

    int countLines(String text){
        return text.split("\n").length;
    }
}