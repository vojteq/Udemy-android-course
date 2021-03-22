package com.example.database.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.database.R;
import com.example.database.model.Contact;
import com.example.database.util.Util;

import java.io.UTFDataFormatException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

//    where we create out table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY, "
                + Util.KEY_NAME + " TEXT, "
                + Util.KEY_PHONE_NUMBER + " TEXT"
                + ")";
        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = String.valueOf(R.string.db_drop);
        db.execSQL(DROP_TABLE, new String[]{Util.DATABASE_NAME});

        onCreate(db);
    }

    /*
    CRUD = Create, Read, Update, Delete
     */

    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, contact.getName());
        values.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());

        db.insert(Util.TABLE_NAME, null, values);
        Log.d("DBHandler", "addContact: " + "item added");
        db.close();
    }

    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                Util.TABLE_NAME,
                new String[] {Util.KEY_ID, Util.KEY_NAME, Util.KEY_PHONE_NUMBER},
                Util.KEY_ID + "=?",
                new String[] {String.valueOf(id)},
                null,
                null,
                null
                );

        if (cursor != null) {
            cursor.moveToFirst();
            Contact contact = new Contact(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            cursor.close();
            return contact;
        }
        return null;
    }

    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectAll = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                contactList.add(new Contact(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)
                ));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return contactList;
    }

    public int updateContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, contact.getName());
        values.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());

//        update(tableName, values, where id = something)
        int updatedRow = db.update(Util.TABLE_NAME, values,
                Util.KEY_ID + "=?",
                new String[]{String.valueOf((contact.getId()))});

        db.close();
        return updatedRow;
    }

    public void deleteContact(Contact contact) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(
                Util.TABLE_NAME,
                Util.KEY_ID + "=?",
                new String[] {String.valueOf(contact.getId())}
                );
        db.close();
    }

    public int getCount() {
        String countQuery = "SELECT * FROM " + Util.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
}
