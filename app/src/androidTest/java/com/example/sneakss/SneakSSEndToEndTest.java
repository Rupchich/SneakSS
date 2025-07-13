package com.example.sneakss;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SneakSSEndToEndTest {

    @Test
    public void fullAppFlow() throws InterruptedException {
        ActivityScenario.launch(LoginActivity.class);

        onView(withId(R.id.usernameInput)).perform(typeText("admin"));
        onView(isRoot()).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordInput)).perform(typeText("sneakss123"));
        onView(isRoot()).perform(closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        Thread.sleep(500);
        onView(withId(R.id.menuButton)).perform(click());
        onView(withText("Dodaj Stavku")).perform(click());

        onView(withId(R.id.nameInput)).perform(typeText("E2ETenisica"));
        onView(isRoot()).perform(closeSoftKeyboard());
        onView(withId(R.id.brandInput)).perform(typeText("TestBrand"));
        onView(isRoot()).perform(closeSoftKeyboard());
        onView(withId(R.id.colorInput)).perform(typeText("Crvena"));
        onView(isRoot()).perform(closeSoftKeyboard());
        onView(withId(R.id.sizeInput)).perform(typeText("43"));
        onView(isRoot()).perform(closeSoftKeyboard());
        onView(withId(R.id.purposeInput)).perform(typeText("TestNamjena"));
        onView(isRoot()).perform(closeSoftKeyboard());
        onView(withId(R.id.priceInput)).perform(typeText("123.45"));
        onView(isRoot()).perform(closeSoftKeyboard());
        onView(withId(R.id.saveButton)).perform(click());

        Thread.sleep(500);
        onView(withId(R.id.recyclerView)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.inputNote)).perform(replaceText("Ovo je napomena za E2E test"));
        onView(isRoot()).perform(closeSoftKeyboard());
        onView(withId(R.id.btnSaveNote)).perform(click());

        Thread.sleep(500);
        onView(withId(R.id.optionsMenu)).perform(click());
        onView(withText("Uredi")).perform(click());
        onView(withId(R.id.colorInput)).perform(replaceText("Plava"));
        onView(isRoot()).perform(closeSoftKeyboard());
        onView(withId(R.id.saveButton)).perform(click());

        Thread.sleep(500);
        onView(withId(R.id.optionsMenu)).perform(click());
        onView(withText("Izbriši")).perform(click());
        onView(withText("Da")).perform(click());

        onView(withId(R.id.filterButton)).perform(click());
        onView(withId(R.id.filterBrand)).perform(typeText("TestBrand"));
        onView(isRoot()).perform(closeSoftKeyboard());
        onView(withId(R.id.btnFilter)).perform(click());

        onView(withId(R.id.menuButton)).perform(click());
        onView(withId(R.id.navSettings)).perform(click());
        pressBack();

        onView(withId(R.id.menuButton)).perform(click());
        onView(withText("Odjava")).perform(click());
    }
}
