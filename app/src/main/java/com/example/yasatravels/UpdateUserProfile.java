package com.example.yasatravels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateUserProfile extends AppCompatActivity {

    private EditText updateProfile_name, updateProfile_email, updateProfile_phone, updateProfile_address, updateProfile_password, updateProfile_password2;
    private Button saveUpdate;
    private ImageView updateImage;
    //private ProgressDialog dialog;

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userReference = database.getReference().child("User");
    private DatabaseReference user;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_profile);

        auth = FirebaseAuth.getInstance();
        //dialog = new ProgressDialog(getContext());

        updateProfile_name = findViewById(R.id.profileUpdate_InputName);
        updateProfile_email = findViewById(R.id.profileUpdate_InputEmail);
        updateProfile_phone = findViewById(R.id.profileUpdate_InputPhone);
        updateProfile_address = findViewById(R.id.profileUpdate_InputAddress);
        updateProfile_password = findViewById(R.id.profileUpdate_InputPassword);
        updateProfile_password2 = findViewById(R.id.profileUpdate_InputReenterPwd);
        saveUpdate = findViewById(R.id.profileUpdateButton);

        auth =FirebaseAuth.getInstance();
        //dialog = new ProgressDialog(getContext());
        user = userReference.child(auth.getCurrentUser().getUid());
        firebaseUser = auth.getCurrentUser();

        displayCurrentUserData();

        saveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //updateUser();

                //daiog.setMessage("Updating your profile..");
                //dialog.show();

                final String name = updateProfile_name.getText().toString().trim();
                final String address = updateProfile_address.getText().toString().trim();
                final String phone = updateProfile_phone.getText().toString().trim();
                final String email = updateProfile_email.getText().toString().trim();
                final  String password = updateProfile_password.getText().toString().trim();
                final  String password2 = updateProfile_password2.getText().toString().trim();
                //final String image = updateImageURL;

                User updateUser = new User(name,address,phone,email,password);

                if(TextUtils.isEmpty(name)) {
                    updateProfile_name.setError("Name is required");
                    return;
                }
                if(TextUtils.isEmpty(address)) {
                    updateProfile_address.setError("Address is required");
                    return;
                }
                if(TextUtils.isEmpty(phone)) {
                    updateProfile_phone.setError("Phone is required");
                    return;
                }
                if(TextUtils.isEmpty(email)) {
                    updateProfile_email.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    updateProfile_password.setError("Password is Required");
                    return;
                }
                if (password.length() < 8) {
                    updateProfile_password.setError("Password must contain minimum eight characters");
                    return;
                }
                if (TextUtils.isEmpty(password2)) {
                    updateProfile_password2.setError("Please re-enter the password");
                    return;
                }
                if (!TextUtils.equals(password, password2)) {
                    updateProfile_password2.setError("Passwords not matched");
                    return;
                }

                //dialog.setMessage("Updating your profile..");
                //dialog.show();

                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            final DatabaseReference updateRef = FirebaseDatabase.getInstance().getReference().child("User");

                            updateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    userId = auth.getCurrentUser().getUid();

                                    if(snapshot.hasChild(userId))
                                    {
                                        User updatedUser = new User(name, address,phone,email,password);
                                        updatedUser.getName().toString().trim();
                                        updatedUser.getAddress().toString().trim();
                                        updatedUser.getEmail().toString().trim();
                                        updatedUser.getPhone().toString().trim();
                                        updatedUser.getPassword().toString().trim();
                                        updateRef.child(userId).setValue(updatedUser);

                                        Toast.makeText(UpdateUserProfile.this, "Profile Successfully updated !", Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(getApplicationContext(),UserProfileActivity.class));
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            //dialog.dismiss();
//                            Toast.makeText(UpdateUserProfile.this, "Profile Successfully updated !", Toast.LENGTH_SHORT).show();
//
//                            userId = auth.getCurrentUser().getUid();
//                            User updatedUser = new User(name, address,phone,email,password);
//                            updatedUser.getName().toString().trim();
//                            updatedUser.getAddress().toString().trim();
//                            updatedUser.getEmail().toString().trim();
//                            updatedUser.getPhone().toString().trim();
//                            updatedUser.getPassword().toString().trim();
//                            user.child(userId).setValue(updatedUser);
//
//                            startActivity(new Intent(getApplicationContext(),UserProfileActivity.class));

                        }
                    }
                });
            }
        });

        //return;
    }

    private void displayCurrentUserData() {
        userReference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                updateProfile_name.setText(snapshot.child("name").getValue().toString());
                updateProfile_address.setText(snapshot.child("address").getValue().toString());
                updateProfile_email.setText(snapshot.child("email").getValue().toString());
                updateProfile_phone.setText(snapshot.child("phone").getValue().toString());
                updateProfile_password.setText(snapshot.child("password").getValue().toString());
                updateProfile_password2.setText(snapshot.child("password").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // private void updateUser() {
//        //daiog.setMessage("Updating your profile..");
//        //dialog.show();
//
//        final String name = updateProfile_name.getText().toString().trim();
//        final String address = updateProfile_address.getText().toString().trim();
//        final String phone = updateProfile_phone.getText().toString().trim();
//        final String email = updateProfile_email.getText().toString().trim();
//        final  String password = updateProfile_password.getText().toString().trim();
//        final  String password2 = updateProfile_password2.getText().toString().trim();
//        //final String image = updateImageURL;
//
//        User updateUser = new User(name,address,phone,email,password);
//
//        if(TextUtils.isEmpty(name)) {
//            updateProfile_name.setError("Name is required");
//            return;
//        }
//        if(TextUtils.isEmpty(address)) {
//            updateProfile_address.setError("Address is required");
//            return;
//        }
//        if(TextUtils.isEmpty(phone)) {
//            updateProfile_phone.setError("Phone is required");
//            return;
//        }
//        if(TextUtils.isEmpty(email)) {
//            updateProfile_email.setError("Email is required");
//            return;
//        }
//        if (TextUtils.isEmpty(password)) {
//            updateProfile_password.setError("Password is Required");
//            return;
//        }
//        if (password.length() < 8) {
//            updateProfile_password.setError("Password must contain minimum eight characters");
//            return;
//        }
//        if (TextUtils.isEmpty(password2)) {
//            updateProfile_password2.setError("Please re-enter the password");
//            return;
//        }
//        if (!TextUtils.equals(password, password2)) {
//            updateProfile_password2.setError("Passwords not matched");
//            return;
//        }
//
//        //dialog.setMessage("Updating your profile..");
//        //dialog.show();
//
//        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    //dialog.dismiss();
//                    Toast.makeText(UpdateUserProfile.this, "Profile Successfully updated !", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(getApplicationContext(),UserProfileActivity.class));
//
//                    userId = auth.getCurrentUser().getUid();
//                    User updatedUser = new User(name, address,phone,email,password);
//                    userReference.child(userId).setValue(updatedUser);
//                }
//            }
//        });
    //}
}