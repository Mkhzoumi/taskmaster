package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Tasks;

import java.util.List;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);


    }

    @Override
    protected void onStart() {
        super.onStart();

        AppDatabase db =  Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "taskMaster000").allowMainThreadQueries().build();
        TaskDao taskDao = db.taskDao();
        Button addTask = findViewById(R.id.add);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Submitted!", Toast.LENGTH_LONG).show();
                EditText tasTitle = findViewById(R.id.taskTitle);
                EditText desc = findViewById(R.id.descreption);
                EditText state = findViewById(R.id.stateOfTaskField);

                Task task = new Task(tasTitle.getText().toString(),desc.getText().toString(),state.getText().toString());

                taskDao.insertAll(task);



                Tasks tasks = Tasks.builder()
                        .title(tasTitle.getText().toString())
                        .body(desc.getText().toString())
                        .state(state.getText().toString())
                        .build();

                Amplify.API.mutate(
                        ModelMutation.create(tasks),
                        response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
                        error -> Log.e("MyAmplifyApp", "Create failed", error));



                Intent toHome = new Intent(AddTask.this,MainActivity.class);
                startActivity(toHome);




            }
        });

        int countNumber = taskDao.getAll().size();

        TextView count = findViewById(R.id.textView2);
        count.setText("Total Tasks : "+countNumber);
    }
}