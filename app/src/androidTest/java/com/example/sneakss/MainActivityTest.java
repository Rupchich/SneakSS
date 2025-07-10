package com.example.sneakss;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Before
    public void setUp() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName("com.example.sneakss", "com.example.sneakss.MainActivity");
        ActivityScenario.launch(intent);
    }

    @Test
    public void testAddSneaker() {
        onView(withId(R.id.fabAdd)).perform(click());

        onView(withId(R.id.nameInput)).perform(replaceText("Test Sneaker"));
        onView(withId(R.id.brandInput)).perform(replaceText("Test Brand"));
        onView(withId(R.id.colorInput)).perform(replaceText("Black"));
        onView(withId(R.id.sizeInput)).perform(replaceText("44"));
        onView(withId(R.id.purposeInput)).perform(replaceText("Fashion"));
        onView(withId(R.id.priceInput)).perform(replaceText("999"));
        closeSoftKeyboard();

        onView(withId(R.id.saveButton)).perform(click());

        onView(withText("Test Sneaker")).check(matches(isDisplayed()));
    }
}
