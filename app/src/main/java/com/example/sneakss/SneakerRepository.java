package com.example.sneakss;

import android.content.Context;
import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SneakerRepository {

    private final SneakerDao sneakerDao;
    private final ExecutorService executorService;

    public SneakerRepository(Context context) {
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "sneaker-db").build();
        sneakerDao = db.sneakerDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Sneaker sneaker) {
        executorService.execute(() -> sneakerDao.insert(sneaker));
    }

    public void update(Sneaker sneaker) {
        executorService.execute(() -> sneakerDao.update(sneaker));
    }

    public void delete(Sneaker sneaker) {
        executorService.execute(() -> sneakerDao.delete(sneaker));
    }

    public void getAll(SneakerCallback callback) {
        executorService.execute(() -> {
            List<Sneaker> sneakers = sneakerDao.getAll();
            callback.onSneakersLoaded(sneakers);
        });
    }

    public interface SneakerCallback {
        void onSneakersLoaded(List<Sneaker> sneakers);
    }
}
