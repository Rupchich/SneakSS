package com.example.sneakss;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CompareActivity extends AppCompatActivity {

    private SneakerRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        int firstId = getIntent().getIntExtra("first_id", -1);
        int secondId = getIntent().getIntExtra("second_id", -1);

        repository = new SneakerRepository(this);

        repository.getById(firstId, firstSneaker -> {
            if (firstSneaker == null) return;
            repository.getById(secondId, secondSneaker -> {
                if (secondSneaker == null) return;
                runOnUiThread(() -> {
                    ((TextView) findViewById(R.id.nameLeft)).setText(firstSneaker.name);
                    ((TextView) findViewById(R.id.nameRight)).setText(secondSneaker.name);

                    ((TextView) findViewById(R.id.brandLeft)).setText(firstSneaker.brand);
                    ((TextView) findViewById(R.id.brandRight)).setText(secondSneaker.brand);

                    ((TextView) findViewById(R.id.colorLeft)).setText(firstSneaker.color);
                    ((TextView) findViewById(R.id.colorRight)).setText(secondSneaker.color);

                    ((TextView) findViewById(R.id.sizeLeft)).setText(firstSneaker.size);
                    ((TextView) findViewById(R.id.sizeRight)).setText(secondSneaker.size);

                    ((TextView) findViewById(R.id.purposeLeft)).setText(firstSneaker.purpose);
                    ((TextView) findViewById(R.id.purposeRight)).setText(secondSneaker.purpose);

                    ((TextView) findViewById(R.id.priceLeft)).setText(String.valueOf(firstSneaker.price));
                    ((TextView) findViewById(R.id.priceRight)).setText(String.valueOf(secondSneaker.price));

                    ((TextView) findViewById(R.id.noteLeft)).setText(firstSneaker.note);
                    ((TextView) findViewById(R.id.noteRight)).setText(secondSneaker.note);
                });
            });
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
