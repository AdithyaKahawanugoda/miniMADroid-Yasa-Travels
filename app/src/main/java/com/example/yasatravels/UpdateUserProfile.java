package com.example.yasatravels;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateUserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_profile);

        //enable back button on action bar to navigate back to home page
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}