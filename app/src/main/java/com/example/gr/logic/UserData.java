package com.example.gr.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Pair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Class includes userdata related functions.
 * Also includes Water, Food and Exercise subclasses solely used by UserData class.
 *
 * @author Santeri Silvennoinen (@silsanteri), Ruslan (@dievskiy)
 * @version 1.0 04/2020
 */

public class UserData {
    private static final String SETTINGS = "settings";
    private static final String TAG = "UserData.class";
    private int intakeLimit, exerciseLimit; // INTAKELIMIT IS THE SAME FOR FOOD AND WATER
    private Water water;
    private Food food;
    private Exercise exercise;
    private Database database;
    private String date;

    /**
     * Constructor for all initial settings.
     * Called on app startup.
     * Checks database for existing values on current day and initializes the class with them.
     *
     * @param context gets context for Database object.
     */
    public UserData(Context context) {
        this.database = new Database(context);
        this.date = getDate();

        this.intakeLimit = 10000;
        this.exerciseLimit = 1440; // 24*60

        this.updateValues();

        Log.d(TAG, "UserData object constructed. Date: " + this.date + ". Values: (Water,Food,Exercise)("
                + this.water.returnWaterAmount() + "," + this.food.returnFoodAmount() + "," + this.exercise.returnExerciseAmount() + ")");
    }

    /**
     * Updates main values.
     */
    public void updateValues() {
        ArrayList<Integer> values = database.getData(this.date);
        this.water = new Water(values.get(0), this.intakeLimit);
        this.food = new Food(values.get(1), this.intakeLimit);
        this.exercise = new Exercise(values.get(2), this.exerciseLimit);
    }

    /**
     * Adds water.
     *
     * @param amount amount of water to add.
     */
    public void addWater(int amount) {
        this.water.addWater(amount);
    }

    /**
     * Adds food.
     *
     * @param amount amount of food to add.
     */
    public void addFood(int amount) {
        this.food.addFood(amount);
    }

    /**
     * Adds exercise.
     *
     * @param amount amount of exercise to add.
     */
    public void addExercise(int amount) {
        this.exercise.addExercise(amount);
    }

    /**
     * Returns water amount of user in milliliters.
     *
     * @return int water amount.
     */
    public int returnWater() {
        return water.returnWaterAmount();
    }

    /**
     * Returns food intake amount of user in calories.
     *
     * @return int food amount.
     */
    public int returnFood() {
        return food.returnFoodAmount();
    }

    /**
     * Returns exercise amount of user in minutes.
     *
     * @return int exercise amount.
     */
    public int returnExercise() {
        return exercise.returnExerciseAmount();
    }

    /**
     * Returns daily intake limit for both water and food.
     *
     * @return int intake limit.
     */
    public int returnIntakeLimit() {
        return this.intakeLimit;
    }

    /**
     * Returns daily exercise limit.
     *
     * @return int exercise limit.
     */
    public int returnExerciseLimit() {
        return this.exerciseLimit;
    }

    /**
     * Returns current date in yyyy-MM-dd format.
     *
     * @return String current date.
     */
    private String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * Returns reversed list of pairs of date and amount
     *
     * @param type Type for which values needed
     * @return list of pairs
     */
    public List<Pair<String, Integer>> getAllValues(ItemType type) {
        final List<Pair<String, Integer>> list = database.getAllPairedValues(type);
        if (list != null) {
            Collections.reverse(list);
        }
        return list;
    }

    /**
     * Returns reversed list of pairs of date and amount
     *
     * @param type Type for which values needed
     * @return list of integers
     */
    public List<Integer> getAllValuesPlain(ItemType type) {
        final List<Integer> list = database.getAllValues(type);
        if (list != null) {
            Collections.reverse(list);
        }
        return list;
    }

    /**
     * Saves current values to database.
     * Uses addData function from Database class.
     */
    public void addDBData() {
        database.addData(this.date, returnWater(), returnFood(), returnExercise());
    }

