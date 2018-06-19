package com.example.meri.todoapplication;

public interface Item {

    public static final int TODO_ITEM = 1;
    public static final int MONTH_ITEM = 2;

    public abstract int getType();
}
