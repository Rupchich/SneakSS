package com.example.sneakss;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_SNEAKER_REQUEST = 1;
    private static final int EDIT_SNEAKER_REQUEST = 2;

    private RecyclerView recyclerView;
    private SearchView searchView;
    private SneakerAdapter adapter;
    private SneakerRepository repository;
    private List<Sneaker> sneakerList = new ArrayList<>();
    private List<Sneaker> fullList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository = new SneakerRepository(this);
        adapter = new SneakerAdapter(sneakerList);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddSneakerActivity.class);
            startActivityForResult(intent, ADD_SNEAKER_REQUEST);
        });

        FloatingActionButton fabSettings = findViewById(R.id.fabSettings);
        fabSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterSneakers(newText);
                return true;
            }
        });

        loadSneakers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSneakers();

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
        repository.getAll(sneakers -> runOnUiThread(() -> {
            fullList.clear();
            fullList.addAll(sneakers);
            sneakerList.clear();
            sneakerList.addAll(sneakers);
            adapter.notifyDataSetChanged();
        }));
    }

    private void filterSneakers(String query) {
        sneakerList.clear();
        for (Sneaker s : fullList) {
            if (s.name.toLowerCase().contains(query.toLowerCase()) || s.brand.toLowerCase().contains(query.toLowerCase())) {
                sneakerList.add(s);
            }
        }
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
        }
    }
}
