package com.example.yasatravels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class UserProfileActivity extends AppCompatActivity {

    private Button updateProfile;
    private Button deleteProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //switch to update user profile page by clicking update profile button
        updateProfile = (Button) findViewById(R.id.upProfileUpdateBtn);
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUpdateProfile();
            }
        });
    }

    public void openUpdateProfile() {
        Intent intent = new Intent(this,UpdateUserProfile.class);
        startActivity(intent);
    }
}