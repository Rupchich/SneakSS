package com.example.sneakss;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText nicknameInput, usernameInput, passwordInput;
    private Button registerButton;
    private UserRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nicknameInput = findViewById(R.id.nicknameInput);
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        registerButton = findViewById(R.id.registerButton);
        repository = new UserRepository(this);

        registerButton.setOnClickListener(v -> {
            String nickname = nicknameInput.getText().toString().trim();
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (nickname.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Sva polja su obavezna", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = new User();
            user.nickname = nickname;
            user.username = username;
            user.password = password;

            repository.insert(user, success -> runOnUiThread(() -> {
                if (success) {
                    Toast.makeText(this, "Registracija uspješna", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Korisničko ime je zauzeto", Toast.LENGTH_SHORT).show();
                }
            }));
        });
    }
}
