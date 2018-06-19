package com.example.meri.todoapplication;

public class MonthItem implements Item{

    private int month;

    public MonthItem(){

    }
    public MonthItem(int month) {
        this.month = month;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @Override
    public int getType() {
        return MONTH_ITEM;
    }
}
