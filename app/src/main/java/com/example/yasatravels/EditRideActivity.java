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

import android.os.Bundle;

public class EditRideActivity extends AppCompatActivity {

    private DatabaseReference dbRef;

    private EditText editType,editDriver,editVehicleNo,editCost,editDescription,editPhone;
    private Button updatebtn,deletebtn;

    String Type,Driver,VehicleNo,Cost,Description,Phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ride);

        Intent intent = getIntent();
        String textId = intent.getStringExtra(ManageRidesActivity.EXTRA_TEXTID);

        dbRef = FirebaseDatabase.getInstance().getReference("Ride").child(textId);

        editType = (EditText) findViewById(R.id.rideEditType);
        editDriver = (EditText) findViewById(R.id.rideEditDriver);
        editVehicleNo = (EditText) findViewById(R.id.rideEditVehicleNo);
        editCost = (EditText) findViewById(R.id.rideEditCost);
        editDescription = (EditText) findViewById(R.id.rideEditDescription);
        editPhone = (EditText) findViewById(R.id.rideEditPhone);

        updatebtn = (Button) findViewById(R.id.updateHbtn);
        deletebtn = (Button) findViewById(R.id.deleteHbtn);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Type = snapshot.child("ridetype").getValue().toString();
                Driver = snapshot.child("name").getValue().toString();
                VehicleNo = snapshot.child("vehicleno").getValue().toString();
                Cost = snapshot.child("costperkm").getValue().toString();
                Description = snapshot.child("description").getValue().toString();
                Phone = snapshot.child("contactNo").getValue().toString();

                editType.setText(Type);
                editDriver.setText(Driver);
                editVehicleNo.setText(VehicleNo);
                editCost.setText(Cost);
                editDescription.setText(Description);
                editPhone.setText(Phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRideDetails();
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditRideActivity.this);
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleting this entry cannot rollback after confirmation");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbRef.removeValue();
                        dialog.dismiss();
                        startActivity(new Intent(EditRideActivity.this, ManageRidesActivity.class));
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

    public void updateRideDetails(){

        if(isTypeChanged() || isDriverChanged() || isVehicleChanged() || isCostChanged() || isDescriptionChanged() || isPhoneChanged()){
            Toast.makeText(this,"Data Updating..",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"No changes have been made..",Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isPhoneChanged() {
        if(!Phone.equals(editPhone.getText().toString())){
            dbRef.child("contactNo").setValue(editPhone.getText().toString());
            Phone = editPhone.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isDescriptionChanged() {
        if(!Description.equals(editDescription.getText().toString())){
            dbRef.child("description").setValue(editDescription.getText().toString());
            Description = editDescription.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isCostChanged() {
        if(!Cost.equals(editCost.getText().toString())){
            dbRef.child("costperkm").setValue(editCost.getText().toString());
            Cost = editCost.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isVehicleChanged() {
        if(!VehicleNo.equals(editVehicleNo.getText().toString())){
            dbRef.child("vehicleno").setValue(editVehicleNo.getText().toString());
            VehicleNo = editVehicleNo.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isDriverChanged() {
        if(!Driver.equals(editType.getText().toString())){
            dbRef.child("name").setValue(editDriver.getText().toString());
            Driver = editDriver.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isTypeChanged() {
        if(!Type.equals(editType.getText().toString())){
            dbRef.child("ridetype").setValue(editType.getText().toString());
            Type = editType.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

}