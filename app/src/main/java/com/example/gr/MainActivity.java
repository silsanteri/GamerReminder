package com.example.gr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.gr.logic.MotivationalTextGenerator;
import com.example.gr.logic.UserData;

//AUTHOR @dievskiy, @silsanteri

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SETTINGS = 420;

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
    protected void onPause(){
        super.onPause();
        mUserData.addDBData();
    }

    private void setUpViews() {
        // todo set relevant time
        ((TextView) findViewById(R.id.txt_notification_next))
                .setText(getString(R.string.notification_next, "04", "20"));

        // set motivational text
        ((TextView) findViewById(R.id.txt_motivational)).setText(textGenerator.getRandomText());
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
     * Activate game mode
     */
    public void activateGameMode(View view) {
        // todo
    }

    /**
     * Add Exercise
     */
    public void addExercise(View view) {
        // todo
    }

    /**
     * Add drink
     */
    public void addDrink(View view) {
        // todo
    }

    /**
     * Add calories
     */
    public void addCalories(View view) {
        // todo
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // todo handle changes in settings
    }
}






































