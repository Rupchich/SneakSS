package com.example.sneakss;

import android.content.Context;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.*;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class SneakerRepositoryTest {

    private AppDatabase db;
    private SneakerDao dao;

    @Before
    public void setup() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        dao = db.sneakerDao();
    }

    @After
    public void tearDown() throws IOException {
        db.close();
    }

    @Test
    public void testInsertAndFetchSneaker() {
        Sneaker s = new Sneaker();
        s.name = "Air Max";
        s.brand = "Nike";
        s.price = 130.0;

        dao.insert(s);

        List<Sneaker> sneakers = dao.getAll();
        assertEquals(1, sneakers.size());
        assertEquals("Air Max", sneakers.get(0).name);
        assertEquals("Nike", sneakers.get(0).brand);
    }
}
