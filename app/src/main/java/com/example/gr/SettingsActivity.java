package com.example.gr;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gr.logic.LocaleUtils;
import com.example.gr.logic.SharedPrefsUtils;
import com.example.gr.logic.UserData;
import com.google.android.material.slider.Slider;

/**
 * SettingsActivity
 *
 * @author Ruslan (@dievskiy), Santeri Silvennoinen (@silsanteri), Tapi (@DXGGX)
 * @version 1.0 04/2020
 */

public class SettingsActivity extends AppCompatActivity {

    // STATIC FINAL VARIABLES
    private static final String TAG = "SettingsActivity.class";

    // UI VARIABLES
    private Button btn_language_en;
    private Button btn_language_fi;
    private Button btn_language_kor;
    private Button btn_language_ru;
    private Button btn_delete_userdata;

    // OBJECTS
    private UserData mUserData;

    /**
     * onCreate @Override
     * Sets up UI and loads users settings from SharedPrefs and values from database.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        // LOADS LOCALE FROM SHAREDPREFS
        LocaleUtils.loadLocale(SettingsActivity.this);
        // SUPER onCreate
        super.onCreate(savedInstanceState);
        // SETS UP UI
        setContentView(R.layout.activity_settings);
        setUpViews();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.appbar_title_settings);
        }
        // CREATES USERDATA OBJECT
        this.mUserData = new UserData(this);
    }

    /**
     * onOptionsItemSelected @Override
     *
     * @param item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // return to MainActivity if back button pressed
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Set up all views and add relevant values to them.
     */
    private void setUpViews() {
        // SETS UP SOURCE BUTTON
        Button btn_sources = findViewById(R.id.btn_sources);
        btn_sources.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, SourcesActivity.class));
            }
        });

        // USERDATA DELETION
        this.btn_delete_userdata = findViewById(R.id.btn_delete_userdata);
        this.btn_delete_userdata.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mUserData.deleteAllData();
                setResult(RESULT_OK);
                finish();
                startActivityForResult(getIntent(), MainActivity.REQUEST_CODE_SETTINGS);
            }
        });

        // FINDS THE LANGUAGEBUTTONS
        this.btn_language_en = findViewById(R.id.buttonEnglish);
        this.btn_language_fi = findViewById(R.id.buttonFinnish);
        this.btn_language_kor = findViewById(R.id.buttonKorean);
        this.btn_language_ru = findViewById(R.id.buttonRussian);

        // SET ONCLICKLISTENERS FOR LANGUAGE BUTTONS
        View.OnClickListener listener = new LanguageListener();
        this.btn_language_en.setOnClickListener(listener);
        this.btn_language_fi.setOnClickListener(listener);
        this.btn_language_kor.setOnClickListener(listener);
        this.btn_language_ru.setOnClickListener(listener);

        // set notification checkbox
        CheckBox checkBox = findViewById(R.id.notification_sound);
        checkBox.setEnabled(true);
        checkBox.setChecked(SharedPrefsUtils.returnNotificationSound(this));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPrefsUtils.saveNotificationSound(SettingsActivity.this, isChecked);
            }
        });

        // set notification frequency slider
        Slider slider = findViewById(R.id.slider);
        slider.setEnabled(true);
        slider.setValue((float) SharedPrefsUtils.returnNotificationFrequency(this));
        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                SharedPrefsUtils.saveNotificationFrequency(SettingsActivity.this, (int) value);
            }
        });
        // set notification settings unchangeable if game mode is active
        if (SharedPrefsUtils.returnGameModeStatus(this)) {
            checkBox.setEnabled(false);
            slider.setEnabled(false);
        }
    }

    /**
     * Listener for language selection.
     */
    private class LanguageListener implements View.OnClickListener {

        /**
         * onClick @Override
         * Sets the selected locale when clicked.
         *
         * @param v View
         */
        @Override
        public void onClick(View v) {
            LocaleUtils.setLocale(SettingsActivity.this, detectLanguage(v.getId()));
            setResult(RESULT_OK);
            finish();
            startActivityForResult(getIntent(), MainActivity.REQUEST_CODE_SETTINGS);
        }

        /**
         * Detects the selected language and returns the appropriate abbreviation as a String.
         *
         * @param id int
         * @return String language abbreviation
         */
        private String detectLanguage(int id) {
            switch (id) {
                case R.id.buttonRussian:
                    return "ru";
                case R.id.buttonKorean:
                    return "kor";
                case R.id.buttonFinnish:
                    return "fi";
                default:
                    return "en";
            }

        }
    }
}