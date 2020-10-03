package com.example.yasatravels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HotelDetailsActivity extends AppCompatActivity {

    private DatabaseReference dbRef;
    private TextView name,description,breakfast,lunch,dinner,singleb,doubleb,amount;
    private Button contactbtn;
    private ImageView img;
    private CheckBox chB,chL,chD,chLodgin;
    private EditText headCount;
    private ImageButton btnSum;

    int callodgin,calbreakfast,callunch,caldinner;
    String Name,Description,ContactNo,ImgUrl,Breakfast,Lunch,Dinner,Singlebed,Doublebed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_details);

        Intent intent = getIntent();
        String textId = intent.getStringExtra(ResultsHotelsActivity.EXTRA_HRESULTID);

        dbRef = FirebaseDatabase.getInstance().getReference("Hotel").child(textId);
        name = (TextView) findViewById(R.id.tvHotelName);
        description = (TextView) findViewById(R.id.tvHotelDescription);
        contactbtn = (Button) findViewById(R.id.hotelcontact);
        img = (ImageView) findViewById(R.id.ivHotelImg);

        breakfast = (TextView) findViewById(R.id.tvbreakfast);
        lunch = (TextView) findViewById(R.id.tvlunch);
        dinner = (TextView) findViewById(R.id.tvdinner);
        singleb = (TextView) findViewById(R.id.tvsingleb);
        doubleb = (TextView) findViewById(R.id.tvdoubleb);

        chB = (CheckBox) findViewById(R.id.chbreakfast);
        chL = (CheckBox) findViewById(R.id.chlunch);
        chD = (CheckBox) findViewById(R.id.chdinner);
        chLodgin = (CheckBox) findViewById(R.id.chlodgin);
        headCount = (EditText) findViewById(R.id.headcount);
        btnSum = (ImageButton) findViewById(R.id.btnsum);
        amount = (TextView) findViewById(R.id.tvSum);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Name = snapshot.child("name").getValue().toString();
                Description = snapshot.child("description").getValue().toString();
                ContactNo = snapshot.child("contactNo").getValue().toString();
                ImgUrl = snapshot.child("image").getValue().toString();

                Breakfast = snapshot.child("breakfast").getValue().toString();
                Lunch = snapshot.child("lunch").getValue().toString();
                Dinner = snapshot.child("dinner").getValue().toString();
                Singlebed = snapshot.child("singleBed").getValue().toString();
                Doublebed = snapshot.child("doubleBed").getValue().toString();

                name.setText(Name);
                description.setText(Description);
                Picasso.get().load(ImgUrl).into(img);

                breakfast.setText("LKR "+Breakfast);
                lunch.setText("LKR "+Lunch);
                dinner.setText("LKR "+Dinner);
                singleb.setText("LKR "+Singlebed);
                doubleb.setText("LKR "+Doublebed);

                btnSum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        calculateSum();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        contactbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+ContactNo));
                startActivity(intent);
            }
        });
    }

    public void contactHandler(View view){}

    public void calculateSum(){

        int singleRooms;
        int doubleRooms;
        int heads = Integer.parseInt(headCount.getText().toString());
        if(chLodgin.isChecked()){
            doubleRooms = heads/2;
            singleRooms = heads%2;
            callodgin = doubleRooms * Integer.parseInt(Doublebed) + singleRooms * Integer.parseInt(Singlebed);
            Log.i("checkCosts", String.valueOf(callodgin)+" :lodgin");
        }
        if(chB.isChecked()){
            calbreakfast = Integer.parseInt(Breakfast) * heads;
            Log.i("checkCosts", String.valueOf(calbreakfast)+" :breakfast");
        }
        if(chL.isChecked()){
            callunch = Integer.parseInt(Lunch) * heads;
            Log.i("checkCosts", String.valueOf(callunch)+" :lunch");
        }
        if(chD.isChecked()){
            caldinner = Integer.parseInt(Dinner) * heads;
            Log.i("checkCosts", String.valueOf(caldinner)+" :dinner");
        }
        float estimationVal = callodgin+calbreakfast+callunch+caldinner;
        amount.setText("LKR "+ estimationVal);

    }
}