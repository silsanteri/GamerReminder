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

    public static final int REQUEST_CODE_SETTINGS = 420;
    private static final int REQUEST_CODE_HISTORY = 4020;
    private static final String TAG = "MainActivity.class";
    private static final int notificationId = 1;
    private static boolean gamemodeActive;

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

    private MotivationalTextGenerator textGenerator;
    private UserData mUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LocaleUtils.loadLocale(MainActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.textGenerator = new MotivationalTextGenerator(this);

        this.mUserData = new UserData(this);
        gamemodeActive = SharedPrefsUtils.returnGameModeStatus(this);
        setUpViews();
    }

    @Override
    protected void onPause() {
        // SAVES USERDATA TO DATABASE
        this.mUserData.addDBData();
        // SAVES gamemodeActive TO SHAREDPREFS
        SharedPrefsUtils.saveGameModeStatus(this, gamemodeActive);
        super.onPause();
    }

    /**
     * Set up all views and add relevant values to them.
     */
    private void setUpViews() {
        // todo set relevant time
        ((TextView) findViewById(R.id.txt_notification_next))
                .setText(getString(R.string.notification_next, "04", "20"));

        // set motivational text
        ((TextView) findViewById(R.id.txt_motivational)).setText(textGenerator.getRandomText());

        // set game mode texts
        txt_game_mode_activated = findViewById(R.id.txt_game_mode_activated);
        txt_game_mode_disabled = findViewById(R.id.txt_game_mode_disabled);
        txt_notification_next = findViewById(R.id.txt_notification_next);

        // set up buttons
        btn_exercise = findViewById(R.id.btn_add_exercise);
        btn_drink = findViewById(R.id.btn_add_water);
        btn_calories = findViewById(R.id.btn_add_calories);
        btn_game_mode = findViewById(R.id.btn_game_mode);
        if (gamemodeActive) {
            setGameModeUi();
        }

        // add listener
        View.OnClickListener addListener = new AddListener(this);
        btn_exercise.setOnClickListener(addListener);
        btn_drink.setOnClickListener(addListener);
        btn_calories.setOnClickListener(addListener);

        // set relevant today values
        txt_total_exer = findViewById(R.id.txt_total_exer);
        txt_total_cal = findViewById(R.id.txt_total_cal);
        txt_total_water = findViewById(R.id.txt_total_water);
        updateTotalValues();
    }

    private void updateTotalValues() {
        txt_total_exer.setText(getString(R.string.total_exercise, mUserData.getValueByType(ItemType.EXERCISE)));
        txt_total_cal.setText(getString(R.string.total_calories, mUserData.getValueByType(ItemType.FOOD)));
        txt_total_water.setText(getString(R.string.total_water, mUserData.getValueByType(ItemType.WATER)));
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
                mUserData.addWater(amount);
                break;
            case FOOD:
                mUserData.addFood(amount);
                break;
            case EXERCISE:
                mUserData.addExercise(amount);
                break;
            default:
                return;
        }
        updateTotalValues();
        Toast.makeText(MainActivity.this, R.string.toast_saved, Toast.LENGTH_SHORT).show();
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
        // start for result to handle changes after user modifications
        startActivityForResult(historyIntent, REQUEST_CODE_HISTORY);
    }

    private void startSettingsActivity() {
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        // start for result to handle changes after user modifications
        startActivityForResult(settingsIntent, REQUEST_CODE_SETTINGS);
    }


    /**
     * Toggles game mode on or off.
     * Customizes UI to express Game Mode activation.
     *
     * @param view
     */
    public void activateGameMode(View view) {
        if (this.gamemodeActive) {
            this.gamemodeActive = false;
            // SETS THE UI TO DEFAULT UI
            this.btn_game_mode.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            this.txt_game_mode_activated.setVisibility(View.INVISIBLE);
            this.txt_notification_next.setVisibility(View.INVISIBLE);
            this.txt_game_mode_disabled.setVisibility(View.VISIBLE);
        } else {
            this.gamemodeActive = true;
            setGameModeUi();

            // STARTS THE REPEATING NOTIFICATIONS
            NotificationUtils.sendNotification(
                    this,
                    this.notificationId,
                    getResources().getString(R.string.app_name),
                    getResources().getString(R.string.notification_content));
        }
    }

    /**
     * SETS THE UI TO VISUALLY EXPRESS GAME MODE ACTIVATION
     */
    private void setGameModeUi() {
        this.btn_game_mode.setBackgroundColor(getResources().getColor(R.color.colorActivated));
        this.txt_game_mode_activated.setVisibility(View.VISIBLE);
        this.txt_notification_next.setVisibility(View.VISIBLE);
        this.txt_game_mode_disabled.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // update values if user has modified them
        switch (requestCode) {
            case REQUEST_CODE_HISTORY: {
                if (resultCode == RESULT_OK) {
                    mUserData.updateValues();
                    updateTotalValues();
                }
            }
            case REQUEST_CODE_SETTINGS: {
                if (resultCode == RESULT_OK) {
                    finish();
                    startActivity(getIntent());
                    mUserData.updateValues();
                    updateTotalValues();
                }
            }
        }
    }
}