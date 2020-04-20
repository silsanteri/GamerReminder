package com.example.gr.logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// AUTHOR @silsanteri
// SOURCE https://www.youtube.com/watch?v=aQAIMY-HzL8

public class Database extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "user_data";
    private static final String DATE = "date";
    private static final String WATER = "water";
    private static final String FOOD = "food";
    private static final String EXERCISE = "exercise";

    public Database(Context context){
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + DATE + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int il) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData (int water, int food, int exercise) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WATER, water);
        contentValues.put(FOOD, food);
        contentValues.put(EXERCISE, exercise);

        long result = db.insert(TABLE_NAME, null, contentValues);

        // IF INSERTED INCORRECTLY IT WILL RETURN -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
}