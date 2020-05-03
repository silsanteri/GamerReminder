package com.example.gr.view;

import android.util.Log;
import android.view.View;

import com.example.gr.R;
import com.example.gr.logic.ItemType;
import com.example.gr.logic.DialogHandler;

/**
 * OnClickListener for add buttons.
 *
 * @author Ruslan (@dievskiy)
 * @version 1.0 04/2020
 */

public class AddListener implements View.OnClickListener {
    // VARIABLES
    private static final String TAG = "AddListener.class";
    private DialogHandler dialogHandler;

    /**
     * Constructor for all initial settings.
     *
     * @param dialogHandler
     */
    public AddListener(DialogHandler dialogHandler) {
        this.dialogHandler = dialogHandler;
    }

    /**
     * onClick @Override
     * Shows appropriate add dialog when add button is pressed.
     *
     * @param v View
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_calories: {
                dialogHandler.showAddDialog(ItemType.FOOD);
                break;
            }
            case R.id.btn_add_water: {
                dialogHandler.showAddDialog(ItemType.WATER);
                break;
            }
            case R.id.btn_add_exercise: {
                dialogHandler.showAddDialog(ItemType.EXERCISE);
                break;
            }
            default: {
                Log.w(TAG, "AddListener: Unknown button id.");
            }
        }
    }

}