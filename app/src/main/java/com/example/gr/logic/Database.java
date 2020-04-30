package com.example.gr.logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that contains database related functions.
 *
 * @author Santeri Silvennoinen (@silsanteri), Ruslan (@dievskiy)
 * @version 1.0 04/2020
 */

public class Database extends SQLiteOpenHelper {
    private static final String TAG = "Database.class";
    private static final String TABLE_NAME = "userdata";
    private static final String DATE = "date";
    private static final String WATER = "water";
    private static final String FOOD = "food";
    private static final String EXERCISE = "exercise";

    /**
     * Constructor for all initial settings.
     *
     * @param context context
     */
    public Database(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " + DATE + " DATE, " + WATER + " INTEGER, " + FOOD + " INTEGER, " + EXERCISE + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int il) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Writes given parameter values to given date in database.
     *
     * @param date     Date in yyyy-MM-dd format.
     * @param water    Amount of water.
     * @param food     Amount of food.
     * @param exercise Amount of exercise.
     * @return boolean Returns false if adding data fails.
     */
    public boolean addData(String date, int water, int food, int exercise) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATE, date);
        contentValues.put(WATER, water);
        contentValues.put(FOOD, food);
        contentValues.put(EXERCISE, exercise);
        long result;
        int id = checkData(date);

        if (id == -1) {
            Log.d(TAG, "addData Insert.");
            result = db.insert(TABLE_NAME, null, contentValues);
        } else {
            Log.d(TAG, "addData Update.");
            contentValues.remove(DATE); // REMOVE DATE TO MINIMIZE BUGS
            result = db.update(TABLE_NAME, contentValues, "id  = '" + id + "'", null);
        }

        // RETURNS -1 IF UPDATE OR INSERT FAILS
        if (result == -1) {
            Log.d(TAG, "addData Failed.");
            return false;
        } else {
            Log.d(TAG, "addData Successful.");
            return true;
        }
    }

    /**
     * Gets values for the specific day from database.
     *
     * @param date Date in yyyy-MM-dd format.
     * @return ArrayList<Integer>, [0] = water, [1] = food, [2] = exercise.
     */
    public ArrayList<Integer> getData(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Integer> values = new ArrayList<>();
        int index, water, food, exercise, id = checkData(date);

        if (id == -1) {
            values.add(0); // ADD 0 TO WATER.
            values.add(0); // ADD 0 TO FOOD.
            values.add(0); // ADD 0 TO EXERCISE.
            Log.d(TAG, "getData returned: (Water,Food,Exercise)(0,0,0)");
            return values;
        } else {
            Cursor cursor = db.rawQuery("SELECT " + WATER + ", " + FOOD + ", " + EXERCISE + " FROM " + TABLE_NAME + " WHERE id = '" + id + "'", null);
            index = cursor.getColumnIndexOrThrow(WATER);
            // ADD WATER TO ARRAY
            if (cursor.moveToFirst()) {
                water = cursor.getInt(index);
                values.add(water);
            }
            // ADD FOOD TO ARRAY
            index = cursor.getColumnIndexOrThrow(FOOD);
            if (cursor.moveToFirst()) {
                food = cursor.getInt(index);
                values.add(food);
            }
            // ADD EXERCISE TO ARRAY
            index = cursor.getColumnIndexOrThrow(EXERCISE);
            if (cursor.moveToFirst()) {
                exercise = cursor.getInt(index);
                values.add(exercise);
            }
            cursor.close();
            Log.d(TAG, "getData returned: (Water,Food,Exercise)(" + values.get(0) + "," + values.get(1) + "," + values.get(2) + ")");
            return values;
        }
    }


    /**
     * Checks if specific date entries exists in database.
     *
     * @param date Date in yyyy-MM-dd format.
     * @return int returns id of the date if found, otherwise returns -1.
     */
    public int checkData(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        int id = -1;

        Cursor query = db.rawQuery("SELECT id FROM " + TABLE_NAME + " WHERE " + DATE + " = '" + date + "'", null);
        if (query.getCount() <= 0) {
            Log.d(TAG, "checkData did not find entries on " + date);
            query.close();

            return id;
        } else {
            int idIndex = query.getColumnIndexOrThrow("id");
            if (query.moveToFirst()) {
                id = query.getInt(idIndex);
            }
            query.close();
            Log.d(TAG, "checkData found an entry of " + date + " with id = " + id);
            return id;
        }
    }

    // todo to helper

    /**
     * Searches values of specific type for all possible dates
     *
     * @param type Type to be searched
     * @return List of pairs, where first refers to date, and second - to value
     */
    public List<Pair<String, Integer>> getAllPairedValues(ItemType type) {
        String typeString = type.toString().toLowerCase();
        List<Pair<String, Integer>> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select " + typeString + ", " + DATE + " from " + TABLE_NAME, null);
        while (cursor.moveToNext()) {
            int index_exercise = cursor.getColumnIndexOrThrow(typeString);
            int index_date = cursor.getColumnIndexOrThrow(DATE);
            list.add(new Pair(cursor.getString(index_date), cursor.getInt(index_exercise)));
        }
        cursor.close();
        db.close();

        return list;
    }

    // todo to helper

    /**
     * Searches values of specific type for all possible dates
     *
     * @param type Type to be searched
     * @return List of values
     */
    public List<Integer> getAllValues(ItemType type) {
        String typeString = type.toString().toLowerCase();
        List<Integer> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select " + typeString + " from " + TABLE_NAME, null);
        while (cursor.moveToNext()) {
            int index_exercise = cursor.getColumnIndexOrThrow(typeString);
            list.add(cursor.getInt(index_exercise));
        }
        cursor.close();
        db.close();

        return list;
    }

    // todo to helper

    /**
     * Updates value
     *
     * @param type     type to be updated
     * @param date     date to be updated
     * @param newValue new value to set
     */
    public void updateItemValue(ItemType type, String date, int newValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATE, date);
        contentValues.put(type.toString().toLowerCase(), newValue);
        int id = checkData(date);
        db.update(TABLE_NAME, contentValues, "id  = '" + id + "'", null);
        Log.d("123", "updateItemValue: " + type + date + newValue);
        Log.d("123", "updateItemValue: " + getAllValues(ItemType.FOOD));
        db.close();
    }

}