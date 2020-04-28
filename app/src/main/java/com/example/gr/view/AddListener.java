package com.example.gr.view;

import android.util.Log;
import android.view.View;

import com.example.gr.R;
import com.example.gr.logic.AddType;
import com.example.gr.logic.DialogHandler;

/**
 * OnClickListener for add buttons
 * @author Ruslan (@dievskiy)
 * @version 1.0 04/2020
 */

public class AddListener implements View.OnClickListener {
    private DialogHandler dialogHandler;

    public AddListener(DialogHandler dialogHandler) {
        this.dialogHandler = dialogHandler;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_calories: {
                dialogHandler.showAddDialog(AddType.CALORIES);
                break;
            }
            case R.id.btn_add_water: {
                dialogHandler.showAddDialog(AddType.WATER);
                break;
            }
            case R.id.btn_add_exercise: {
                dialogHandler.showAddDialog(AddType.EXERCISE);
                break;
            }
            default: {
                Log.w("AddListener.java", "AddListener: Unknown button id..");
            }
        }
    }

}