package com.example.gr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.gr.view.HistoryPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class HistoryActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        // add back button
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupViews();

    }

    private void setupViews() {
        // setup viewpager with tabLayout
        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tablayout);
        viewPager.setAdapter(new HistoryPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
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

}