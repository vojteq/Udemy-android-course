package com.example.notebookapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

import androidx.annotation.Nullable;

import com.example.notebookapp.R;
import com.example.notebookapp.model.Note;
import com.example.notebookapp.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY, "
                + Util.KEY_PRODUCT_NAME + " TEXT, "
                + Util.KEY_QUANTITY + "INTEGER, "
                + Util.KEY_DATE + " TEXT"
                + ")";
        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = String.valueOf(R.string.drop_table);
        db.execSQL(DROP_TABLE, new String[] {Util.TABLE_NAME});

        onCreate(db);
    }

    public void addNote(Note note) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Util.KEY_PRODUCT_NAME, note.getProductName());
        contentValues.put(Util.KEY_QUANTITY, note.getQuantity());
        contentValues.put(Util.KEY_DATE, note.getStringDate());

        database.insert(Util.TABLE_NAME, null, contentValues);
        database.close();
    }

    public void deleteNote(Note note) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(Util.TABLE_NAME,
                Util.KEY_ID + "=?",
                new String[] {String.valueOf(note.getId())}
                );
        database.close();
    }

    public int updateNote(Note note) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.KEY_PRODUCT_NAME, note.getProductName());
        contentValues.put(Util.KEY_QUANTITY, note.getQuantity());
        contentValues.put(Util.KEY_DATE, note.getStringDate());

        int updatedRow = database.update(Util.TABLE_NAME,
                contentValues,
                Util.KEY_ID + "=?",
                new String[] {String.valueOf(note.getId())}
                );
        database.close();
        return updatedRow;
    }

    public int getCount() {
        SQLiteDatabase database = this.getReadableDatabase();
        String selectAllQuery = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = database.rawQuery(selectAllQuery, null);
        int count = cursor.getCount();
        cursor.close();
        database.close();
        return count;
    }

    public Note getNote(int id) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(
                Util.TABLE_NAME,
                new String[] {
                        Util.KEY_ID,
                        Util.KEY_PRODUCT_NAME,
                        Util.KEY_QUANTITY,
                        Util.KEY_DATE},
                Util.KEY_ID + "=?",
                new String[] {String.valueOf(id)},
                null,
                null,
                null
        );

        Note note;

        if (cursor != null && cursor.moveToFirst()) {
            note = new Note(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3)
            );
            cursor.close();
        }
        else
            note = null;

        database.close();
        return note;
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String selectAllQuery = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = database.rawQuery(selectAllQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                notes.add(new Note(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3)
                ));
            } while (cursor.moveToNext());
            cursor.close();
        }
        database.close();
        return notes;
    }
}
