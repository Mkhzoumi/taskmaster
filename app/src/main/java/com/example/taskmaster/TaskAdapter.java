package com.example.taskmaster;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.TaskMaster;
import com.amplifyframework.datastore.generated.model.Tasks;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{
    List<TaskMaster> allTasks = new ArrayList<>();


    public TaskAdapter(List<TaskMaster> allTasks){
        this.allTasks = allTasks;
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder{

        View itemView;
        public TaskMaster task;



        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

            itemView.findViewById(R.id.taskFragmentButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toDetailsPage = new Intent(v.getContext(),TaskDetail.class);
                    toDetailsPage.putExtra("title",task.getTitle());
                    toDetailsPage.putExtra("body",task.getBody());
                    toDetailsPage.putExtra("state",task.getState());
                    v.getContext().startActivity(toDetailsPage);
                }
            });
        }
    }
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task,parent,false);
        TaskViewHolder taskViewHolder = new TaskViewHolder(view);
        return taskViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.task = allTasks.get(position);

        Button taskButton = holder.itemView.findViewById(R.id.taskFragmentButton);

        taskButton.setText(holder.task.getTitle());


    }

    @Override
    public int getItemCount() {
        return allTasks.size();
    }
}
