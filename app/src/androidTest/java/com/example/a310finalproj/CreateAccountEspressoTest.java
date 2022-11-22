package com.example.a310finalproj;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CreateAccountEspressoTest {

    @Rule
    public ActivityScenarioRule<CreateAccountPage> activityRule =
            new ActivityScenarioRule<>(CreateAccountPage.class);

    @Test
    public void createAccountEmptyEmailTest() {
        onView(withId(R.id.createAccountEmail))
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.createAccountName))
                .perform(typeText("name"), closeSoftKeyboard());
        onView(withId(R.id.createAccountPassword))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.createAccountConfirmPassword))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.createAccountButton)).perform(click());

        onView(withId(R.id.createAccountError)).check(matches(withText("Missing required field: email")));
    }

    @Test
    public void createAccountEmptyNameTest() {
        onView(withId(R.id.createAccountEmail))
                .perform(typeText("email"), closeSoftKeyboard());
        onView(withId(R.id.createAccountName))
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.createAccountPassword))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.createAccountConfirmPassword))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.createAccountButton)).perform(click());

        onView(withId(R.id.createAccountError)).check(matches(withText("Missing required field: name")));
    }

    @Test
    public void createAccountEmptyPasswordTest() {
        onView(withId(R.id.createAccountEmail))
                .perform(typeText("email"), closeSoftKeyboard());
        onView(withId(R.id.createAccountName))
                .perform(typeText("name"), closeSoftKeyboard());
        onView(withId(R.id.createAccountPassword))
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.createAccountConfirmPassword))
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.createAccountButton)).perform(click());

        onView(withId(R.id.createAccountError)).check(matches(withText("Missing required field: password")));
    }

    @Test
    public void createAccountPasswordsMatchTest() {
        onView(withId(R.id.createAccountEmail))
                .perform(typeText("email"), closeSoftKeyboard());
        onView(withId(R.id.createAccountName))
                .perform(typeText("name"), closeSoftKeyboard());
        onView(withId(R.id.createAccountPassword))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.createAccountConfirmPassword))
                .perform(typeText("pwd"), closeSoftKeyboard());
        onView(withId(R.id.createAccountButton)).perform(click());

        onView(withId(R.id.createAccountError)).check(matches(withText("Your password and confirm password must match")));
    }

    @Test
    public void createAccountEmptyPictureTest() {
        onView(withId(R.id.createAccountEmail))
                .perform(typeText("email"), closeSoftKeyboard());
        onView(withId(R.id.createAccountName))
                .perform(typeText("name"), closeSoftKeyboard());
        onView(withId(R.id.createAccountPassword))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.createAccountConfirmPassword))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.createAccountButton)).perform(click());

        onView(withId(R.id.createAccountError)).check(matches(withText("Missing required field: picture")));
    }
}
