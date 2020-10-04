package com.example.yasatravels;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {

    public static final String EXTRA_TEXTDISTRICT = "searchDistrict";

    RadioGroup radioGroup;
    RadioButton rbH, rbL, rbR, rbG, selectedRB;
    ImageButton buttonSearch;
    EditText input;
    String option;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //enable back button on action bar - must ensure to define parent activity in manifest file
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonSearch = (ImageButton) findViewById(R.id.imgBtnSearch);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        input = (EditText) findViewById(R.id.tfSearch);

        rbH = findViewById(R.id.radBtn1);
        rbL = findViewById(R.id.radBtn2);
        rbR = findViewById(R.id.radBtn3);
        rbG = findViewById(R.id.radBtn4);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String districtName = input.getText().toString();
                Log.i("searchwindow", districtName);
                int radioId = radioGroup.getCheckedRadioButtonId();
                Log.i("searchwindow", String.valueOf(radioId));
                selectedRB = (RadioButton) findViewById(radioId);
                option = selectedRB.getText().toString();
                Log.i("searchwindow", "option:"+option);
                searchResultHandler(districtName, option);
            }
        });

    }

    public void searchResultHandler(String district, String option) {

        Intent intent = null;
        Log.i("searchwindow", "optionInHandler:"+option);
        if (option == rbH.getText()) {
            intent = new Intent(SearchActivity.this, ResultsHotelsActivity.class);
            Log.i("searchwindow", "HotelResults");
        }
        else if (option == rbL.getText()) {
            intent = new Intent(SearchActivity.this, ResultsLocationsActivity.class);

        }
        else if (option == rbR.getText()) {
            Log.i("searchwindow", "RidesSearching..");
            intent = new Intent(SearchActivity.this, ResultsRidesActivity.class);

        }
        else  {
            Log.i("searchwindow", "GuidesSearching..");
            intent = new Intent(SearchActivity.this, ResultsGuidesActivity.class);
        }

        intent.putExtra(EXTRA_TEXTDISTRICT, district);
        startActivity(intent);

    }
}