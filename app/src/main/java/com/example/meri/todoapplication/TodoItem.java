package com.example.meri.todoapplication;

import java.io.Serializable;

public class TodoItem implements Serializable{

    private String title;
    private String description;
    private String date;

    private boolean isCheckedReminder;
    private boolean isCheckedRepeat;

    private int checkedRadioId;
    private int priority;

    public TodoItem(String title, String description, String date,
                    boolean isCheckedReminder, boolean isCheckedRepeat,
                    int checkedRadioId, int priority){

        this.title = title;
        this.description = description;
        this.date = date;
        this.isCheckedReminder = isCheckedReminder;
        this.isCheckedRepeat = isCheckedRepeat;
        this.checkedRadioId = checkedRadioId;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public boolean isCheckedReminder() {
        return isCheckedReminder;
    }

    public boolean isCheckedRepeat() {
        return isCheckedRepeat;
    }

    public int getCheckedRadioId() {
        return checkedRadioId;
    }

    public int getPriority() {
        return priority;
    }
}