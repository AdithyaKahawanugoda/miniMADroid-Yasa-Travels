package com.example.yasatravels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class UserHomeActivity extends AppCompatActivity {

    private ImageButton profileButton;
    private ImageButton aboutusButton;
    private ImageButton CurrancyConvertorBtn;
    private Button explore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        //switch to user profile by clicking profile icon
        profileButton = (ImageButton) findViewById(R.id.home_profileIcon);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserProfile();
            }
        });

        //switch to about us page by clicking about us icon
        aboutusButton = (ImageButton) findViewById(R.id.homeAboutusIcon);
        aboutusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAboutUs();
            }
        });

        //switch to currancy convertor page by clicking  currancy convertor icon
        CurrancyConvertorBtn = (ImageButton) findViewById(R.id.home_CurrencyConvertorIcon);
        CurrancyConvertorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCurrancyConvertor();
            }
        });

        //switch to search window
        explore = (Button) findViewById(R.id.homeExploreButton);
        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openSearchWindow();
            }
        });
    }

    //logout
    public  void logout (View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }

    //switch to user profile by clicking profile icon
    public void openUserProfile() {
        Intent intent = new Intent(this,UserProfileActivity.class);
        startActivity(intent);
    }

    //switch to currancy converter by clicking currancy converter icon
    public void  openCurrancyConvertor() {
        Intent intent = new Intent(this,CurrancyConvertorActivity.class);
        startActivity(intent);
    }

    //switch to about us page by clicking about us icon
    public void openAboutUs() {
        Intent intent = new Intent(this, AboutUsActivity.class);
        startActivity(intent);
    }

    //switch to the search window
    public void openSearchWindow(){
        Intent intent = new Intent(this,SearchActivity.class);
        startActivity(intent);
    }
}