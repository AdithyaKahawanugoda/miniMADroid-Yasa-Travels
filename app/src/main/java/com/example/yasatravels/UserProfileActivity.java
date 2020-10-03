package com.example.yasatravels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {

    private TextView userName, userEmail, userPhone;
    private ImageView profilepic;
    private Button updateProfile;
    private Button deleteProfile;

    private DatabaseReference userProfileRef;
    private FirebaseAuth auth;

    private String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        auth = FirebaseAuth.getInstance();
        currentUserID = auth.getCurrentUser().getUid();
        userProfileRef = FirebaseDatabase.getInstance().getReference().child("User").child(currentUserID);

        userName = (TextView) findViewById(R.id.upUserName);
        userEmail = (TextView) findViewById(R.id.upEmail);
        userPhone = (TextView) findViewById(R.id.upPhone);
        profilepic = (ImageView) findViewById(R.id.upProfileImage);

        //switch to update user profile page by clicking update profile button
        updateProfile = (Button) findViewById(R.id.upProfileUpdateBtn);
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUpdateProfile();
            }
        });

        userProfileRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.exists()) {
                    //String myProfileImage = datasnapshot.child("Profile picture").getValue().toString();
                    String myUserName = datasnapshot.child("name").getValue().toString();
                    String myUserEmail = datasnapshot.child("email").getValue().toString();
                    String myPhone = datasnapshot.child("phone").getValue().toString();

                    //Picasso.get().load("URL").into(profilepic);

                    userName.setText(myUserName);
                    userEmail.setText(myUserEmail);
                    userPhone.setText(myPhone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void openUpdateProfile() {
        Intent intent = new Intent(this,UpdateUserProfile.class);
        startActivity(intent);
    }
}