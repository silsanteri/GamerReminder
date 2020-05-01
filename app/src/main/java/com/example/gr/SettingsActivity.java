package com.example.gr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.gr.logic.LocaleUtils;

/**
 * SettingsActivity
 *
 * @author Ruslan (@dievskiy), Santeri Silvennoinen (@silsanteri)
 * @version 1.0 04/2020
 */

//TODO MAKE SOURCES BUTTON WORK AND ADD SOURCELIST

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity.class";

    private Button btn_language_en;
    private Button btn_language_fi;
    private Button btn_language_kor;
    private Button btn_language_ru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LocaleUtils.loadLocale(SettingsActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setUpViews();

        // add back button
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

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
        View.OnClickListener listener = new LanguageListener();

        //TODO TURN NOT SELECTED LANGUAGE BUTTONS TO DIFFERENT COLOR(GRAY?)
        btn_language_en = findViewById(R.id.buttonEnglish);
        btn_language_fi = findViewById(R.id.buttonFinnish);
        btn_language_kor = findViewById(R.id.buttonKorean);
        btn_language_ru = findViewById(R.id.buttonRussian);

        btn_language_en.setOnClickListener(listener);
        btn_language_fi.setOnClickListener(listener);
        btn_language_kor.setOnClickListener(listener);
        btn_language_ru.setOnClickListener(listener);
    }

    /**
     * Listener for language selection.
     */
    private class LanguageListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            LocaleUtils.setLocale(SettingsActivity.this, detectLanguage(v.getId()));
            setResult(RESULT_OK);
            finish();
            startActivityForResult(getIntent(), MainActivity.REQUEST_CODE_SETTINGS);
        }

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