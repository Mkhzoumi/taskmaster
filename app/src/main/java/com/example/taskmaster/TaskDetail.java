package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

//        AppDatabase db =  Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "taskMaster000").allowMainThreadQueries().build();
//        TaskDao userDao = db.taskDao();

        Intent intent = getIntent();
//
//        Task task = userDao.findById(intent.getExtras().getInt("id"));


        TextView titleText = findViewById(R.id.detailPageTitle);
        TextView state = findViewById(R.id.textView11);
        TextView desc = findViewById(R.id.textView8);

        titleText.setText(intent.getExtras().getString("title"));
        state.setText(intent.getExtras().getString("body"));
        desc.setText(intent.getExtras().getString("state"));
    }
}