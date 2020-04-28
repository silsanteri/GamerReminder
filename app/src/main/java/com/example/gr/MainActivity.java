package com.example.gr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gr.logic.AddType;
import com.example.gr.logic.DialogHandler;
import com.example.gr.logic.MotivationalTextGenerator;
import com.example.gr.logic.UserData;
import com.example.gr.view.AddListener;

/**
 * Launcher Activity class.
 *
 * @author Ruslan (@dievskiy), Santeri Silvennoinen (@silsanteri)
 * @version 1.0 04/2020
 */

public class MainActivity extends AppCompatActivity implements DialogHandler {

    private static final int REQUEST_CODE_SETTINGS = 420;
    private static final String TAG = "MainActivity.class";

    private Button btn_exercise;
    private Button btn_calories;
    private Button btn_drink;

    private MotivationalTextGenerator textGenerator;
    UserData mUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textGenerator = new MotivationalTextGenerator(this);
        setUpViews();

        mUserData = new UserData(this);
    }

    @Override
    protected void onPause() {
        mUserData.addDBData();
        super.onPause();
    }

    private void setUpViews() {
        // todo set relevant time
        ((TextView) findViewById(R.id.txt_notification_next))
                .setText(getString(R.string.notification_next, "04", "20"));

        // set motivational text
        ((TextView) findViewById(R.id.txt_motivational)).setText(textGenerator.getRandomText());

        // set up buttons
        btn_exercise = findViewById(R.id.btn_add_exercise);
        btn_drink = findViewById(R.id.btn_add_water);
        btn_calories = findViewById(R.id.btn_add_calories);

        View.OnClickListener addListener = new AddListener(this);
        btn_exercise.setOnClickListener(addListener);
        btn_drink.setOnClickListener(addListener);
        btn_calories.setOnClickListener(addListener);
    }

    /**
     * Show dialog to get input from user and add it to new value.
     *
     * @param type AddType that should be modified.
     * @author Ruslan (@dievskiy)
     */
    @Override
    public void showAddDialog(final AddType type) {
        final View dialogView = View.inflate(this, R.layout.dialog_add, null);
        final EditText editText = dialogView.findViewById(R.id.edit_value);
        String title = type.toString().toLowerCase();
        // capitalize title
        // source https://stackoverflow.com/questions/3904579/how-to-capitalize-the-first-letter-of-a-string-in-java
        title = title.substring(0, 1).toUpperCase() + title.substring(1);

        // source https://stackoverflow.com/questions/26097513/android-simple-alert-dialog
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(getString(R.string.dialog_add_message));
        alertDialog.setView(dialogView);

        // set positive button
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // try in case user has inserted non-decimal value
                        try {
                            addAmount(type, Integer.valueOf(editText.getText().toString()));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, R.string.toast_not_saved, Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "onClick: wrong input from user".concat(editText.getText().toString()));
                        }
                    }
                });

        // set negative button
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    /**
     * Adds amount to the user data in database.
     *
     * @param type   AddType to be changed.
     * @param amount Amount to add.
     * @author Ruslan (@dievskiy)
     */
    private void addAmount(AddType type, Integer amount) {
        switch (type) {
            case WATER:
                mUserData.addWater(amount);
                break;
            case CALORIES:
                mUserData.addFood(amount);
                break;
            case EXERCISE:
                mUserData.addExercise(amount);
                break;
        }
        Toast.makeText(MainActivity.this, R.string.toast_saved_succ, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_info:
                startHistoryActivity();
                break;
            case R.id.menu_settings:
                // start SettingsActivity if settings icon clicked
                startSettingsActivity();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return false;

    }

    private void startHistoryActivity() {
        Intent historyIntent = new Intent(this, HistoryActivity.class);
        startActivity(historyIntent);
    }

    private void startSettingsActivity() {
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        // start for result to handle changes after user modifications
        startActivityForResult(settingsIntent, REQUEST_CODE_SETTINGS);
    }


    /**
     * Activate game mode.
     */
    public void activateGameMode(View view) {
        // todo
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // todo handle changes in settings
    }
}












