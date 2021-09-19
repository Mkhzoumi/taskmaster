package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TaskMaster;
import com.amplifyframework.datastore.generated.model.Team;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

    }


    String imgName="";

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        if (intent.getType() != null && intent.getType().equals("text/plain")){
            EditText desc = findViewById(R.id.descreption);
            desc.setText(intent.getExtras().get(Intent.EXTRA_TEXT).toString());
        }

        List<Team> teams = new ArrayList<>();

        Amplify.API.query(
                ModelQuery.list(Team.class),
                response -> {
                    for (Team todo : response.getData()) {
                        Log.i("MyAmplifyApp", todo.getName());
                        teams.add(todo);

                    }
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );



        Button uploadImg = findViewById(R.id.uploadImg);
        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileChoose();
            }
        });

//        AppDatabase db =  Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "taskMaster000").allowMainThreadQueries().build();
//        TaskDao taskDao = db.taskDao();
        Button addTask = findViewById(R.id.add);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Submitted!", Toast.LENGTH_LONG).show();


                EditText tasTitle = findViewById(R.id.taskTitle);
                EditText desc = findViewById(R.id.descreption);
                EditText state = findViewById(R.id.stateOfTaskField);

//                Task task = new Task(tasTitle.getText().toString(),desc.getText().toString(),state.getText().toString());
//
//                taskDao.insertAll(task);



                RadioButton mkhzoumi = findViewById(R.id.mkhzoumiRadio);
                RadioButton asac = findViewById(R.id.asacRadio);

                RadioButton dev = findViewById(R.id.devRadio);

               String name="";
               if(mkhzoumi.isChecked()) {
//                   id = "f7441181-f7a9-42f0-96e0-830c77066f0a";
                   name="Mkhzoumi";
                }else if(asac.isChecked()){
                   name = "ASAC";
               }else if(dev.isChecked()){
                   name = "Dev Team";
               }


                Team team=null;
                for (int i = 0; i < teams.size(); i++) {
                    if(teams.get(i).getName().equals(name)){
                        team = teams.get(i);
                    }
                }



                TaskMaster task = TaskMaster.builder()
                        .title(tasTitle.getText().toString())
                        .body(desc.getText().toString())
                        .state(state.getText().toString())
                        .imgName(imgName)
                        .team(team)
                        .build();


                Amplify.API.mutate(
                        ModelMutation.create(task),
                        response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
                        error -> Log.e("MyAmplifyApp", "Create failed", error));



                Intent toHome = new Intent(AddTask.this,MainActivity.class);
                startActivity(toHome);

            }
        });





        TextView count = findViewById(R.id.textView2);
        count.setText("Total Tasks : ");
    }


    public void fileChoose(){
        Intent fileChoose=new Intent(Intent.ACTION_GET_CONTENT);
        fileChoose.setType("*/*");
        fileChoose=Intent.createChooser(fileChoose,"choose file");
        startActivityForResult(fileChoose,1111);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        try {
            InputStream exampleInputStream = getContentResolver().openInputStream(data.getData());
            System.out.println("hhhhhhhh"+data.getData().toString());

            imgName = data.getData().toString();


            Amplify.Storage.uploadInputStream(
                    data.getData().toString(),
                    exampleInputStream,
                    result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
                    storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
            );
        }  catch (FileNotFoundException error) {
            Log.e("MyAmplifyApp", "Could not find file to open for input stream.", error);
        }
    }


}

