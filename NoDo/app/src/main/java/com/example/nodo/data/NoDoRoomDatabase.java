package com.example.nodo.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.nodo.model.NoDo;

@Database(entities = {NoDo.class}, version = 1)
public abstract class NoDoRoomDatabase extends RoomDatabase {
    private static volatile NoDoRoomDatabase INSTANCE;

    public abstract NoDoDao noDoDao();

    public static NoDoRoomDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (NoDoRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context,
                            NoDoRoomDatabase.class,
                            "no_do_database"
                    ).addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDBAsynch(INSTANCE).execute();
        }
    };

    private static class PopulateDBAsynch extends AsyncTask<Void, Void, Void> {
        private final NoDoDao noDoDao;


        public PopulateDBAsynch(NoDoRoomDatabase instance) {
            noDoDao = instance.noDoDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
//            noDoDao.deleteAll();
//            //for testing
//            NoDo noDo = new NoDo("Buy a new car");
//            noDoDao.insert(noDo);
//            noDo = new NoDo("Buy a new motorcycle");
//            noDoDao.insert(noDo);
//            noDo = new NoDo("Buy a new house");
//            noDoDao.insert(noDo);
//            noDo = new NoDo("Buy a new flat");
//            noDoDao.insert(noDo);
            return null;
        }
    }
}
