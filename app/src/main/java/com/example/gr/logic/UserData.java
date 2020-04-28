package com.example.gr.logic;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Class includes userdata related functions.
 * Also includes Water, Food and Exercise subclasses solely used by UserData class.
 *
 * @author Santeri Silvennoinen (@silsanteri)
 * @version 1.0 04/2020
 */

public class UserData {
    private static final String TAG = "UserData.class";
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
        ArrayList<Integer> values = database.getData(this.date);
        this.water = new Water(values.get(0));
        this.food = new Food(values.get(1));
        this.exercise = new Exercise(values.get(2));
        Log.d(TAG, "UserData object constructed. Date: " + this.date + ". Values: (Water,Food,Exercise)("
                + this.water.returnWaterAmount() + "," + this.food.returnFoodAmount() + "," + this.exercise.returnExerciseAmount() + ")");
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
     * Removes water.
     *
     * @param amount amount of water to remove.
     */
    public void removeWater(int amount) {
        this.water.removeWater(amount);
    }

    /**
     * Removes food.
     *
     * @param amount amount of food to remove.
     */
    public void removeFood(int amount) {
        this.food.removeFood(amount);
    }

    /**
     * Removes exercise.
     *
     * @param amount amount of exercise to remove.
     */
    public void removeExercise(int amount) {
        this.exercise.removeExercise(amount);
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
     * Saves current values to database.
     * Uses addData function from Database class.
     */
    public void addDBData() {
        database.addData(this.date, returnWater(), returnFood(), returnExercise());
    }
}

// WATER -------------------------------------------------------------------------------------------
class Water {
    private int waterAmount;

    /**
     * Constructor for initial values.
     *
     * @param amount initial amount of water in milliliters (1 = 1ml, 100 = 100ml, etc).
     */
    public Water(int amount) {
        this.waterAmount = amount;
    }

    /**
     * Adds water.
     *
     * @param amount amount of water to add.
     */
    public void addWater(int amount) {
        this.waterAmount += amount;
    }

    /**
     * Removes water.
     *
     * @param amount amount of water to remove.
     */
    public void removeWater(int amount) {
        this.waterAmount -= amount;
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
    private int foodAmount;

    /**
     * Constructor for initial values.
     *
     * @param amount initial amount of food in calories (1 = 1kcal, 100 = 100kcal, etc).
     */
    public Food(int amount) {
        this.foodAmount = amount;
    }

    /**
     * Adds food.
     *
     * @param amount amount of food to add.
     */
    public void addFood(int amount) {
        this.foodAmount += amount;
    }

    /**
     * Removes food.
     *
     * @param amount amount of food to remove.
     */
    public void removeFood(int amount) {
        this.foodAmount -= amount;
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
    private int exerciseAmount;

    /**
     * Constructor for initial values.
     *
     * @param amount initial amount of exercise in minutes (1 = 1min, 100 = 100min, etc).
     */
    public Exercise(int amount) {
        this.exerciseAmount = amount;
    }

    /**
     * Adds exercise.
     *
     * @param amount amount of exercise to add.
     */
    public void addExercise(int amount) {
        this.exerciseAmount += amount;
    }

    /**
     * Removes exercise.
     *
     * @param amount amount of exercise to remove.
     */
    public void removeExercise(int amount) {
        this.exerciseAmount -= amount;
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