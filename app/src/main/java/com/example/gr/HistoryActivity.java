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
        LocaleUtils.loadLocale(HistoryActivity.this); //TODO FIX: LANGUAGE UPDATES ONLY ON APP RESTART
        super.onStart();
        // add back button
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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