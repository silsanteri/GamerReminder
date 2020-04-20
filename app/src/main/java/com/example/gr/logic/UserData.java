package com.example.gr.logic;

import java.util.ArrayList;
import java.util.List;

// AUTHOR @silsanteri

public class UserData {

    // water = List of water added (in milliliters, 1 = 1ml, 100 = 100ml, etc.).
    // waterAmount = Total amount of water added.
    private List<Integer> water = new ArrayList<>();
    private int waterAmount;


    public void addWater(int amount) {
        // ADDS THE AMOUNT OF WATER ADDED
        this.water.add(amount);
        this.waterAmount += amount;
    }

    public void removeWater(int index) {
        //TODO
    }
}