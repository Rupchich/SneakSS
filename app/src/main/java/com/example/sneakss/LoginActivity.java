package com.example.sneakss;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput, passwordInput;
    private Button loginButton;
    private ImageButton infoButton;

    private final String correctUsername = "admin";
    private final String correctPassword = "sneakss123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        infoButton = findViewById(R.id.infoButton);

        loginButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (username.equals(correctUsername) && password.equals(correctPassword)) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Pogrešno korisničko ime ili lozinka", Toast.LENGTH_SHORT).show();
            }
        });

        infoButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, InfoActivity.class);
            startActivity(intent);
        });
    }
}
