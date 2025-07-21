package com.example.sneakss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput, passwordInput;
    private Button loginButton;
    private ImageButton infoButton;
    private TextView registerLink;
    private UserRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        infoButton = findViewById(R.id.infoButton);
        registerLink = findViewById(R.id.registerLink);
        repository = new UserRepository(this);

        loginButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            repository.login(username, password, user -> runOnUiThread(() -> {
                if (user != null) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("nickname", user.nickname);
                    SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
                    prefs.edit().putString("nickname", user.nickname).apply();

                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Pogrešno korisničko ime ili lozinka", Toast.LENGTH_SHORT).show();
                }
            }));
        });

        registerLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        infoButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, InfoActivity.class);
            startActivity(intent);
        });
    }
}
