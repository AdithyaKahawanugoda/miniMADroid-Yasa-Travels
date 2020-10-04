package com.example.yasatravels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class UserProfileActivity extends AppCompatActivity {

    private TextView userName, userEmail, userPhone;
    private ImageView profilepic;
    private Button updateProfile;
    private Button deleteProfile;
    private ProgressDialog dialog;

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
        updateProfile = (Button) findViewById(R.id.upProfileUpdateBtn);
        deleteProfile = (Button) findViewById(R.id.upDeleteBtn);

        //switch to update user profile page by clicking update profile button
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUpdateProfile();
            }
        });

        deleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
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

//    private void deleteUser() {
////        dialog.setMessage("Deleting your profile..");
////        dialog.show();
//
//        auth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()) {
//                    dialog.dismiss();
//                    Toast.makeText(getApplicationContext(), "Your profile has been deleted !", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(getApplicationContext(),SignupActivity.class));
//                }else {
//                    dialog.dismiss();
//                    Toast.makeText(getApplicationContext(), "Deletion failed !",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

    private void deleteUser() {


        auth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {

                    final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("User");

                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.hasChild(currentUserID))
                            {
                                userRef.child(currentUserID).removeValue();
                                Toast.makeText(getApplicationContext(), "Your profile has been deleted !", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),SignupActivity.class));
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }else {

                    Toast.makeText(getApplicationContext(), "Deletion failed !",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openUpdateProfile() {
        Intent intent = new Intent(this,UpdateUserProfile.class);
        startActivity(intent);
    }
}