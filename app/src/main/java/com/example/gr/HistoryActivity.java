package com.example.gr;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.gr.logic.LocaleUtils;
import com.example.gr.logic.UserData;
import com.example.gr.view.HistoryPagerAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * HistoryActivity
 *
 * @author Ruslan (@dievskiy)
 * @version 1.0 04/2020
 */

public class HistoryActivity extends AppCompatActivity {

    // UI VARIABLES
    private ViewPager viewPager;
    private TabLayout tabLayout;

    // OBJECTS
    private UserData mUserData;

    /**
     * onCreate @Override
     * Sets up UI and loads user's settings from SharedPrefs and values from database.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // LOADS LOCALE FROM SHAREDPREFS
        LocaleUtils.loadLocale(HistoryActivity.this);
        super.onCreate(savedInstanceState);
        // SETS UP UI
        setContentView(R.layout.activity_history);
        // CREATES USERDATA OBJECT
        mUserData = new UserData(this);
    }

    /**
     * onStart @Override
     */
    @Override
    protected void onStart() {
        super.onStart();
        // SETS UP UI
        setUpViews();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.appbar_title_history);
        }
    }

    /**
     * Set up all views and add relevant values to them.
     */
    private void setUpViews() {
        // setup viewpager with tabLayout
        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tablayout);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new HistoryPagerAdapter(getSupportFragmentManager(), mUserData, this));
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
                // return to MainActivity if back arrow pressed
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}