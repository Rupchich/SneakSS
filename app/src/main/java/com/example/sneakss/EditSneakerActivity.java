package com.example.sneakss;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditSneakerActivity extends AppCompatActivity {

    private EditText nameInput, brandInput, colorInput, sizeInput, purposeInput, priceInput;
    private Button saveButton;
    private SneakerRepository repository;
    private Sneaker sneaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sneaker);

        nameInput = findViewById(R.id.nameInput);
        brandInput = findViewById(R.id.brandInput);
        colorInput = findViewById(R.id.colorInput);
        sizeInput = findViewById(R.id.sizeInput);
        purposeInput = findViewById(R.id.purposeInput);
        priceInput = findViewById(R.id.priceInput);
        saveButton = findViewById(R.id.saveButton);

        repository = new SneakerRepository(this);

        int id = getIntent().getIntExtra("sneaker_id", -1);
        String name = getIntent().getStringExtra("name");
        String brand = getIntent().getStringExtra("brand");
        String color = getIntent().getStringExtra("color");
        String size = getIntent().getStringExtra("size");
        String purpose = getIntent().getStringExtra("purpose");
        double price = getIntent().getDoubleExtra("price", 0.0);
        int imageResId = getIntent().getIntExtra("imageResId", R.drawable.ic_launcher_foreground);

        sneaker = new Sneaker();
        sneaker.id = id;
        sneaker.name = name;
        sneaker.brand = brand;
        sneaker.color = color;
        sneaker.size = size;
        sneaker.purpose = purpose;
        sneaker.price = price;
        sneaker.imageResId = imageResId;

        nameInput.setText(name);
        brandInput.setText(brand);
        colorInput.setText(color);
        sizeInput.setText(size);
        purposeInput.setText(purpose);
        priceInput.setText(String.valueOf(price));

        saveButton.setOnClickListener(v -> {
            sneaker.name = nameInput.getText().toString().trim();
            sneaker.brand = brandInput.getText().toString().trim();
            sneaker.color = colorInput.getText().toString().trim();
            sneaker.size = sizeInput.getText().toString().trim();
            sneaker.purpose = purposeInput.getText().toString().trim();
            sneaker.price = Double.parseDouble(priceInput.getText().toString().trim());

            repository.update(sneaker);
            finish();
        });
    }
}
