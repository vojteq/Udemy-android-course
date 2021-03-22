package com.example.nodo.util;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.nodo.data.NoDoDao;
import com.example.nodo.data.NoDoRoomDatabase;
import com.example.nodo.model.NoDo;

import java.util.List;

public class NoDoRepository {
    private NoDoDao noDoDao;
    private LiveData<List<NoDo>> allNoDos;

    public NoDoRepository(Application application) {
        NoDoRoomDatabase db = NoDoRoomDatabase.getInstance(application);

        noDoDao = db.noDoDao();
        allNoDos = noDoDao.getAllNoDos();
    }

    public LiveData<List<NoDo>> getAllNoDos() {
        return allNoDos;
    }

    public void insert(NoDo noDo) {
        new InsertAsyncTask(noDoDao).execute(noDo);
    }

    private class InsertAsyncTask extends AsyncTask<NoDo, Void, Void> {
        private NoDoDao asyncTaskDao;

        public InsertAsyncTask(NoDoDao noDoDao) {
            asyncTaskDao = noDoDao;
        }

        @Override
        protected Void doInBackground(NoDo... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
