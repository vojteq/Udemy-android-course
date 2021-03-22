package com.example.nodo.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.nodo.model.NoDo;

import java.util.List;

//DAO -> data access object
@Dao
public interface NoDoDao {
    //CRUD
    @Insert
    void insert(NoDo noDo);

    @Query("DELETE FROM no_do_table")
    void deleteAll();

    @Query("DELETE FROM no_do_table WHERE id = :id")
    int deleteNoDo(int id);

    @Query("UPDATE no_do_table SET no_do_column = :newText WHERE id = :id")
    int updateNoDo(int id, String newText);

    @Query("SELECT * FROM no_do_table ORDER BY no_do_column DESC")
    LiveData<List<NoDo>> getAllNoDos();
}
