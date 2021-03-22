package com.example.shoppinglist.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.shoppinglist.model.Item;
import com.example.shoppinglist.util.Util;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEM_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY, "
                + Util.KEY_PRODUCT_NAME + " TEXT, "
                + Util.KEY_QUANTITY + " INTEGER, "
                + Util.KEY_DATE + " LONG"
                + ")";
        db.execSQL(CREATE_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);
        onCreate(db);
    }

    public void addItem(Item item) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Util.KEY_PRODUCT_NAME, item.getProductName());
        contentValues.put(Util.KEY_QUANTITY, item.getQuantity());
        contentValues.put(Util.KEY_DATE, java.lang.System.currentTimeMillis());     //timestamp of the system

        database.insert(Util.TABLE_NAME, null, contentValues);
        database.close();
    }

    public void deleteItem(Item item) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(
                Util.TABLE_NAME,
                Util.KEY_ID + "=?",
                new String[] {String.valueOf(item.getId())}
                );
        database.close();
    }

    public void deleteItem(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(
                Util.TABLE_NAME,
                Util.KEY_ID + "=?",
                new String[] {String.valueOf(id)}
        );
        database.close();
    }

    public int updateItem(Item item) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Util.KEY_PRODUCT_NAME, item.getProductName());
        contentValues.put(Util.KEY_QUANTITY, item.getQuantity());
        contentValues.put(Util.KEY_DATE, java.lang.System.currentTimeMillis());

        int updatedRow = database.update(
                Util.TABLE_NAME,
                contentValues,
                Util.KEY_ID + "=?",
                new String[] {String.valueOf(item.getId())}
        );
        database.close();
        return updatedRow;
    }

    public int getCount() {
        SQLiteDatabase database = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = database.rawQuery(
                SELECT_QUERY,
                null
        );
        int count = cursor.getCount();
        cursor.close();
        database.close();
        return count;
    }

    public Item getItem(int id) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(
                Util.TABLE_NAME,
                new String[] {
                        Util.KEY_ID,
                        Util.KEY_PRODUCT_NAME,
                        Util.KEY_QUANTITY,
                        Util.KEY_DATE
                },
                Util.KEY_ID + "=?",
                new String[] {String.valueOf(id)},
                null,
                null,
                null
        );
        Item item;
        if (cursor != null && cursor.moveToFirst()) {
            DateFormat dateFormat = DateFormat.getDateInstance();
            item = new Item(
                    cursor.getInt(cursor.getColumnIndex(Util.KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(Util.KEY_PRODUCT_NAME)),
                    cursor.getInt(cursor.getColumnIndex(Util.KEY_QUANTITY)),
                    dateFormat.format(
                            new Date(cursor.getLong(cursor.getColumnIndex(Util.KEY_DATE)))
                                    .getTime())
            );
            cursor.close();
        }
        else
            item = null;
        database.close();
        return item;
    }

    public List<Item> getAllItems() {
         List<Item> items = new ArrayList<>();
         SQLiteDatabase database = this.getReadableDatabase();
//         String SELECT_ALL_QUERY = "SELECT * FROM " + Util.TABLE_NAME;
//         Cursor cursor = database.rawQuery(SELECT_ALL_QUERY, null);

        Cursor cursor = database.query(
                Util.TABLE_NAME,
                new String[] {
                        Util.KEY_ID,
                        Util.KEY_PRODUCT_NAME,
                        Util.KEY_QUANTITY,
                        Util.KEY_DATE
                },
                null,
                null,
                null,
                null,
                Util.KEY_DATE + " DESC"
        );

         if (cursor != null && cursor.moveToFirst()) {
             DateFormat dateFormat = DateFormat.getDateInstance();
             do {
                 items.add(new Item(
                         cursor.getInt(cursor.getColumnIndex(Util.KEY_ID)),
                         cursor.getString(cursor.getColumnIndex(Util.KEY_PRODUCT_NAME)),
                         cursor.getInt(cursor.getColumnIndex(Util.KEY_QUANTITY)),
                         dateFormat.format(
                                 new Date(cursor.getLong(cursor.getColumnIndex(Util.KEY_DATE)))
                                         .getTime()
                 )));
             } while (cursor.moveToNext());
             cursor.close();
         }
         database.close();
         return items;
    }
}
