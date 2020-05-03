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


    /**
     * onCreate @Override
     * Creates database table.
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
        // CREATE NEW DATABASE TABLE
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " + DATE + " DATE, " + WATER + " INTEGER, " + FOOD + " INTEGER, " + EXERCISE + " INTEGER)");
    }

    /**
     * onUpgrade @Override
     * Drops table and creates a new one on upgrade.
     *
     * @param db
     * @param i
     * @param il
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int il) {
        Log.d(TAG, "onUpgrade");
        // DROPS EXISTING TABLE ON UPGRADE
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // CALLS onCreate TO CREATE A NEW TABLE
        onCreate(db);
    }

    /**
     * Writes given parameter values to given date in database.
     *
     * @param date     Date in yyyy-MM-dd format.
     * @param water    Amount of water.
     * @param food     Amount of food.
     * @param exercise Amount of exercise.
     * @return boolean Returns false if data addition fails.
     */
    public boolean addData(String date, int water, int food, int exercise) {
        // GET WRITABLE DATABASE
        SQLiteDatabase db = this.getWritableDatabase();
        //SETS PARAMETERS TO CONTENTVALUES
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATE, date);
        contentValues.put(WATER, water);
        contentValues.put(FOOD, food);
        contentValues.put(EXERCISE, exercise);
        // RESULT VARIABLE
        long result;
        // SETS ID VARIABLE TO DATE PARAMETER DATE ID
        int id = checkData(date);

        if (id == -1) {
            Log.d(TAG, "addData Insert");
            result = db.insert(TABLE_NAME, null, contentValues);
        } else {
            Log.d(TAG, "addData Update");
            contentValues.remove(DATE); // REMOVE DATE TO MINIMIZE BUGS
            result = db.update(TABLE_NAME, contentValues, "id  = '" + id + "'", null);
        }
        // RESULT = -1 IF UPDATE OR INSERT FAILS
        if (result == -1) {
            // RETURNS FALSE IF UPDATE OR INSERT FAILS
            Log.d(TAG, "addData Failed");
            return false;
        } else {
            // RETURNS TRUE IF UPDATE OR RESULT IS SUCCESSFUL
            Log.d(TAG, "addData Successful");
            return true;
        }
    }

    /**
     * Deletes all data from database and resets the AUTOINCREMENT'ed id to 1.
     */
    public void deleteData() {
        Log.d(TAG, "deleteData executed.");
        // GET WRITABLE DATABASE
        SQLiteDatabase db = this.getWritableDatabase();
        // DELETES EVERYTHING FROM THE TABLE
        db.execSQL("DELETE FROM " + TABLE_NAME);
        // RESETS THE AUTOINCREMENT ID TO 1
        db.execSQL("DELETE FROM sqlite_sequence WHERE name='" + TABLE_NAME + "'");
    }


    /**
     * Gets values for the specific day from database.
     *
     * @param date Date in yyyy-MM-dd format.
     * @return ArrayList<Integer>, [0] = water, [1] = food, [2] = exercise.
     */
    public ArrayList<Integer> getData(String date) {
        // GET WRITABLE DATABASE
        SQLiteDatabase db = this.getWritableDatabase();
        // VARIABLES
        ArrayList<Integer> values = new ArrayList<>();
        int index, water, food, exercise;
        int id = checkData(date); // SETS ID VARIABLE TO DATE PARAMETER DATE ID

        if (id == -1) {
            // IF DATE DOESN'T EXIST IN DATABASE
            // SETS ALL VALUES TO 0 AND RETURNS THEM
            values.add(0);
            values.add(0);
            values.add(0);
            Log.d(TAG, "getData returned: (Water,Food,Exercise)(" + values.get(0) + "," + values.get(1) + "," + values.get(2) + ")");
            return values;
        } else {
            // IF DATE EXISTS
            // GETS ALL VALUES FROM DATE ID DATABASE TO CURSOR
            Cursor cursor = db.rawQuery("SELECT " + WATER + ", " + FOOD + ", " + EXERCISE + " FROM " + TABLE_NAME + " WHERE id = '" + id + "'", null);
            // ADD WATER TO ARRAY
            index = cursor.getColumnIndexOrThrow(WATER);
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
            // CLOSES CURSOR
            cursor.close();
            Log.d(TAG, "getData returned: (Water,Food,Exercise)(" + values.get(0) + "," + values.get(1) + "," + values.get(2) + ")");
            // RETURNS VALUES
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
        // GET WRITABLE DATABASE
        SQLiteDatabase db = this.getWritableDatabase();
        // DEFAULT ID -1
        int id = -1;
        // DATABASE QUERY TO CHECK IF DATE EXISTS IN DATABASE
        Cursor query = db.rawQuery("SELECT id FROM " + TABLE_NAME + " WHERE " + DATE + " = '" + date + "'", null);
        if (query.getCount() <= 0) {
            // DATE DOES NOT EXIST
            // CLOSES QUERY
            query.close();
            Log.d(TAG, "checkData did not find entries on " + date);
            // RETURNS DEFAULT ID (-1)
            return id;
        } else {
            // DATE EXISTS
            // GETS ID INDEX FROM QUERY
            int idIndex = query.getColumnIndexOrThrow("id");
            // WRITES DATE ID FROM DATABASE TO "ID" VARIABLE
            if (query.moveToFirst()) {
                id = query.getInt(idIndex);
            }
            // CLOSES QUERY
            query.close();
            Log.d(TAG, "checkData found an entry of " + date + " with id = " + id);
            // RETURNS THE FOUND DATE ID
            return id;
        }
    }

    /**
     * Searches values of specific type for all possible dates.
     *
     * @param type Type to be searched
     * @return List of pairs, where first refers to date, and second - to value
     */
    public List<Pair<String, Integer>> getAllPairedValues(ItemType type) {
        // GET WRITABLE DATABASE
        SQLiteDatabase db = this.getWritableDatabase();
        // VARIABLES
        String typeString = type.toString().toLowerCase();
        List<Pair<String, Integer>> list = new ArrayList<>();
        // DATABASE QUERY TO GET PAIRED VALUES
        Cursor cursor = db.rawQuery("SELECT " + typeString + ", " + DATE + " FROM " + TABLE_NAME, null);
        // ADDS PAIRED VALUES TO ARRAYLIST
        while (cursor.moveToNext()) {
            int index_exercise = cursor.getColumnIndexOrThrow(typeString);
            int index_date = cursor.getColumnIndexOrThrow(DATE);
            list.add(new Pair(cursor.getString(index_date), cursor.getInt(index_exercise)));
        }
        // CLOSES QUERY
        cursor.close();
        // RETURNS VALUES
        return list;
    }

    /**
     * Searches values of specific type for all possible dates.
     *
     * @param type Type to be searched
     * @return List of values
     */
    public List<Integer> getAllValues(ItemType type) {
        // GET WRITABLE DATABASE
        SQLiteDatabase db = this.getWritableDatabase();
        // VARIABLES
        String typeString = type.toString().toLowerCase();
        List<Integer> list = new ArrayList<>();
        // DATABASE QUERY TO GET ALL VALUES
        Cursor cursor = db.rawQuery("SELECT " + typeString + " FROM " + TABLE_NAME, null);
        // ADDS ALL VALUES TO ARRAYLIST
        while (cursor.moveToNext()) {
            int index_exercise = cursor.getColumnIndexOrThrow(typeString);
            list.add(cursor.getInt(index_exercise));
        }
        // CLOSES QUERY
        cursor.close();
        // RETURNS VALUES
        return list;
    }

    /**
     * Updates item value.
     *
     * @param type     type to be updated
     * @param date     date to be updated
     * @param newValue new value to set
     */
    public void updateItemValue(ItemType type, String date, int newValue) {
        // GET WRITABLE DATABASE
        SQLiteDatabase db = this.getWritableDatabase();
        // SETS PARAMETERS TO CONTENTVALUES
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATE, date);
        contentValues.put(type.toString().toLowerCase(), newValue);
        // CHECK IF CURRENT DATE EXISTS
        int id = checkData(date);
        // UPDATES DB WITH CONTENTVALUES
        db.update(TABLE_NAME, contentValues, "id  = '" + id + "'", null);
    }

}