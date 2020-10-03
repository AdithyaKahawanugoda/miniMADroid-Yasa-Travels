package com.example.yasatravels;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.UUID;

public class SignupActivity extends AppCompatActivity {

    private ImageView profilepic;
    public Uri imageURI;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private EditText regName, regAddress, regPhone, regEmail, regPassword, regPassword2;
    private Button signupButton;
    private TextView regToLogin;
    //private ProgressDialog dialog;
    private ProgressBar progBar;

    private FirebaseAuth auth;
    private String userId;
    FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
    DatabaseReference reference = rootNode.getReference().child("User");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        //dialog = new ProgressDialog(this);
        createUser();
    }

    private void createUser() {
        //hooks for all xml elements
        profilepic = findViewById(R.id.signupInsertPicure);
        regName = findViewById(R.id.signupInputName);
        regAddress = findViewById(R.id.signupInputAddress);
        regPhone = findViewById(R.id.signupInputPhone);
        regEmail = findViewById(R.id.signupInputEmail);
        regPassword = findViewById(R.id.signupInputPassword);
        regPassword2 = findViewById(R.id.signupInputReenterPwd);
        regToLogin = findViewById(R.id.signup_loginLink);
        progBar = findViewById(R.id.userSignupProgressBar);

        signupButton = (Button) findViewById(R.id.signUpButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openUserHome();

                //save data in firebase on button click
                //get all the values
                final String name = regName.getText().toString().trim();
                final String address = regAddress.getText().toString().trim();
                final String phone = regPhone.getText().toString().trim();
                final String email = regEmail.getText().toString().trim();
                final String password = regPassword.getText().toString().trim();
                String conformPassword = regPassword2.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    regName.setError("Name is Required");
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    regAddress.setError("Address is Required");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    regPhone.setError("Phone number is Required");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    regEmail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    regPassword.setError("Password is Required");
                    return;
                }
                if (password.length() < 8) {
                    regPassword.setError("Password must contain minimum eight characters");
                    return;
                }
                if (TextUtils.isEmpty(conformPassword)) {
                    regPassword2.setError("Please re-enter the password");
                    return;
                }
                if (!TextUtils.equals(password, conformPassword)) {
                    regPassword2.setError("Passwords not matched");
                    return;
                }

                progBar.setVisibility(View.VISIBLE);

//                dialog.setMessage("Sending Data...");
//                dialog.show();

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //dialog.dismiss();
                            Toast.makeText(SignupActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),UserHomeActivity.class));
                            userId = auth.getCurrentUser().getUid();
                            User newUser = new User(name, address,phone,email,password);
                            reference.child(userId).setValue(newUser);
                        }else {
                            //dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Registration Fail", Toast.LENGTH_SHORT).show();
                            progBar.setVisibility(View.GONE);
                        }
                    }
                });

//                User use = new User(name, address, phone, email, password);
//                reference.child(phone).setValue(use).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
            }
        });

        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });

        regToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() !=null){
            imageURI = data.getData();
            profilepic.setImageURI(imageURI);
            uploadPicture();
        }
    }

    private void uploadPicture() {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading image..");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/" +randomKey);

        riversRef.putFile(imageURI)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Image uploaded successfully.", Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed to upload the image..", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progressPercent = (100.00 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        pd.setMessage("Percentage : " + (int) progressPercent + "%");
                    }
                });
    }

    //    public void openUserHome() {
//        Intent intent = new Intent(this, UserHome.class);
//        startActivity(intent);
//    }
}