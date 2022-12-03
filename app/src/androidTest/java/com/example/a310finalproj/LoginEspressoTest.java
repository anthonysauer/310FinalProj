package com.example.a310finalproj;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.idling.CountingIdlingResource;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginEspressoTest {

    CountingIdlingResource idlingResource = new CountingIdlingResource("LoginFirebaseCalls");

    @Rule
    public ActivityScenarioRule<LoginPage> activityRule =
            new ActivityScenarioRule<>(LoginPage.class);

    @Before
    public void setUp() {
        activityRule.getScenario().onActivity(activity -> {
            activity.idlingResource = idlingResource;
            IdlingRegistry.getInstance().register(idlingResource);
        });
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(idlingResource);
    }

    @Test
    public void loginEmptyEmailTest() {
        onView(withId(R.id.loginEmail))
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.loginPassword))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        onView(withId(R.id.loginError)).check(matches(withText("Missing required field: email")));
    }

    @Test
    public void loginEmptyPasswordTest() {
        onView(withId(R.id.loginEmail))
                .perform(typeText("email"), closeSoftKeyboard());
        onView(withId(R.id.loginPassword))
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        onView(withId(R.id.loginError)).check(matches(withText("Missing required field: password")));
    }

    @Test
    public void loginIncorrectEmailTest() {
        onView(withId(R.id.loginEmail))
                .perform(typeText("THIS_EMAIL_DOES_NOT_EXIST"), closeSoftKeyboard());
        onView(withId(R.id.loginPassword))
                .perform(typeText("pwd"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        onView(withId(R.id.loginError)).check(matches(withText("Incorrect email or password")));
    }

    @Test
    public void loginIncorrectPasswordTest() {
        onView(withId(R.id.loginEmail))
                .perform(typeText("test@test.com"), closeSoftKeyboard());
        onView(withId(R.id.loginPassword))
                .perform(typeText("pw"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        onView(withId(R.id.loginError)).check(matches(withText("Incorrect email or password")));
    }
}
