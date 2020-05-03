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

import com.example.gr.logic.ItemType;
import com.example.gr.logic.DialogHandler;
import com.example.gr.logic.LocaleUtils;
import com.example.gr.logic.MotivationalTextGenerator;
import com.example.gr.logic.NotificationUtils;
import com.example.gr.logic.SharedPrefsUtils;
import com.example.gr.logic.UserData;
import com.example.gr.view.AddListener;

/**
 * MainActivity
 *
 * @author Ruslan (@dievskiy), Santeri Silvennoinen (@silsanteri), Tapi (@DXGGX)
 * @version 1.0 04/2020
 */

public class MainActivity extends AppCompatActivity implements DialogHandler {

    // STATIC FINAL VARIABLES
    private static final String TAG = "MainActivity.class";
    public static final int REQUEST_CODE_SETTINGS = 420;
    private static final int REQUEST_CODE_HISTORY = 4020;
    private static final int notificationId = 1;

    // GAMEMODE ACTIVE BOOLEAN
    private static boolean gamemodeActive;

    // UI VARIABLES
    private Button btn_exercise;
    private Button btn_calories;
    private Button btn_drink;
    private Button btn_game_mode;
    private TextView txt_total_cal;
    private TextView txt_total_water;
    private TextView txt_total_exer;
    private TextView txt_game_mode_activated;
    private TextView txt_notification_next;
    private TextView txt_game_mode_disabled;

    // OBJECTS
    private MotivationalTextGenerator textGenerator;
    private UserData mUserData;

