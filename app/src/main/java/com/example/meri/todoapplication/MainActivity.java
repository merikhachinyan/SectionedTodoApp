package com.example.meri.todoapplication;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, RecyclerViewItemClickListener{

    private final int REQUEST_CODE = 3;
    private final int REQUEST_CODE1 = 1;

    private final String TODO_ITEM = "ToDo Item";
    private final String SEND = "send";
    private String intentInfo;

    private FloatingActionButton mButtonAdd;
    private RecyclerView mRecyclerView;
    private TodoAdapter mTodoAdapter;

    private int itemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonAdd = findViewById(R.id.buttonAdd);
        mButtonAdd.setOnClickListener(this);

        mRecyclerView = findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(
                mRecyclerView.getContext(), LinearLayoutManager.VERTICAL));

        mTodoAdapter = new TodoAdapter();
        mRecyclerView.setAdapter(mTodoAdapter);
        mTodoAdapter.setClickListener(this);

        mTodoAdapter.setRemoveItemListener(new TodoAdapter.RemoveItemListener() {
            @Override
            public void onRemove(int position) {
                TodoItem todo = (TodoItem) mTodoAdapter.getmItems().get(position);
                mTodoAdapter.removeTodoItem(todo);

                changeItems();
            }
        });
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.buttonAdd:
                intentInfo = "On button click";

                Intent intent = new Intent(this, TodoItemActivity.class);
                intent.putExtra(SEND, intentInfo);
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
     }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        TodoItem item;

        switch (requestCode){
            case REQUEST_CODE:
                item = (TodoItem)data.getSerializableExtra(TODO_ITEM);
                if(notNullValues(item)){
                    mTodoAdapter.addTodoItem(item);

                    changeItems();
                }
                break;

            case REQUEST_CODE1:
                item = (TodoItem)data.getSerializableExtra(TODO_ITEM);
                if (notNullValues(item)){
                    mTodoAdapter.setmItem(item, itemPosition);
                    mTodoAdapter.notifyDataSetChanged();
                    //mTodoAdapter.notifyItemChanged(itemPosition);
                }
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position){
            intentInfo = "On item click";
            TodoItem item = (TodoItem) mTodoAdapter.getmItem(position);
            itemPosition = position;

            Intent intent = new Intent(this, TodoItemActivity.class);
            intent.putExtra(SEND, intentInfo);
            intent.putExtra(TODO_ITEM, item);
            startActivityForResult(intent, REQUEST_CODE1);
    }

    private TreeMap<Integer, List<TodoItem>> getItemsMap(List<TodoItem> list){
        TreeMap<Integer, List<TodoItem>> map = new TreeMap<>();
        Calendar calendar = Calendar.getInstance();

        for (TodoItem todoItem : list){
            calendar.setTimeInMillis(todoItem.getDate());
            int key = calendar.get(Calendar.MONTH);
            if(map.containsKey(key)){
                map.get(key).add(todoItem);
            } else {
                List<TodoItem> item = new ArrayList<>();
                item.add(todoItem);
                map.put(key, item);
            }
        }
        return map;
    }

    private void changeItems(){
        mTodoAdapter.removeItems();

        TreeMap<Integer, List<TodoItem>> map =
                getItemsMap(mTodoAdapter.getmTodoList());

        for (int month : map.keySet()){
            MonthItem monthItem = new MonthItem();
            monthItem.setMonth(month);
            mTodoAdapter.addItems(monthItem);

            for (TodoItem todoItem : map.get(month)){
                mTodoAdapter.addItems(todoItem);
            }
        }
        mTodoAdapter.notifyDataSetChanged();
    }

    private boolean notNullValues(TodoItem todoItem){
        if (todoItem.getTitle() != null && todoItem.getDescription() != null){
            return true;
        }
        return false;
    }
}