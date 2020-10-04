package com.example.yasatravels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class AdminProfileActivity extends AppCompatActivity {


    TextView ManageGuidesBtn;
    TextView ManageLocationBtn;
    TextView ManageHotelBtn;
    TextView ManageRideBtn;
    TextView ManageUserBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        ManageGuidesBtn = findViewById(R.id.textView16);
        ManageLocationBtn = findViewById(R.id.textView17);
        ManageHotelBtn = findViewById(R.id.textView15);
        ManageRideBtn = findViewById(R.id.textView14);
        ManageUserBtn = findViewById(R.id.textView18);

        ManageGuidesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ManageGuidesActivity.class));
            }
        });

        ManageLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ManageLocationsActivity.class));
            }
        });

        ManageHotelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ManageRidesActivity.class));
            }
        });

        ManageUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ManageUsersActivity.class));
            }
        });




    }
    public void logout(View view){
        FirebaseAuth.getInstance().signOut(); //logout of the Admin
        startActivity(new Intent(getApplicationContext(),AdminLoginActivity.class));
        finish();
    }

}