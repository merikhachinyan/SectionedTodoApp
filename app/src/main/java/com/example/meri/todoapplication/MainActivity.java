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
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, TodoAdapter.recyclerViewItemClickListener{

    private final int REQUEST_CODE = 3;
    private final int REQUEST_CODE1 = 1;

    private final String TODO_ITEM = "ToDo Item";
    private final String SEND = "send";
    private String intentInfo;

    private List<TodoItem> mList;

    private FloatingActionButton mButtonAdd;
    private RecyclerView mRecyclerView;
    private TodoAdapter mTodoAdapter;

    private int itemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = new ArrayList<>();

        mButtonAdd = findViewById(R.id.buttonAdd);
        mButtonAdd.setOnClickListener(this);

        mRecyclerView = findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new
                LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(
                mRecyclerView.getContext(), LinearLayoutManager.VERTICAL));

    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.buttonAdd:
                intentInfo = "On button click";

                Intent intent = new Intent(this, TodoItemActivity.class);
                intent.putExtra(SEND, intentInfo);
                startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        TodoItem item;
        mTodoAdapter = new TodoAdapter(mList);
        mRecyclerView.setAdapter(mTodoAdapter);
        mTodoAdapter.setClickListener(this);

        switch (requestCode){
            case REQUEST_CODE:
                item = (TodoItem)data.getSerializableExtra(TODO_ITEM);
                if(notNullValues(item)){
                    mList.add(item);
                    mTodoAdapter.notifyDataSetChanged();
                }
                break;

            case REQUEST_CODE1:
                item = (TodoItem)data.getSerializableExtra(TODO_ITEM);
                if (notNullValues(item)){
                    mList.set(itemPosition, item);
                    mTodoAdapter.notifyDataSetChanged();
                    //mTodoAdapter.notifyItemChanged(itemPosition);
                }
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position){
            intentInfo = "On item click";
            TodoItem todoItem = mList.get(position);
            itemPosition = position;

            Intent intent = new Intent(this, TodoItemActivity.class);
            intent.putExtra(SEND, intentInfo);
            intent.putExtra(TODO_ITEM, todoItem);
            startActivityForResult(intent, REQUEST_CODE1);
    }

    private boolean notNullValues(TodoItem todoItem){
        if (todoItem.getTitle() != null && todoItem.getDescription() != null
                && todoItem.getDate() != null){

            return true;
        }
        return false;
    }
}