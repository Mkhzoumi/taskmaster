package com.example.taskmaster;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM tasks")
    List<Task> getAll();



    @Query("SELECT * FROM tasks WHERE id LIKE :id")
    Task findById(int id) ;

    @Insert
    void insertAll(Task... task);

    @Delete
    void delete(Task task);

}
