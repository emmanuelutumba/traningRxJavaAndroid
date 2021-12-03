package com.creasmit.task.data.repository.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.creasmit.task.data.model.Task;

import java.util.List;

import retrofit2.http.DELETE;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task")
    List<Task> getList();

    @Insert
    List<Task> insertAll(List<Task> task);

    @DELETE
    long delete(Task task);

    @Query("DELETE FROM Task")
    long deleteAll();

}
