package com.example.yasatravels;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

    private EditText name,desc,contact;
    private Button updatebtn,deletebtn;

    String Name,Description,ContactNo;

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

        updatebtn = (Button) findViewById(R.id.updateHbtn);
        deletebtn = (Button) findViewById(R.id.deleteHbtn);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 Name = snapshot.child("name").getValue().toString();
                 Description = snapshot.child("description").getValue().toString();
                 ContactNo = snapshot.child("contactNo").getValue().toString();

                name.setText(Name);
                desc.setText(Description);
                contact.setText(ContactNo);
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
                deleteHotelEntry();
            }
        });
    }

    public void updateHotelDetails(){

        if(isNameChanged() || isDescriptionChanged() || isContactNoChanged()){
            Toast.makeText(this,"Data Updating..",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"No changes have been made..",Toast.LENGTH_SHORT).show();
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

    public void deleteHotelEntry(){

        dbRef.removeValue();
        Toast.makeText(this,"Entry Deleted!",Toast.LENGTH_LONG).show();
    }




}