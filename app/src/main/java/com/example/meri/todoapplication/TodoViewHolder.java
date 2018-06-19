package com.example.meri.todoapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TodoViewHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private TextView description;
    private TextView date;
    private ImageView deleteItem;


    public TodoViewHolder(View view){
        super(view);

        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);
        date = view.findViewById(R.id.date);
        deleteItem = view.findViewById(R.id.deleteItem);

    }

    public TextView getTitle() {
        return title;
    }

    public TextView getDescription() {
        return description;
    }

    public TextView getDate() {
        return date;
    }

    public ImageView getDeleteItem() {
        return deleteItem;
    }
}
