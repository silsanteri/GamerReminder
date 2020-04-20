package com.example.gr.logic;

// AUTHOR @silsanteri

    public class UserData {
        private Water water;
        private Food food;
        private Exercise exercise;

        public UserData() {
        }
    }

    // WATER ------------------------------------
    class Water {
        // waterAmount in milliliters, 1 = 1ml, 100 = 100ml, etc.
        private int waterAmount;

        public Water(){
            this.waterAmount = 0;
        }

        public void addWater(int amount) {
            this.waterAmount += amount;
        }

        public void removeWater(int amount) {
            this.waterAmount -= amount;
        }

        public int returnWaterAmount(){
            return this.waterAmount;
        }
    }


    // FOOD --------------------------------------
    class Food {
        // FoodAmount in calories, 1 = 1kcal, 100 = 100kcal, etc.
        private int foodAmount;

        public Food(){
            this.foodAmount = 0;
        }

        public void addFood(int amount) {
            this.foodAmount += amount;
        }

        public void removeFood(int amount) {
            this.foodAmount -= amount;
        }

        public int returnFoodAmount(){
            return this.foodAmount;
        }
    }


    // EXERCISE --------------------------------
    class Exercise {
        // exerciseAmount in minutes, 1 = 1min, 100 = 100min, etc.
        private int exerciseAmount;

        public Exercise(){
            this.exerciseAmount = 0;
        }

        public void addExercise(int amount) {
            this.exerciseAmount += amount;
        }

        public void removeExercise(int amount) {
            this.exerciseAmount -= amount;
        }

        public int returnExerciseAmount(){
            return this.exerciseAmount;
        }
    }