package com.example.sneakss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_SNEAKER_REQUEST = 1;
    private static final int EDIT_SNEAKER_REQUEST = 2;
    private static final int FILTER_REQUEST = 3;

    private RecyclerView recyclerView;
    private SearchView searchView;
    private ImageButton filterButton, menuButton;
    private SneakerAdapter adapter;
    private SneakerRepository repository;
    private List<Sneaker> sneakerList = new ArrayList<>();
    private List<Sneaker> fullList = new ArrayList<>();
    private boolean showingFavorites = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        filterButton = findViewById(R.id.filterButton);
        menuButton = findViewById(R.id.menuButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository = new SneakerRepository(this);
        adapter = new SneakerAdapter(sneakerList);
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterSneakersByQuery(newText);
                return true;
            }
        });

        filterButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FilterActivity.class);
            startActivityForResult(intent, FILTER_REQUEST);
        });

        menuButton.setOnClickListener(this::showMenu);

        loadSneakers();
    }

    private void showMenu(View v) {
        View sheetView = getLayoutInflater().inflate(R.layout.bottom_nav_menu, null);
        com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetDialog = new com.google.android.material.bottomsheet.BottomSheetDialog(this);
        bottomSheetDialog.setContentView(sheetView);

        sheetView.findViewById(R.id.navAdd).setOnClickListener(view -> {
            startActivityForResult(new Intent(this, AddSneakerActivity.class), ADD_SNEAKER_REQUEST);
            bottomSheetDialog.dismiss();
        });

        sheetView.findViewById(R.id.navFilter).setOnClickListener(view -> {
            startActivityForResult(new Intent(this, FilterActivity.class), FILTER_REQUEST);
            bottomSheetDialog.dismiss();
        });

        sheetView.findViewById(R.id.navSettings).setOnClickListener(view -> {
            startActivity(new Intent(this, SettingsActivity.class));
            bottomSheetDialog.dismiss();
        });

        sheetView.findViewById(R.id.navLogout).setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            bottomSheetDialog.dismiss();
        });

        TextView favoritesView = sheetView.findViewById(R.id.navFavorites);
        favoritesView.setText(showingFavorites ? "Prikaži sve" : "Prikaži favorite");

        favoritesView.setOnClickListener(view -> {
            if (showingFavorites) {
                showingFavorites = false;
                loadSneakers();
            } else {
                showingFavorites = true;
                repository.getFavorites(favorites -> runOnUiThread(() -> {
                    sneakerList.clear();
                    sneakerList.addAll(favorites);
                    adapter.notifyDataSetChanged();
                }));
            }
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!showingFavorites) loadSneakers();

        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        String screenColor = prefs.getString("screen_color", "white");

        int bgColor = android.graphics.Color.WHITE;
        if (screenColor.equals("gray")) {
            bgColor = android.graphics.Color.LTGRAY;
        } else if (screenColor.equals("beige")) {
            bgColor = android.graphics.Color.parseColor("#F5F5DC");
        }

        findViewById(R.id.mainLayout).setBackgroundColor(bgColor);
    }

    private void loadSneakers() {
        showingFavorites = false;
        repository.getAll(sneakers -> runOnUiThread(() -> {
            fullList.clear();
            fullList.addAll(sneakers);
            sneakerList.clear();
            sneakerList.addAll(sneakers);
            adapter.notifyDataSetChanged();
        }));
    }

    private void filterSneakersByQuery(String query) {
        sneakerList.clear();
        for (Sneaker s : fullList) {
            if (s.name.toLowerCase().contains(query.toLowerCase()) || s.brand.toLowerCase().contains(query.toLowerCase())) {
                sneakerList.add(s);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void filterSneakersByAttributes(String name, String brand, String color, String size, String purpose, String price) {
        List<Sneaker> filtered = new ArrayList<>();
        for (Sneaker s : fullList) {
            boolean match = true;

            if (!name.isEmpty() && !s.name.equalsIgnoreCase(name)) match = false;
            if (!brand.isEmpty() && !s.brand.equalsIgnoreCase(brand)) match = false;
            if (!color.isEmpty() && !s.color.equalsIgnoreCase(color)) match = false;
            if (!size.isEmpty() && !s.size.equals(size)) match = false;
            if (!purpose.isEmpty() && !s.purpose.equalsIgnoreCase(purpose)) match = false;
            if (!price.isEmpty()) {
                try {
                    double p = Double.parseDouble(price);
                    if (s.price != p) match = false;
                } catch (NumberFormatException e) {
                    match = false;
                }
            }

            if (match) filtered.add(s);
        }

        sneakerList.clear();
        sneakerList.addAll(filtered);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_SNEAKER_REQUEST && resultCode == RESULT_OK && data != null) {
            Sneaker newSneaker = new Sneaker();
            newSneaker.imageResId = data.getIntExtra("image", R.drawable.ic_launcher_foreground);
            newSneaker.name = data.getStringExtra("name");
            newSneaker.brand = data.getStringExtra("brand");
            newSneaker.color = data.getStringExtra("color");
            newSneaker.size = data.getStringExtra("size");
            newSneaker.purpose = data.getStringExtra("purpose");
            newSneaker.price = data.getDoubleExtra("price", 0.0);
            repository.insert(newSneaker);
            loadSneakers();
        } else if (requestCode == EDIT_SNEAKER_REQUEST && resultCode == RESULT_OK && data != null) {
            int position = data.getIntExtra("position", -1);
            if (position != -1 && position < sneakerList.size()) {
                Sneaker updated = sneakerList.get(position);
                updated.name = data.getStringExtra("name");
                updated.brand = data.getStringExtra("brand");
                updated.color = data.getStringExtra("color");
                updated.size = data.getStringExtra("size");
                updated.purpose = data.getStringExtra("purpose");
                updated.price = data.getDoubleExtra("price", 0.0);
                repository.update(updated);
                loadSneakers();
            }
        } else if (requestCode == FILTER_REQUEST && resultCode == RESULT_OK && data != null) {
            String name = data.getStringExtra("name");
            String brand = data.getStringExtra("brand");
            String color = data.getStringExtra("color");
            String size = data.getStringExtra("size");
            String purpose = data.getStringExtra("purpose");
            String price = data.getStringExtra("price");

            filterSneakersByAttributes(
                    name != null ? name : "",
                    brand != null ? brand : "",
                    color != null ? color : "",
                    size != null ? size : "",
                    purpose != null ? purpose : "",
                    price != null ? price : ""
            );
        }
    }
}
