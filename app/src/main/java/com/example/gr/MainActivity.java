package com.example.gr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.gr.logic.MotivationalTextGenerator;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SETTINGS = 1;

    private MotivationalTextGenerator textGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textGenerator = new MotivationalTextGenerator(this);
        setUpViews();
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
            case R.id.menu_settings:
                // start SettingsActivity if settings icon clicked
                startSettingsActivity();
            default:
                return super.onOptionsItemSelected(item);
        }

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


}
