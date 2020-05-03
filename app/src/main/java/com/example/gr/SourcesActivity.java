package com.example.gr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * SourcesActivity
 *
 * @author Ruslan (@dievskiy)
 * @version 1.0 04/2020
 */

public class SourcesActivity extends AppCompatActivity {

    // STATIC FINAL VARIABLES
    private static final String TAG = "SourcesActivity.class";

    // UI VARIABLES
    private ListView listView;

    /**
     * onCreate @Override
     * Sets up UI.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sources);

        // add back button and set title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.appbar_title_sources);
        }

        // SETS UP UI
        setUpViews();

    }

    /**
     * Set up all views and add relevant values to them.
     */
    private void setUpViews() {
        this.listView = findViewById(R.id.listView);
        String[] sourcesList = getSources();
        this.listView.setAdapter(new ArrayAdapter<String>(this, R.layout.sources_element, sourcesList) {

        });
    }

    /**
     * Gets sources.
     *
     * @return String[]
     */
    private String[] getSources() {
        return getResources().getStringArray(R.array.motivational_source);
    }

    /**
     * onOptionsItemSelected @Override
     *
     * @param item
     * @return boolean
     */
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
