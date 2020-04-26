package com.example.gr.logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// AUTHOR @silsanteri

public class Database extends SQLiteOpenHelper {
    private static final String TAG = "DB";
    private static final String TABLE_NAME = "userdata";
    private static final String DATE = "date";
    private static final String WATER = "water";
    private static final String FOOD = "food";
    private static final String EXERCISE = "exercise";

    public Database(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " + DATE + " DATE, " + WATER + " INTEGER, " + FOOD + " INTEGER, " + EXERCISE + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int il) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String date, int water, int food, int exercise) {
        long result;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATE, date);
        contentValues.put(WATER, water);
        contentValues.put(FOOD, food);
        contentValues.put(EXERCISE, exercise);

        // CHECK IF DATE ALREADY EXISTS
        // IF EXISTS RETURNS VALUE <= 1
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + DATE + " = '" + date + "'", null);
        if(cursor.getCount() <= 0){
            Log.d(TAG, "addData Insert.");
            result = db.insert(TABLE_NAME, null, contentValues);
        } else {
            Log.d(TAG, "addData Update.");
            contentValues.remove(DATE); // REMOVE DATE FROM CONTENTVALUES TO MINIMIZE POSSIBLE BUGS
            result = db.update(TABLE_NAME, contentValues, DATE + " = '" + date + "'", null);
        }
        cursor.close();

        // IF INSERTED INCORRECTLY IT WILL RETURN -1
        if (result == -1) {
            Log.d(TAG, "addData Failed.");
            return false;
        } else {
            Log.d(TAG, "addData Successful.");
            return true;
        }
    }
}