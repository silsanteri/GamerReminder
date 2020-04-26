package com.example.gr.logic;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// AUTHOR @silsanteri

public class UserData {
    private Water water;
    private Food food;
    private Exercise exercise;
    private Database database;

    public UserData(Context context) {
        water = new Water();
        food = new Food();
        exercise = new Exercise();
        database = new Database(context);
    }

    public int returnWater(){
        return water.returnWaterAmount();
    }

    public int returnFood(){
        return food.returnFoodAmount();
    }

    public int returnExercise(){
        return exercise.returnExerciseAmount();
    }

    // DATE FOR DB
    private String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    // DATABASE STORING
    public void addDBData(){
        database.addData(getDate(), returnWater(), returnFood(), returnExercise());
    }
}

// WATER ------------------------------------
class Water {
    // waterAmount in milliliters, 1 = 1ml, 100 = 100ml, etc.
    private int waterAmount;

    public Water() {
        this.waterAmount = 0;
    }

    public void addWater(int amount) {
        this.waterAmount += amount;
    }

    public void removeWater(int amount) {
        this.waterAmount -= amount;
    }

    public int returnWaterAmount() {
        return this.waterAmount;
    }
}


// FOOD --------------------------------------
class Food {
    // foodAmount in calories, 1 = 1kcal, 100 = 100kcal, etc.
    private int foodAmount;

    public Food() {
        this.foodAmount = 0;
    }

    public void addFood(int amount) {
        this.foodAmount += amount;
    }

    public void removeFood(int amount) {
        this.foodAmount -= amount;
    }

    public int returnFoodAmount() {
        return this.foodAmount;
    }
}


// EXERCISE --------------------------------
class Exercise {
    // exerciseAmount in minutes, 1 = 1min, 100 = 100min, etc.
    private int exerciseAmount;

    public Exercise() {
        this.exerciseAmount = 0;
    }

    public void addExercise(int amount) {
        this.exerciseAmount += amount;
    }

    public void removeExercise(int amount) {
        this.exerciseAmount -= amount;
    }

    public int returnExerciseAmount() {
        return this.exerciseAmount;
    }
}