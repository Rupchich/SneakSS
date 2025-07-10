package com.example.sneakss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SneakerDetailsActivity extends Activity {

    private SneakerRepository repository;
    private int sneakerId;
    private EditText noteInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sneaker_details);

        TextView nameText = findViewById(R.id.textName);
        TextView brandText = findViewById(R.id.textBrand);
        TextView colorText = findViewById(R.id.textColor);
        TextView sizeText = findViewById(R.id.textSize);
        TextView purposeText = findViewById(R.id.textPurpose);
        TextView priceText = findViewById(R.id.textPrice);
        noteInput = findViewById(R.id.inputNote);
        Button btnSave = findViewById(R.id.btnSaveNote);

        Intent intent = getIntent();
        if (intent != null) {
            sneakerId = intent.getIntExtra("id", -1);
            String name = intent.getStringExtra("name");
            String brand = intent.getStringExtra("brand");
            String color = intent.getStringExtra("color");
            String size = intent.getStringExtra("size");
            String purpose = intent.getStringExtra("purpose");
            double price = intent.getDoubleExtra("price", 0.0);
            String note = intent.getStringExtra("note");

            nameText.setText(name);
            brandText.setText(brand);
            colorText.setText(color);
            sizeText.setText(size);
            purposeText.setText(purpose);
            priceText.setText(String.valueOf(price));
            noteInput.setText(note);
        }

        repository = new SneakerRepository(this);

        btnSave.setOnClickListener(v -> {
            String newNote = noteInput.getText().toString();
            repository.getAll(sneakers -> {
                for (Sneaker s : sneakers) {
                    if (s.id == sneakerId) {
                        s.note = newNote;
                        repository.update(s);
                        break;
                    }
                }
                finish();
            });
        });
    }
}
