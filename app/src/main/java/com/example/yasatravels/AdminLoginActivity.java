package com.example.yasatravels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLoginActivity extends AppCompatActivity {

    EditText aEmail,aPassword;
    Button aLoginBtn;
    TextView createBtn;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        aEmail = findViewById(R.id.ainputEmail);
        aPassword = findViewById(R.id.ainputPassword);
        progressBar = findViewById(R.id.progressBar3);
        fAuth = FirebaseAuth.getInstance();
        aLoginBtn = findViewById(R.id.adminLogin);
        //createBtn = findViewById(R.id.acreate);

        aLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = aEmail.getText().toString().trim();
                String password = aPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    aEmail.setError("Email is Required!");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    aPassword.setError("Password is Required!");
                    return;
                }

                if(password.length() < 6){
                    aPassword.setError("Password must contain more than 6 characters..");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //Authenticate the Admin
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(AdminLoginActivity.this, "Logged in  Successfully!..", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),AdminProfileActivity.class));
                        }else{

                            Toast.makeText(AdminLoginActivity.this, "Error!.." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }

        });

//        createBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(),AdminSignup.class));
//            }
//        });


    }
}