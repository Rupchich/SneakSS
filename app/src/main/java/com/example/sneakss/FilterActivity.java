package com.example.sneakss;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class FilterActivity extends Activity {
    EditText nameInput, brandInput, colorInput, sizeInput, purposeInput, priceInput;
    Button filterBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        nameInput = findViewById(R.id.filterName);
        brandInput = findViewById(R.id.filterBrand);
        colorInput = findViewById(R.id.filterColor);
        sizeInput = findViewById(R.id.filterSize);
        purposeInput = findViewById(R.id.filterPurpose);
        priceInput = findViewById(R.id.filterPrice);
        filterBtn = findViewById(R.id.btnFilter);

        filterBtn.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("name", nameInput.getText().toString());
            resultIntent.putExtra("brand", brandInput.getText().toString());
            resultIntent.putExtra("color", colorInput.getText().toString());
            resultIntent.putExtra("size", sizeInput.getText().toString());
            resultIntent.putExtra("purpose", purposeInput.getText().toString());
            resultIntent.putExtra("price", priceInput.getText().toString());
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
