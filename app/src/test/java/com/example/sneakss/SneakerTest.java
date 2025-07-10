package com.example.sneakss;

import org.junit.Test;
import static org.junit.Assert.*;

public class SneakerTest {

    @Test
    public void testSneakerPriceIsPositive() {
        Sneaker sneaker = new Sneaker();
        sneaker.price = 149.99;
        assertTrue("Očekivana cijena > 0", sneaker.price > 0);
    }

    @Test
    public void testSneakerBrandIsNotNull() {
        Sneaker sneaker = new Sneaker();
        sneaker.brand = "Nike";
        assertNotNull("Brend ne smije biti null", sneaker.brand);
    }
}
