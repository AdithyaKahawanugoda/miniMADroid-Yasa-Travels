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

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText lEmail, lPassword;
    private Button login;
    private TextView signUp;
    private ProgressBar progBar;
    private FirebaseAuth auth;
    private TextView AdLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lEmail = findViewById(R.id.inputEmail);
        lPassword = findViewById(R.id.inputPassword);
        progBar = findViewById(R.id.loginProgressBar);
        login = findViewById(R.id.btnLogin);
        signUp = findViewById(R.id.create);
        AdLogin = findViewById(R.id.AdminLogin);
        auth = FirebaseAuth.getInstance();




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = lEmail.getText().toString().trim();
                final String password = lPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    lEmail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    lPassword.setError("Password is Required");
                    return;
                }
                if (password.length() < 8) {
                    lPassword.setError("Password must contain minimum eight characters");
                    return;
                }

                progBar.setVisibility(View.VISIBLE);

                //authenticate the user
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),UserHomeActivity.class));
                        }else {
                            Toast.makeText(LoginActivity.this, "Log in Fail", Toast.LENGTH_SHORT).show();
                            progBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignupActivity.class));
            }
        });

        AdLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AdminLoginActivity.class));
            }
        });



    }
}