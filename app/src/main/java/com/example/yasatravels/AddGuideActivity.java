
package com.example.yasatravels;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddGuideActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private static final int GALLERY_REQUEST = 1;

    private ImageView img;
    private EditText txtname, txtcontact, txtdescription,GuideEmail;
    private Button btnsave;
    private Uri imageUri = null;
    private Spinner spinner;
    private String item;
    private StorageReference mystorage;
    private DatabaseReference mydatabase;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guide);

        mystorage = FirebaseStorage.getInstance().getReference();
        mydatabase = FirebaseDatabase.getInstance().getReference().child("Guide");


        txtname = (EditText) findViewById(R.id.AddGuide);
        GuideEmail = (EditText) findViewById(R.id.AddGuideEmail);
        txtcontact = (EditText) findViewById(R.id.AddGuideContact);
        txtdescription = (EditText) findViewById(R.id.AddGuideDescription);
        img = (ImageView) findViewById(R.id.AddGuideImg);
        spinner = (Spinner) findViewById(R.id.AddGuideDestrict);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Districts, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        btnsave = (Button) findViewById(R.id.SaveGuideBtn);

        progress = new ProgressDialog(this);


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
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
            img.setImageURI(imageUri);
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
        final String name = txtname.getText().toString().trim();
        final String email = GuideEmail.getText().toString().trim();
        final String description = txtdescription.getText().toString().trim();
        final Integer contactNo = Integer.parseInt(txtcontact.getText().toString().trim());
        final String district = item;

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(description) && !TextUtils.isEmpty(contactNo.toString()) && imageUri != null) {

            final StorageReference filepath = mystorage.child("Guide_Images").child(System.currentTimeMillis() + "guideImg");
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
                                    DatabaseReference newGuide = mydatabase.push();
                                    newGuide.child("name").setValue(name);
                                    newGuide.child("email").setValue(email);
                                    newGuide.child("description").setValue(description);
                                    newGuide.child("contactNo").setValue(contactNo);
                                    newGuide.child("district").setValue(district);
                                    newGuide.child("image").setValue(url);
                                }
                            });
                        }


                    });

                    progress.dismiss();

                    startActivity(new Intent(AddGuideActivity.this, ManageGuidesActivity.class));
                }
            });
        }
    }


}