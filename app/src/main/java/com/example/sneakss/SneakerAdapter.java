package com.example.sneakss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class SneakerAdapter extends RecyclerView.Adapter<SneakerAdapter.SneakerViewHolder> {

    private Context context;
    private List<Sneaker> sneakerList;

    public SneakerAdapter(Context context, List<Sneaker> sneakerList) {
        this.context = context;
        this.sneakerList = sneakerList != null ? sneakerList : new ArrayList<>();
    }

    public void addSneaker(Sneaker sneaker){
        sneakerList.add(sneaker);
        notifyItemInserted(sneakerList.size() - 1);
    }
    public List<Sneaker> getSneakers() {
        return sneakerList;
    }

    public Sneaker getSneaker(int index){
        return sneakerList.get(index);
    }

    public void removeSneaker(Sneaker sneaker) {
        int position = sneakerList.indexOf(sneaker);
        if (position >= 0) {
            sneakerList.remove(position);
            notifyItemRemoved(position);
        }
    }

    @NonNull
    @Override
    public SneakerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sneaker, parent, false);
        return new SneakerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SneakerViewHolder holder, int position) {
        Sneaker sneaker = sneakerList.get(position);
        Glide.with(context).load(sneaker.getImageUrl()).into(holder.imageViewSneaker);
        holder.textViewModel.setText(sneaker.getModel());
        holder.textViewCompany.setText(sneaker.getCompany());
        holder.textViewSize.setText("Veličina: " + sneaker.getSize());
        holder.textViewPrice.setText("Cijena: $" + sneaker.getPrice());

        holder.buttonDelete.setOnClickListener(v -> {
            sneakerList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, sneakerList.size());
        });
    }

    @Override
    public int getItemCount() {
        return sneakerList.size();
    }

    public static class SneakerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewSneaker;
        TextView textViewModel, textViewCompany, textViewSize, textViewPrice;
        Button buttonDelete;

        public SneakerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewSneaker = itemView.findViewById(R.id.imageViewSneaker);
            textViewModel = itemView.findViewById(R.id.textViewModel);
            textViewCompany = itemView.findViewById(R.id.textViewCompany);
            textViewSize = itemView.findViewById(R.id.textViewSize);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}