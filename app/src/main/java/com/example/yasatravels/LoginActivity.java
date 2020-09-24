package com.example.yasatravels;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    public void loginHandler(View view) {
        Intent intent = new Intent(LoginActivity.this, SearchActivity.class);
        startActivity(intent);
    }
    public void signupHandler(View view) {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = findViewById(R.id.btnLogin);
    }
}