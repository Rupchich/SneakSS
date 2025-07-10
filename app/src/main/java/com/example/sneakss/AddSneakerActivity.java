package com.example.sneakss;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddSneakerActivity extends AppCompatActivity {

    private EditText nameInput, brandInput, colorInput, sizeInput, purposeInput, priceInput;
    private Button saveButton;
    private SneakerRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sneaker);

        nameInput = findViewById(R.id.nameInput);
        brandInput = findViewById(R.id.brandInput);
        colorInput = findViewById(R.id.colorInput);
        sizeInput = findViewById(R.id.sizeInput);
        purposeInput = findViewById(R.id.purposeInput);
        priceInput = findViewById(R.id.priceInput);
        saveButton = findViewById(R.id.saveButton);

        repository = new SneakerRepository(this);

        saveButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            String brand = brandInput.getText().toString().trim();
            String color = colorInput.getText().toString().trim();
            String size = sizeInput.getText().toString().trim();
            String purpose = purposeInput.getText().toString().trim();
            String priceStr = priceInput.getText().toString().trim();

            if (!name.isEmpty() && !brand.isEmpty() && !color.isEmpty() && !size.isEmpty() && !purpose.isEmpty() && !priceStr.isEmpty()) {
                double price = Double.parseDouble(priceStr);
                Sneaker sneaker = new Sneaker();
                sneaker.imageResId = R.drawable.ic_launcher_foreground;
                sneaker.name = name;
                sneaker.brand = brand;
                sneaker.color = color;
                sneaker.size = size;
                sneaker.purpose = purpose;
                sneaker.price = price;

                repository.insert(sneaker);
                finish();
            }
        });
    }
}
