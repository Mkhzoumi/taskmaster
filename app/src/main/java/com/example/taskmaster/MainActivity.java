package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TaskMaster;
import com.amplifyframework.datastore.generated.model.Team;

import java.util.ArrayList;
import java.util.List;

import static com.amazonaws.mobile.client.internal.oauth2.OAuth2Client.TAG;

public class MainActivity extends AppCompatActivity {

    public void login(){
        Amplify.Auth.signInWithWebUI(
                this,
                result -> Log.i("AuthQuickStart", result.toString()),
                error -> Log.e("AuthQuickStart", error.toString())
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }

        login();



        Button logoutButton =  findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Amplify.Auth.signOut(
                () -> {
                    login();
                    Log.i("AuthQuickstart", "Signed out successfully");
                },
                error -> Log.e("AuthQuickstart", error.toString())
        );
            }
        });



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


        Button setting = findViewById(R.id.settingsButton);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSettings = new Intent(MainActivity.this, Settings.class);
                startActivity(goToSettings);
            }
        });


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String team = sharedPreferences.getString("team", "team");



        RecyclerView allTasksRecuclerView = findViewById(R.id.tasksRecucleView);
        List<Team> teams = new ArrayList<>();
        List<TaskMaster> tasks = new ArrayList<>();


        Handler handler =new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                allTasksRecuclerView.getAdapter().notifyDataSetChanged();
                return false;
            }
        });







        allTasksRecuclerView.setLayoutManager(new LinearLayoutManager(this));
        allTasksRecuclerView.setAdapter(new TaskAdapter(tasks));



        Amplify.API.query(
                ModelQuery.list(Team.class),
                response -> {
                    for (Team teamo : response.getData()) {
                        Log.i("MyAmplifyApp", teamo.getName());
                        Log.i("MyAmplifyApp", teamo.getId());

                        ///add new data to array
                        teams.add(teamo);
                    }
                    for (int i = 0; i < teams.size(); i++) {
                        if (teams.get(i).getName().equals(team)){
                            tasks.addAll(teams.get(i).getTasks());
                            break;
                        }
                    }

                    handler.sendEmptyMessage(1);
                    Log.i("MyAmplifyApp", "outside the loop");
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );





        // mkhzoumi    f7441181-f7a9-42f0-96e0-830c77066f0a
        // ASAC        2a61bec2-e32e-4a11-810e-da6da8b82ffc
        // dev team    e8d207d2-32aa-479a-9414-49a25ee268f2











//                AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "taskMaster000").allowMainThreadQueries().build();
//        TaskDao userDao = db.taskDao();
//
//
//        List<Task> tasks = userDao.getAll();


    }



    @Override
    protected void onStart() {
        super.onStart();




        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
//        String username = sharedPreferences.getString("username", "user");
        String team = sharedPreferences.getString("team", "team");

//        Amplify.Auth.fetchUserAttributes(
//
//                attributes -> Log.i("AuthDemo", "User attributes = " +attributes.toString() ),
//                error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
//        );




        TextView usernameField = findViewById(R.id.textView3);
        usernameField.setText(com.amazonaws.mobile.client.AWSMobileClient.getInstance().getUsername() + "'s Tasks");

        TextView teamName = findViewById(R.id.teamNameHome);
        teamName.setText(team);

    }
}