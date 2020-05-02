package com.example.gr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class SourcesActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sources);

        // add back button and set title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.appbar_title_sources);
        }

        setUpViews();

    }

    private void setUpViews() {
        listView = findViewById(R.id.listView);
        String[] sourcesList = getSources();
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.sources_element, sourcesList) {

        });
    }

    private String[] getSources() {
        return getResources().getStringArray(R.array.motivational_source);
    }

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
