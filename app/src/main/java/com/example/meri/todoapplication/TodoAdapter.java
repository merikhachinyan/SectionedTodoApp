package com.example.meri.todoapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private List<TodoItem> mTodoList;

    private recyclerViewItemClickListener mClickListener;
    View itemView;

    TodoAdapter(List<TodoItem> todoList){
        this.mTodoList = todoList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        TextView title;
        TextView description;
        TextView date;

        public ViewHolder(View view){
            super(view);

            title = view.findViewById(R.id.title);
            description = view.findViewById(R.id.description);
            date = view.findViewById(R.id.date);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TodoItem item = mTodoList.get(position);

        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        holder.date.setText(item.getDate());
    }

    @Override
    public int getItemCount() {
        return mTodoList.size();
    }

    public interface recyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setClickListener(recyclerViewItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}