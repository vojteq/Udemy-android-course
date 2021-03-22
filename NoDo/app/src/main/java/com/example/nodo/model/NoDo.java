package com.example.nodo.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "no_do_table")
public class NoDo {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "no_do_column")
    private String noDo;

    public NoDo(@NonNull String noDo) {
        this.noDo = noDo;
    }

    public int getId() {
        return id;
    }

    public String getNoDo() {
        return noDo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNoDo(String noDo) {
        this.noDo = noDo;
    }
}
