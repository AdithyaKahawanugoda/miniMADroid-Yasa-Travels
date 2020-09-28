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
    private TextView name,description;
    private Button contactbtn;
    private ImageView img;

    String Name,Description,ContactNo,ImgUrl;

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

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Name = snapshot.child("name").getValue().toString();
                Description = snapshot.child("description").getValue().toString();
                ContactNo = snapshot.child("contactNo").getValue().toString();
                ImgUrl = snapshot.child("image").getValue().toString();

                name.setText(Name);
                description.setText(Description);
                Picasso.get().load(ImgUrl).into(img);
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