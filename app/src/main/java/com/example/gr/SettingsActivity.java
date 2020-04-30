package com.example.gr;

import androidx.appcompat.app.AppCompatActivity;
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

public class SettingsActivity extends AppCompatActivity{
    //TODO ADD SOURCE LIST
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
        //TODO TURN NOT SELECTED LANGUAGE BUTTONS TO DIFFERENT COLOR(GRAY?)
        btn_language_en = findViewById(R.id.buttonEnglish);
        btn_language_fi = findViewById(R.id.buttonFinnish);
        btn_language_kor = findViewById(R.id.buttonKorean);
        btn_language_ru = findViewById(R.id.buttonRussian);

        //TODO change following setOnClickListeners to not be 20 lines long total :)
        btn_language_en.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LocaleUtils.setLocale(SettingsActivity.this,"en");
                finish();
                startActivity(getIntent());
            }
        });
        btn_language_fi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LocaleUtils.setLocale(SettingsActivity.this,"fi");
                finish();
                startActivity(getIntent());
            }
        });
        btn_language_kor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LocaleUtils.setLocale(SettingsActivity.this,"kor");
                finish();
                startActivity(getIntent());
            }
        });
        btn_language_ru.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LocaleUtils.setLocale(SettingsActivity.this,"ru");
                finish();
                startActivity(getIntent());
            }
        });
    }
}