    public int getValueByType(ItemType type) {
        switch (type) {
            case FOOD:
                return returnFood();
            case EXERCISE:
                return returnExercise();
            case WATER:
                return returnWater();
            default:
                return 0;
        }
    }

    public int getLimitByType(ItemType type) {
        switch (type) {
            case FOOD:
            case WATER:
                return returnIntakeLimit();
            case EXERCISE:
                return returnExerciseLimit();
            default:
                return 0;
        }
    }

    /**
     * @param type     Type of Item to update
     * @param date     Date
     * @param newValue Value to update
     */
    // todo move to helper
    public void editItem(ItemType type, String date, int newValue) {
        database.updateItemValue(type, date, newValue);
    }
    // todo move to helper

    /**
     * Deletes all data from database and inserts current day with empty values.
     */
    public void deleteAllData() {
        database.deleteData();
        database.addData(this.date, 0, 0, 0);
    }
}

// WATER -------------------------------------------------------------------------------------------
class Water {
    private static final String TAG = "UserData.class";
    private int waterAmount;
    private int waterLimit;

    /**
     * Constructor for initial values.
     *
     * @param amount initial amount of water in milliliters (1 = 1ml, 100 = 100ml, etc).
     * @param limit  limit of water intake.
     */
    public Water(int amount, int limit) {
        this.waterAmount = amount;
        this.waterLimit = limit;
    }

    /**
     * Adds water.
     *
     * @param amount amount of water to add.
     */
    public void addWater(int amount) {
        int newAmount = this.waterAmount + amount;
        if (waterLimit >= newAmount) {
            this.waterAmount = newAmount;
            Log.d(TAG, "addWater added water. New amount: " + this.waterAmount + "/" + this.waterLimit);
        } else {
            Log.d(TAG, "addWater did not add water.");
        }
    }

    /**
     * Returns water.
     *
     * @return int water amount.
     */
    public int returnWaterAmount() {
        return this.waterAmount;
    }
}


// FOOD --------------------------------------------------------------------------------------------
class Food {
    private static final String TAG = "UserData.class";
    private int foodAmount;
    private int foodLimit;

    /**
     * Constructor for initial values.
     *
     * @param amount initial amount of food in calories (1 = 1kcal, 100 = 100kcal, etc).
     * @param limit  limit of food intake.
     */
    public Food(int amount, int limit) {
        this.foodAmount = amount;
        this.foodLimit = limit;
    }

    /**
     * Adds food.
     *
     * @param amount amount of food to add.
     */
    public void addFood(int amount) {
        int newAmount = this.foodAmount + amount;
        if (foodLimit >= newAmount) {
            this.foodAmount = newAmount;
            Log.d(TAG, "addFood added food. New amount: " + this.foodAmount + "/" + this.foodLimit);
        } else {
            Log.d(TAG, "addFood did not add food.");
        }
    }

    /**
     * Returns food.
     *
     * @return int food amount.
     */
    public int returnFoodAmount() {
        return this.foodAmount;
    }
}


// EXERCISE ----------------------------------------------------------------------------------------
class Exercise {
    private static final String TAG = "UserData.class";
    private int exerciseAmount;
    private int exerciseLimit;

    /**
     * Constructor for initial values.
     *
     * @param amount initial amount of exercise in minutes (1 = 1min, 100 = 100min, etc).
     * @param limit  limit of exercise time.
     */
    public Exercise(int amount, int limit) {
        this.exerciseAmount = amount;
        this.exerciseLimit = limit;
    }

    /**
     * Adds exercise.
     *
     * @param amount amount of exercise to add.
     */
    public void addExercise(int amount) {
        int newAmount = this.exerciseAmount + amount;
        if (exerciseLimit >= newAmount) {
            this.exerciseAmount = newAmount;
            Log.d(TAG, "addExercise added exercise. New amount: " + this.exerciseAmount + "/" + this.exerciseLimit);
        } else {
            Log.d(TAG, "addExercise did not add exercise.");
        }
    }

    /**
     * Returns exercise.
     *
     * @return int exercise amount.
     */
    public int returnExerciseAmount() {
        return this.exerciseAmount;
    }
}