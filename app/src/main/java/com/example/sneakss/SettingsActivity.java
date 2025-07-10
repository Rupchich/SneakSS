package com.example.sneakss;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private RadioGroup itemColorOptions;
    private RadioGroup screenColorOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        itemColorOptions = findViewById(R.id.itemColorOptions);
        screenColorOptions = findViewById(R.id.screenColorOptions);

        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        String itemColor = prefs.getString("item_color", "white");
        String screenColor = prefs.getString("screen_color", "white");

        if (itemColor.equals("white")) ((RadioButton) findViewById(R.id.radioItemWhite)).setChecked(true);
        else if (itemColor.equals("gray")) ((RadioButton) findViewById(R.id.radioItemGray)).setChecked(true);
        else if (itemColor.equals("blue")) ((RadioButton) findViewById(R.id.radioItemBlue)).setChecked(true);

        if (screenColor.equals("white")) ((RadioButton) findViewById(R.id.radioScreenWhite)).setChecked(true);
        else if (screenColor.equals("gray")) ((RadioButton) findViewById(R.id.radioScreenGray)).setChecked(true);
        else if (screenColor.equals("beige")) ((RadioButton) findViewById(R.id.radioScreenBeige)).setChecked(true);

        itemColorOptions.setOnCheckedChangeListener((group, checkedId) -> {
            String color = "white";
            if (checkedId == R.id.radioItemGray) color = "gray";
            else if (checkedId == R.id.radioItemBlue) color = "blue";
            prefs.edit().putString("item_color", color).apply();
            Toast.makeText(this, "Boja stavki spremljena", Toast.LENGTH_SHORT).show();
        });

        screenColorOptions.setOnCheckedChangeListener((group, checkedId) -> {
            String color = "white";
            if (checkedId == R.id.radioScreenGray) color = "gray";
            else if (checkedId == R.id.radioScreenBeige) color = "beige";
            prefs.edit().putString("screen_color", color).apply();
            Toast.makeText(this, "Boja ekrana spremljena", Toast.LENGTH_SHORT).show();
        });
    }
}
