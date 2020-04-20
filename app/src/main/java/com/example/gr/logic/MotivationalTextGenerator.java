package com.example.gr.logic;

// AUTHOR ruslan

import android.content.Context;

import com.example.gr.R;

import java.util.Random;

/**
 * Class that generates random motivational texts from resource folder
 */
public class MotivationalTextGenerator {

    private Context context;

    public MotivationalTextGenerator(Context context) {
        this.context = context;
    }

    public String getRandomText() {
        // get array of motivational texts
        String[] array_motivational = context.getResources().getStringArray(R.array.motivational);
        // get random motivational number
        int num = new Random().nextInt(array_motivational.length);
        return array_motivational[num];
    }
}
