
package com.example.yasatravels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
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

public class LocationDetailsActivity extends AppCompatActivity{

    private DatabaseReference dbRef;
    private TextView name,description;
    private EditText TxtNoOfAdults, TxtNoOfChildren;
    //private Button contactbtn;
    private Button BtnCalculatePrice;
    private ImageView img;

    String Name,Description,ImgUrl,AdultTicketPrice,ChildTicketPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);

        Intent intent = getIntent();
        String textId = intent.getStringExtra(ResultsLocationsActivity.EXTRA_LRESULTID );

        dbRef = FirebaseDatabase.getInstance().getReference("Location").child(textId);
        name = (TextView) findViewById(R.id.tvLocationName);
        description = (TextView) findViewById(R.id.tvLocationDescription);
        TxtNoOfAdults = (EditText) findViewById(R.id.tfNumberOfAdults);
        TxtNoOfChildren = (EditText) findViewById(R.id.tfNumberOfChildren);
        BtnCalculatePrice = (Button) findViewById(R.id.BtnCalculateTPrice);
        //contactbtn = (Button) findViewById(R.id.LRateSubmit);
        img = (ImageView) findViewById(R.id.ivLocationImage);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Name = snapshot.child("name").getValue().toString();
                Description = snapshot.child("description").getValue().toString();
                //ContactNo = snapshot.child("contactNo").getValue().toString();
                ImgUrl = snapshot.child("image").getValue().toString();
                if(snapshot.hasChild("ticketPriceAdult") && snapshot.hasChild("ticketPriceChild")) {
                    AdultTicketPrice = snapshot.child("ticketPriceAdult").getValue().toString();
                    ChildTicketPrice = snapshot.child("ticketPriceChild").getValue().toString();
                }else{
                    AdultTicketPrice = "Not Available";
                    ChildTicketPrice = "Not Available";
                }

                name.setText(Name);
                description.setText(Description);
                Picasso.get().load(ImgUrl).into(img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        BtnCalculatePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    final int NoOfAdults = Integer.parseInt(TxtNoOfAdults.getText().toString());
                    final int NoOfChildren = Integer.parseInt(TxtNoOfChildren.getText().toString());

                    final int TotalPrice = CalculatePrice(Integer.parseInt(AdultTicketPrice), Integer.parseInt(ChildTicketPrice), NoOfAdults, NoOfChildren);

                    new AlertDialog.Builder(LocationDetailsActivity.this)
                            .setTitle("Total Ticket Price")
                            .setMessage("Price Rs: " + TotalPrice)
                            .setNegativeButton(android.R.string.ok, null)
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                }catch (Exception e){
                    new AlertDialog.Builder(LocationDetailsActivity.this)
                            .setTitle("Error")
                            .setMessage("No Data Available to Show !")
                            .setNegativeButton(android.R.string.ok, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });

//        contactbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:"+ContactNo));
//                startActivity(intent);
//            }
//        });

    }

    private int CalculatePrice(int AdultTicketPrice,int ChildTicketPrice,int NoOfAdults,int NoOfChildren){
        return ((AdultTicketPrice*NoOfAdults) + (ChildTicketPrice*NoOfChildren));
    }

    // public void contactHandler(View view){}
}
