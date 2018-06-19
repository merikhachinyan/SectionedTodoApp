package com.example.meri.todoapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TodoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TodoItem> mTodoList = new ArrayList<>();
    private List<Item> mItems = new ArrayList<>();

    private RecyclerViewItemClickListener mItemClickListener;
    private RemoveItemListener mRemoveItemListener;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;

        switch (viewType) {
            case Item.TODO_ITEM:
                view = layoutInflater.inflate(R.layout.todo_item, parent, false);
                return new TodoViewHolder(view);
            case Item.MONTH_ITEM:
                view = layoutInflater.inflate(R.layout.month_item, parent, false);
                return new MonthViewHolder(view);
            default:
                throw new IllegalStateException("Unknown view type");
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()){
            case Item.TODO_ITEM: {
                TodoViewHolder todoHolder = (TodoViewHolder) holder;
                final TodoItem item = (TodoItem) mItems.get(position);

                todoHolder.getTitle().setText(item.getTitle());
                todoHolder.getDescription().setText(item.getDescription());

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(item.getDate());

                todoHolder.getDate().setText(dateFormat().format(calendar.getTime()));

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mItemClickListener.onItemClick(view, holder.getAdapterPosition());
                    }
                });

                todoHolder.getDeleteItem().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mRemoveItemListener.onRemove(holder.getAdapterPosition());
                    }
                });
            }
                break;
            case Item.MONTH_ITEM:{
                MonthViewHolder monthHolder = (MonthViewHolder) holder;
                MonthItem item = (MonthItem) mItems.get(position);

                String month = new DateFormatSymbols().getMonths()[item.getMonth()];
                monthHolder.getMonth().setText(month);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getType();
    }

    private SimpleDateFormat dateFormat(){
        String dateFormat = "dd MMM yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                dateFormat, Locale.US);

        return simpleDateFormat;
    }

    public void addTodoItem(TodoItem todoItem){
        mTodoList.add(todoItem);
    }

    public void removeTodoItem(TodoItem todoItem){
        mTodoList.remove(todoItem);
    }

    public void addItems(Item item){
        mItems.add(item);
    }

    public void setmItem(TodoItem todoItem, int position){
        mItems.set(position, todoItem);
    }

    public List<TodoItem> getmTodoList() {
        return mTodoList;
    }

    public List<Item> getmItems() {
        return mItems;
    }

    public Item getmItem(int position){
        return mItems.get(position);
    }

    public void removeItems(){
        mItems.clear();
    }

    public void setClickListener(RecyclerViewItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public interface RemoveItemListener{
        void onRemove(int position);
    }

    public void setRemoveItemListener(RemoveItemListener removeItemListener){
        mRemoveItemListener = removeItemListener;
    }
}