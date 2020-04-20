package com.example.gr.logic;

import java.util.ArrayList;
import java.util.List;

// AUTHOR @silsanteri

public class UserData {

    // water = List of water added (in milliliters, 1 = 1ml, 100 = 100ml, etc.).
    // waterAmount = Total amount of water added.
    private List<Integer> water = new ArrayList<>();
    private int waterAmount;

    // food = List of calories added (in calories, 1 = 1kcal, 100 = 100kcal, etc.).
    // foodAmount = Total amount of calories added.
    private List<Integer> food = new ArrayList<>();
    private int foodAmount;

    // exercise = List of exercise added (in minutes, 1 = 1min, 100 = 100min, etc.).
    // exerciseAmount = Total amount of exercise added.
    private List<Integer> exercise = new ArrayList<>();
    private int exerciseAmount;

    // CONSTRUCTOR ------------------------------
    public UserData(){
    }

    // WATER ------------------------------------
    public void addWater(int amount) {
        this.water.add(amount);
        this.waterAmount += amount;
    }

    public void removeWater(int index) {
        this.water.remove(index);
    }

    public int returnWaterCount(){
        return this.water.size();
    }

    public int returnWaterAmount(){
        return this.waterAmount;
    }

    public List<Integer> returnWaterArray(){
        return this.water;
    }

    // FOOD --------------------------------------
    public void addFood(int amount) {
        this.food.add(amount);
        this.foodAmount += amount;
    }

    public void removeFood(int index) {
        this.food.remove(index);
    }

    public int returnFoodCount(){
        return this.food.size();
    }

    public int returnFoodAmount(){
        return this.foodAmount;
    }

    public List<Integer> returnFoodArray(){
        return this.food;
    }

    // EXERCISE --------------------------------
    public void addExercise(int amount) {
        this.exercise.add(amount);
        this.exerciseAmount += amount;
    }

    public void removeExercise(int index) {
        this.exercise.remove(index);
    }

    public int returnExerciseCount(){
        return this.exercise.size();
    }

    public int returnExerciseAmount(){
        return this.exerciseAmount;
    }

    public List<Integer> returnExerciseArray(){
        return this.exercise;
    }
}