package com.example.sneakss;

import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

@RunWith(AndroidJUnit4.class)
public class LoginFlowTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginAddEditDeleteSneaker() throws InterruptedException {
        onView(withId(R.id.usernameInput)).perform(typeText("admin"), closeSoftKeyboard());
        onView(withId(R.id.passwordInput)).perform(typeText("sneakss123"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        Thread.sleep(1000);

        onView(withId(R.id.fabAdd)).perform(click());

        onView(withId(R.id.nameInput)).perform(typeText("Test Tenisica"), closeSoftKeyboard());
        onView(withId(R.id.brandInput)).perform(typeText("TestBrend"), closeSoftKeyboard());
        onView(withId(R.id.colorInput)).perform(typeText("Crna"), closeSoftKeyboard());
        onView(withId(R.id.sizeInput)).perform(typeText("44"), closeSoftKeyboard());
        onView(withId(R.id.purposeInput)).perform(typeText("Fashion"), closeSoftKeyboard());
        onView(withId(R.id.priceInput)).perform(typeText("1200"), closeSoftKeyboard());
        onView(withId(R.id.saveButton)).perform(click());

        Thread.sleep(1000);

        onView(withText("Test Tenisica")).perform(click());
        onView(withId(R.id.optionsMenu)).perform(click());
        onView(withText("Uredi")).perform(click());

        onView(withId(R.id.priceInput)).perform(clearText(), typeText("1100"), closeSoftKeyboard());
        onView(withId(R.id.saveButton)).perform(click());

        Thread.sleep(1000);

        onView(withText("Test Tenisica")).perform(click());
        onView(withId(R.id.optionsMenu)).perform(click());
        onView(withText("Izbriši")).perform(click());
        onView(withText("Da")).perform(click());

        Thread.sleep(1000);
    }
}
