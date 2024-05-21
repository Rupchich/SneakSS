package com.example.sneakss;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewSneakers;
    private SneakerAdapter sneakerAdapter;
    private List<Sneaker> sneakerList;
    private FloatingActionButton fabAddSneaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewSneakers = findViewById(R.id.recyclerViewSneakers);
        recyclerViewSneakers.setLayoutManager(new LinearLayoutManager(this));

        fabAddSneaker = findViewById(R.id.fabAddSneaker);

        sneakerList = new ArrayList<>();
        sneakerList.add(new Sneaker("https://example.com/image1.jpg", 42, "Nike", "Air Max", 120.0));
        sneakerList.add(new Sneaker("https://example.com/image2.jpg", 43, "Adidas", "Ultra Boost", 140.0));
        sneakerList.add(new Sneaker("https://example.com/image3.jpg", 44, "Puma", "RS-X", 110.0));
        sneakerList.add(new Sneaker("https://example.com/image4.jpg", 41, "Reebok", "Classic", 100.0));
        sneakerList.add(new Sneaker("https://example.com/image5.jpg", 42.5, "New Balance", "574", 130.0));

        sneakerAdapter = new SneakerAdapter(this, sneakerList);
        recyclerViewSneakers.setAdapter(sneakerAdapter);

        fabAddSneaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddSneakerDialog();
            }
        });
    }

    private void showAddSneakerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_sneaker, null);
        builder.setView(dialogView);

        final EditText editTextImageUrl = dialogView.findViewById(R.id.editTextImageUrl);
        final EditText editTextSize = dialogView.findViewById(R.id.editTextSize);
        final EditText editTextCompany = dialogView.findViewById(R.id.editTextCompany);
        final EditText editTextModel = dialogView.findViewById(R.id.editTextModel);
        final EditText editTextPrice = dialogView.findViewById(R.id.editTextPrice);

        builder.setTitle("Dodaj novu tenisicu");
        builder.setPositiveButton("Dodaj", (dialog, which) -> {
            String imageUrl = editTextImageUrl.getText().toString();
            String sizeStr = editTextSize.getText().toString();
            String company = editTextCompany.getText().toString();
            String model = editTextModel.getText().toString();
            String priceStr = editTextPrice.getText().toString();

            if (TextUtils.isEmpty(imageUrl) || TextUtils.isEmpty(sizeStr) ||
                    TextUtils.isEmpty(company) || TextUtils.isEmpty(model) || TextUtils.isEmpty(priceStr)) {
                Toast.makeText(MainActivity.this, "Sva polja su obavezna", Toast.LENGTH_SHORT).show();
                return;
            }

            double size = Double.parseDouble(sizeStr);
            double price = Double.parseDouble(priceStr);

            Sneaker newSneaker = new Sneaker(imageUrl, size, company, model, price);
            sneakerList.add(newSneaker);
            sneakerAdapter.notifyItemInserted(sneakerList.size() - 1);
            recyclerViewSneakers.scrollToPosition(sneakerList.size() - 1);
            Toast.makeText(MainActivity.this, "Tenisica dodana", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Odustani", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}