package com.example.yasatravels;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddRideActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int GALLERY_REQUEST = 1;

    private ImageView vehicleImage;
    private EditText rideType, driver, vehicleNo, cost, description, phone;
    private Button saveRide;
    private Uri imageUri = null;
    private Spinner district;
    private String item;
    private StorageReference mystorage;
    private DatabaseReference mydatabase;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ride);

        mystorage = FirebaseStorage.getInstance().getReference();
        mydatabase = FirebaseDatabase.getInstance().getReference().child("Rides");

        rideType = (EditText) findViewById(R.id.typeInputAddRide);
        driver = (EditText) findViewById(R.id.driverAddRides);
        vehicleNo = (EditText) findViewById(R.id.vehicleNoAddRides);
        cost = (EditText) findViewById(R.id.costAddRides);
        description = (EditText) findViewById(R.id.descriptionAddRides);
        phone = (EditText) findViewById(R.id.phoneAddRides);
        vehicleImage = (ImageView) findViewById(R.id.imageAddRides);
        district = (Spinner) findViewById(R.id.spinDistrictAddRides);
        saveRide = (Button) findViewById(R.id.saveBtn);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Districts, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        district.setAdapter(adapter);
        district.setOnItemSelectedListener(this);

        progress = new ProgressDialog(this);

        vehicleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        saveRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startInsert();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            imageUri = data.getData();
            vehicleImage.setImageURI(imageUri);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        item = adapterView.getItemAtPosition(i).toString();
        //Log.i("spinnerval", item);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void startInsert() {

        progress.setMessage("Uploading...");
        progress.show();
        final String rType = rideType.getText().toString().trim();
        final String rDriver = driver.getText().toString().trim();
        final String rvehicleNo = vehicleNo.getText().toString().trim();
        final String rcost = cost.getText().toString().trim();
        final String rDescription = description.getText().toString().trim();
        final Integer rPhone = Integer.parseInt(phone.getText().toString().trim());
        final String district = item;

        if (!TextUtils.isEmpty(rType) && !TextUtils.isEmpty(rDriver) && !TextUtils.isEmpty(rvehicleNo) && !TextUtils.isEmpty(rcost) && !TextUtils.isEmpty(rDescription) && !TextUtils.isEmpty(rPhone.toString()) && imageUri != null) {

            final StorageReference filepath = mystorage.child("Rides_Images").child(System.currentTimeMillis() + "ridesImg");
            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();
                                    DatabaseReference newRide = mydatabase.push();
                                    newRide.child("Ride Type").setValue(rType);
                                    newRide.child("Driver's name").setValue(rDriver);
                                    newRide.child("Vehicle No").setValue(rvehicleNo);
                                    newRide.child("Cost per 1KM").setValue(rcost);
                                    newRide.child("Description").setValue(rDescription);
                                    newRide.child("contactNo").setValue(rPhone);
                                    newRide.child("District").setValue(district);
                                    newRide.child("Vehicle image").setValue(url);
                                }
                            });
                        }

                    });
                    progress.dismiss();
                    startActivity(new Intent(AddRideActivity.this, ManageRidesActivity.class));
                }
            });
        }

    }
}