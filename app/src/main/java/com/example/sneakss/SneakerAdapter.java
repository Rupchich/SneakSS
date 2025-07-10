package com.example.sneakss;

import android.content.SharedPreferences;
import android.content.Context;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SneakerAdapter extends RecyclerView.Adapter<SneakerAdapter.SneakerViewHolder> {

    private List<Sneaker> sneakerList;

    public SneakerAdapter(List<Sneaker> sneakerList) {
        this.sneakerList = sneakerList;
    }

    @Override
    public SneakerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sneaker, parent, false);
        return new SneakerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SneakerViewHolder holder, int position) {
        Sneaker sneaker = sneakerList.get(position);
        holder.image.setImageResource(sneaker.imageResId);
        holder.name.setText(sneaker.name);
        holder.brand.setText(sneaker.brand);
        holder.color.setText(sneaker.color);
        holder.size.setText("Veličina: " + sneaker.size);
        holder.purpose.setText("Namjena: " + sneaker.purpose);
        holder.price.setText("€" + sneaker.price);

        SharedPreferences prefs = holder.itemView.getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        String color = prefs.getString("item_color", "white");

        int bgColor = android.graphics.Color.WHITE;
        if (color.equals("gray")) bgColor = android.graphics.Color.LTGRAY;
        else if (color.equals("blue")) bgColor = android.graphics.Color.parseColor("#BBDEFB");

        holder.itemView.setBackgroundColor(bgColor);

        holder.options.setOnClickListener(v -> showPopupMenu(v, holder.getAdapterPosition(), sneaker, holder.itemView.getContext()));
    }

    @Override
    public int getItemCount() {
        return sneakerList.size();
    }

    public static class SneakerViewHolder extends RecyclerView.ViewHolder {
        ImageView image, options;
        TextView name, brand, color, size, purpose, price;

        public SneakerViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.sneakerImage);
            options = itemView.findViewById(R.id.optionsMenu);
            name = itemView.findViewById(R.id.sneakerName);
            brand = itemView.findViewById(R.id.sneakerBrand);
            color = itemView.findViewById(R.id.sneakerColor);
            size = itemView.findViewById(R.id.sneakerSize);
            purpose = itemView.findViewById(R.id.sneakerPurpose);
            price = itemView.findViewById(R.id.sneakerPrice);
        }
    }

    private void showPopupMenu(View view, int position, Sneaker sneaker, Context context) {
        PopupMenu popup = new PopupMenu(context, view);
        popup.inflate(R.menu.sneaker_menu);

        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_delete) {
                confirmDelete(context, sneaker);
                return true;
            } else if (item.getItemId() == R.id.menu_edit) {
                Intent intent = new Intent(context, EditSneakerActivity.class);
                intent.putExtra("sneaker_id", sneaker.id);
                intent.putExtra("name", sneaker.name);
                intent.putExtra("brand", sneaker.brand);
                intent.putExtra("color", sneaker.color);
                intent.putExtra("size", sneaker.size);
                intent.putExtra("purpose", sneaker.purpose);
                intent.putExtra("price", sneaker.price);
                intent.putExtra("imageResId", sneaker.imageResId);
                context.startActivity(intent);
                return true;
            }
            return false;
        });

        popup.show();
    }

    private void confirmDelete(Context context, Sneaker sneaker) {
        new AlertDialog.Builder(context)
                .setTitle("Potvrdi brisanje")
                .setMessage("Želiš li izbrisati ovu tenisicu?")
                .setPositiveButton("Da", (dialog, which) -> {
                    SneakerRepository repository = new SneakerRepository(context);
                    repository.delete(sneaker);
                    ((MainActivity) context).onResume();
                })
                .setNegativeButton("Ne", null)
                .show();
    }
}