    /**
     * onCreate @Override
     * Sets up UI and loads user's settings from SharedPrefs and values from database.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        // LOADS LOCALE FROM SHAREDPREFS
        LocaleUtils.loadLocale(MainActivity.this);
        // SUPER onCreate
        super.onCreate(savedInstanceState);
        // CREATES USERDATA OBJECT
        this.mUserData = new UserData(this);
        // LOADS USER'S SETTINGS
        this.gamemodeActive = SharedPrefsUtils.returnGameModeStatus(this);
        // SETS UP UI
        setContentView(R.layout.activity_main);
        this.textGenerator = new MotivationalTextGenerator(this);
        setUpViews();
    }

    /**
     * onPause @Override
     * Saves userdata to database and settings to SharedPrefs.
     */
    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        // SAVES USERDATA TO DATABASE
        this.mUserData.addDBData();
        // SAVES gamemodeActive TO SHAREDPREFS
        SharedPrefsUtils.saveGameModeStatus(this, this.gamemodeActive);
        super.onPause();
    }

    /**
     * Set up all views and add relevant values to them.
     */
    private void setUpViews() {
        Log.d(TAG, "setUpViews");
        // todo set relevant time
        ((TextView) findViewById(R.id.txt_notification_next))
                .setText(getString(R.string.notification_next, "04", "20"));

        // set motivational text
        ((TextView) findViewById(R.id.txt_motivational)).setText(textGenerator.getRandomText());

        // set game mode texts
        this.txt_game_mode_activated = findViewById(R.id.txt_game_mode_activated);
        this.txt_game_mode_disabled = findViewById(R.id.txt_game_mode_disabled);
        this.txt_notification_next = findViewById(R.id.txt_notification_next);

        // set up buttons
        this.btn_exercise = findViewById(R.id.btn_add_exercise);
        this.btn_drink = findViewById(R.id.btn_add_water);
        this.btn_calories = findViewById(R.id.btn_add_calories);
        this.btn_game_mode = findViewById(R.id.btn_game_mode);
        if (gamemodeActive) {
            setGameModeUI();
        }

        // add listener
        View.OnClickListener addListener = new AddListener(this);
        this.btn_exercise.setOnClickListener(addListener);
        this.btn_drink.setOnClickListener(addListener);
        this.btn_calories.setOnClickListener(addListener);

        // set relevant today values
        this.txt_total_exer = findViewById(R.id.txt_total_exer);
        this.txt_total_cal = findViewById(R.id.txt_total_cal);
        this.txt_total_water = findViewById(R.id.txt_total_water);
        updateTotalValues();
    }

    /**
     * Updates today's values.
     */
    private void updateTotalValues() {
        // UPDATES TODAYS VALUES
        Log.d(TAG, "updateTotalValues");
        this.txt_total_exer.setText(getString(R.string.total_exercise, mUserData.getValueByType(ItemType.EXERCISE)));
        this.txt_total_cal.setText(getString(R.string.total_calories, mUserData.getValueByType(ItemType.FOOD)));
        this.txt_total_water.setText(getString(R.string.total_water, mUserData.getValueByType(ItemType.WATER)));
    }

    /**
     * Show dialog to get input from user and add it to new value.
     *
     * @param type AddType that should be modified.
     */
    @Override
    public void showAddDialog(final ItemType type) {
        final View dialogView = View.inflate(this, R.layout.dialog_add, null);
        final EditText editText = dialogView.findViewById(R.id.edit_value);
        String title = type.getLocaledName(this);
        // capitalize title
        // source https://stackoverflow.com/questions/3904579/how-to-capitalize-the-first-letter-of-a-string-in-java
        title = title.substring(0, 1).toUpperCase() + title.substring(1);
        Log.d(TAG, "showAddDialog: " + title);

        // source https://stackoverflow.com/questions/26097513/android-simple-alert-dialog
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(title);

        final String message = getString(R.string.dialog_today, mUserData.getValueByType(type), mUserData.getLimitByType(type));
        alertDialog.setMessage(message);

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
     * Adds the input amount to the UserData object.
     *
     * @param type   AddType to be changed.
     * @param amount Amount to add.
     */
    private void addAmount(ItemType type, Integer amount) {
        switch (type) {
            case WATER:
                this.mUserData.addWater(amount);
                break;
            case FOOD:
                this.mUserData.addFood(amount);
                break;
            case EXERCISE:
                this.mUserData.addExercise(amount);
                break;
            default:
                return;
        }
        updateTotalValues();
        Toast.makeText(MainActivity.this, R.string.toast_saved, Toast.LENGTH_SHORT).show();
    }

    /**
     * onCreateOptionsMenu @Override
     *
     * @param menu
     * @return boolean true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    /**
     * onOptionsItemSelected @Override
     *
     * @param item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_info:
                // start HistoryActivity if history icon clicked
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

    /**
     * Function that starts history activity.
     */
    private void startHistoryActivity() {
        Intent historyIntent = new Intent(this, HistoryActivity.class);
        // start for result to handle changes after user modifications
        startActivityForResult(historyIntent, REQUEST_CODE_HISTORY);
    }

    /**
     * Function that starts settings activity.
     */
    private void startSettingsActivity() {
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        // start for result to handle changes after user modifications
        startActivityForResult(settingsIntent, REQUEST_CODE_SETTINGS);
    }


    /**
     * Toggles game mode on or off.
     * Customizes UI to indicate Game Mode activation.
     *
     * @param view
     */
    public void activateGameMode(View view) {
        if (this.gamemodeActive) {
            // IF GAME MODE WAS ACTIVE WHEN CLICKED
            this.gamemodeActive = false;
            // SETS THE UI TO DEFAULT UI
            this.btn_game_mode.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            this.txt_game_mode_activated.setVisibility(View.INVISIBLE);
            this.txt_notification_next.setVisibility(View.INVISIBLE);
            this.txt_game_mode_disabled.setVisibility(View.VISIBLE);
        } else {
            // IF GAME MODE WAS NOT ACTIVE WHEN CLICKED
            this.gamemodeActive = true;
            // SETS THE UI TO VISUALLY INDICATE GAME MODE ACTIVATION
            setGameModeUI();
            // STARTS THE REPEATING NOTIFICATIONS
            NotificationUtils.sendNotification(
                    this,
                    this.notificationId,
                    getResources().getString(R.string.app_name),
                    getResources().getString(R.string.notification_content));
        }
    }

    /**
     * Sets the UI to visually indicate Game Mode activation.
     */
    private void setGameModeUI() {
        this.btn_game_mode.setBackgroundColor(getResources().getColor(R.color.colorActivated));
        this.txt_game_mode_activated.setVisibility(View.VISIBLE);
        this.txt_notification_next.setVisibility(View.VISIBLE);
        this.txt_game_mode_disabled.setVisibility(View.INVISIBLE);
    }

    /**
     * onActivityResult @Override
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // update values if user has modified them
        switch (requestCode) {
            case REQUEST_CODE_HISTORY: {
                if (resultCode == RESULT_OK) {
                    this.mUserData.updateValues();
                    updateTotalValues();
                }
            }
            case REQUEST_CODE_SETTINGS: {
                if (resultCode == RESULT_OK) {
                    finish();
                    startActivity(getIntent());
                    this.mUserData.updateValues();
                    updateTotalValues();
                }
            }
        }
    }
}