package com.example.sneakss;

import android.content.Context;

import java.util.concurrent.Executors;

public class UserRepository {

    private final UserDao userDao;

    public UserRepository(Context context) {
        AppDatabase db = androidx.room.Room.databaseBuilder(context, AppDatabase.class, "sneaker-db")
                .fallbackToDestructiveMigration()
                .build();
        userDao = db.userDao();
    }

    public void insert(User user, Callback<Boolean> callback) {
        Executors.newSingleThreadExecutor().execute(() -> {
            User existing = userDao.getByUsername(user.username);
            if (existing != null) {
                callback.onResult(false);
            } else {
                userDao.insert(user);
                callback.onResult(true);
            }
        });
    }

    public void login(String username, String password, Callback<User> callback) {
        Executors.newSingleThreadExecutor().execute(() -> {
            User user = userDao.getByUsername(username);
            if (user != null && user.password.equals(password)) {
                callback.onResult(user);
            } else {
                callback.onResult(null);
            }
        });
    }
}
