package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button addTask = (Button) findViewById(R.id.addTask);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent goToAddTask = new Intent(MainActivity.this, AddTask.class);
                startActivity(goToAddTask);
            }
        });

        Button allTasks = (Button) findViewById(R.id.allTasks);
        allTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAllTasks = new Intent(MainActivity.this, AllTasks.class);
                startActivity(goToAllTasks);
            }
        });


        Button presentation = findViewById(R.id.presentationButton);
        presentation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String presentationTitle = presentation.getText().toString();
                Intent goToPresentationDetails = new Intent(MainActivity.this, TaskDetail.class);
                goToPresentationDetails.putExtra("title",presentationTitle);
                startActivity(goToPresentationDetails);
            }
        });


        Button codeChallenge = findViewById(R.id.codeChallengeButton);
        codeChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codeChallengeTitle = codeChallenge.getText().toString();
                Intent goToCodeChallengeDetails = new Intent(MainActivity.this, TaskDetail.class);
                goToCodeChallengeDetails.putExtra("title",codeChallengeTitle);
                startActivity(goToCodeChallengeDetails);
            }
        });



        Button solveLab = findViewById(R.id.solveLabButton);
        solveLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String labTitle = solveLab.getText().toString();
                Intent goToLabDetails = new Intent(MainActivity.this, TaskDetail.class);
                goToLabDetails.putExtra("title",labTitle);
                startActivity(goToLabDetails);
            }
        });

        Button setting = findViewById(R.id.settingsButton);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSettings = new Intent(MainActivity.this, Settings.class);
                startActivity(goToSettings);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        String tasks = "'s Tasks";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String username = sharedPreferences.getString("username", "user");

        TextView usernameField = findViewById(R.id.textView3);
        usernameField.setText(username + tasks);
    }
}