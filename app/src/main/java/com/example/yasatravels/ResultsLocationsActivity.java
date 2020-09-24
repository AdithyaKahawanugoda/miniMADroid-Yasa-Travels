package com.example.yasatravels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ResultsLocationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_locations);

        Intent intent = getIntent();
        String input = intent.getStringExtra(SearchActivity.EXTRA_TEXTDISTRICT);
    }
}