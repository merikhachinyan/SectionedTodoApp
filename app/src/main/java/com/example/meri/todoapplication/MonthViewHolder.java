package com.example.meri.todoapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MonthViewHolder extends RecyclerView.ViewHolder{

    TextView month;

    public MonthViewHolder(View itemView) {
        super(itemView);

        month = itemView.findViewById(R.id.month);
    }

    public TextView getMonth(){
        return month;
    }
}
