package com.example.yasatravels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private TextView name,description,breakfast,lunch,dinner,singleb,doubleb;
    private Button contactbtn;
    private ImageView img;

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

                breakfast.setText(Breakfast);
                lunch.setText(Lunch);
                dinner.setText(Dinner);
                singleb.setText(Singlebed);
                doubleb.setText(Doublebed);

                
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
}