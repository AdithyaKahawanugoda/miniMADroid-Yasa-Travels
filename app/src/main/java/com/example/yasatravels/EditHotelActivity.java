package com.example.yasatravels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditHotelActivity extends AppCompatActivity {

    private DatabaseReference dbRef;

    private EditText name,desc,contact,breakfast,lunch,dinner,doublebed,singlebed;
    private Button updatebtn,deletebtn;

    String Name,Description,ContactNo,Breakfast,Lunch,Dinner,Singleb,Doubleb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hotel);

        Intent intent = getIntent();
        String textId = intent.getStringExtra(ManageHotelsActivity.EXTRA_TEXTID);

        dbRef = FirebaseDatabase.getInstance().getReference("Hotel").child(textId);

        name = (EditText) findViewById(R.id.tfAHname);
        desc = (EditText) findViewById(R.id.tfAHdescription);
        contact = (EditText) findViewById(R.id.tfAHcontact);

        breakfast = (EditText) findViewById(R.id.tfEHcbreakfast);
        lunch = (EditText) findViewById(R.id.tfEHclunch);
        dinner = (EditText) findViewById(R.id.tfEHcdinner);
        singlebed = (EditText) findViewById(R.id.tfEHcsingleB);
        doublebed = (EditText) findViewById(R.id.tfEHcdoubleB);

        updatebtn = (Button) findViewById(R.id.updateHbtn);
        deletebtn = (Button) findViewById(R.id.deleteHbtn);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 Name = snapshot.child("name").getValue().toString();
                 Description = snapshot.child("description").getValue().toString();
                 ContactNo = snapshot.child("contactNo").getValue().toString();

                 Breakfast = snapshot.child("breakfast").getValue().toString();
                 Lunch = snapshot.child("lunch").getValue().toString();
                 Dinner = snapshot.child("dinner").getValue().toString();
                 Singleb = snapshot.child("singleBed").getValue().toString();
                 Doubleb = snapshot.child("doubleBed").getValue().toString();

                name.setText(Name);
                desc.setText(Description);
                contact.setText(ContactNo);

                breakfast.setText(Breakfast);
                lunch.setText(Lunch);
                dinner.setText(Dinner);
                singlebed.setText(Singleb);
                doublebed.setText(Doubleb);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateHotelDetails();
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditHotelActivity.this);
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleting this entry cannot rollback after confirmation");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbRef.removeValue();
                        dialog.dismiss();
                        startActivity(new Intent(EditHotelActivity.this, ManageHotelsActivity.class));
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });
    }

    public void updateHotelDetails(){

        if(isNameChanged() || isDescriptionChanged() || isContactNoChanged() || isCBChanged() || isCLChanged() || isCDChanged() || isCSRchanged() || isCDRchanged()){
            Toast.makeText(this,"Data Updating..",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"No changes have been made..",Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isCSRchanged() {
        if(!Singleb.equals(singlebed.getText().toString())){
            dbRef.child("singleBed").setValue(singlebed.getText().toString());
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isCDRchanged() {
        if(!Doubleb.equals(doublebed.getText().toString())){
            dbRef.child("doubleBed").setValue(doublebed.getText().toString());
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isCDChanged() {
        if(!Dinner.equals(dinner.getText().toString())){
            dbRef.child("dinner").setValue(dinner.getText().toString());
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isCLChanged() {
        if(!Lunch.equals(lunch.getText().toString())){
            dbRef.child("lunch").setValue(lunch.getText().toString());
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isCBChanged(){
        if(!Breakfast.equals(breakfast.getText().toString())){
            dbRef.child("breakfast").setValue(breakfast.getText().toString());
            return true;
        }


        else {
            return false;
        }
    }

    private boolean isContactNoChanged() {
        if(!ContactNo.equals(contact.getText().toString())){
            dbRef.child("contactNo").setValue(contact.getText().toString());
            ContactNo = contact.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isDescriptionChanged() {
        if(!Description.equals(desc.getText().toString())){
            dbRef.child("description").setValue(desc.getText().toString());
            Description = desc.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isNameChanged() {
        if(!Name.equals(name.getText().toString())){
            dbRef.child("name").setValue(name.getText().toString());
            Name = name.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }




}