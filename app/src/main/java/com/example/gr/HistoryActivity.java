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

//TODO CHANGE FRAGMENT TITLES TO CURRENT LOCALE (VALUES ARE READY)

public class HistoryActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private UserData mUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LocaleUtils.loadLocale(HistoryActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mUserData = new UserData(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // add back button and set title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.appbar_title_history);
        }


        setupViews();
    }

    private void setupViews() {
        // setup viewpager with tabLayout
        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tablayout);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new HistoryPagerAdapter(getSupportFragmentManager(), mUserData));
    }

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