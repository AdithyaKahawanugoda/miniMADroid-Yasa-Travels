package com.example.yasatravels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class RideDetailsActivity extends AppCompatActivity {

    private DatabaseReference dbRef;
    private TextView rideType, rideDriver,rideVehicleNo, rideCost, rideDescription;
    private Button contactbtn, ridePayment;
    private ImageView img;
    private EditText rideDistance;

    String Type,Driver,VehicleNo,Cost,Description,Phone,ImgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_details);

        Intent intent = getIntent();
        String textId = intent.getStringExtra(ResultsRidesActivity.EXTRA_RRESULTID);

        dbRef = FirebaseDatabase.getInstance().getReference("Ride").child(textId);
        rideType = (TextView) findViewById(R.id.rideType);
        rideDriver = (TextView) findViewById(R.id.rideName);
        rideVehicleNo = (TextView) findViewById(R.id.rideVehicleNo);
        rideCost = (TextView) findViewById(R.id.rideCost);
        rideDescription = (TextView) findViewById(R.id.rideDescription);
        contactbtn = (Button) findViewById(R.id.contactBtn);
        img = (ImageView) findViewById(R.id.rideImage);
        ridePayment = (Button) findViewById(R.id.ridePaymentBtn);
        rideDistance = (EditText)findViewById(R.id.rideDistance);

        //ridePayment = (Button) findViewById(R.id.ridePaymentBtn);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Type = snapshot.child("ridetype").getValue().toString();
                Driver = snapshot.child("name").getValue().toString();
                VehicleNo= snapshot.child("vehicleno").getValue().toString();
                Cost= snapshot.child("costperkm").getValue().toString();
                Description = snapshot.child("description").getValue().toString();
                Phone = snapshot.child("contactNo").getValue().toString();
                ImgUrl = snapshot.child("image").getValue().toString();

                if(snapshot.hasChild("costperkm")) {
                    Cost = snapshot.child("costperkm").getValue().toString();
                }else{
                    Cost = "Not Available";
                }

                rideType.setText(Type);
                rideDriver.setText(Driver);
                rideVehicleNo.setText(VehicleNo);
                rideCost.setText(Cost);
                rideDescription.setText(Description);
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
                intent.setData(Uri.parse("tel:"+Phone));
                startActivity(intent);
            }
        });

        //payment calculation
        ridePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    final int rDistance = Integer.parseInt(rideDistance.getText().toString());

                    final int TotalPrice = CalculatePrice(Integer.parseInt(Cost), rDistance);

                    new AlertDialog.Builder(RideDetailsActivity.this)
                            .setTitle("Rough Payment for the Distance")
                            .setMessage("Rs: " + TotalPrice)
                            .setNegativeButton(android.R.string.ok, null)
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                }catch (Exception e){
                    new AlertDialog.Builder(RideDetailsActivity.this)
                            .setTitle("Error")
                            .setMessage("No Data Available to Show !")
                            .setNegativeButton(android.R.string.ok, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }

            }
        });

    }

    int CalculatePrice(int Cost, int rDistance){
        return ((Cost*rDistance));
    }

}