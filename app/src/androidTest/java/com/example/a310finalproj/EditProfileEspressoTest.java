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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EditProfileEspressoTest {

    @Rule
    public ActivityScenarioRule<EditProfilePage> activityRule =
            new ActivityScenarioRule<>(EditProfilePage.class);

    @Before
    public void setup() {
        User testUser = new User();
        testUser.setEmail("testEmail");
        testUser.setName("testName");
        testUser.setPassword("password");
        activityRule.getScenario().onActivity(activity -> activity.user = testUser);
    }

    @Test
    public void editProfileEmptyEmailTest() {
        onView(withId(R.id.editEmail))
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.editName))
                .perform(typeText("name"), closeSoftKeyboard());
        onView(withId(R.id.oldPassword))
                .perform(typeText("oldPassword"), closeSoftKeyboard());
        onView(withId(R.id.editPassword))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.editConfirmPassword))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.editProfileButton)).perform(click());

        onView(withId(R.id.editError)).check(matches(withText("Missing required field: email")));
    }

    @Test
    public void editProfileEmptyNameTest() {
        onView(withId(R.id.editEmail))
                .perform(typeText("email"), closeSoftKeyboard());
        onView(withId(R.id.editName))
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.oldPassword))
                .perform(typeText("oldPassword"), closeSoftKeyboard());
        onView(withId(R.id.editPassword))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.editConfirmPassword))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.editProfileButton)).perform(click());

        onView(withId(R.id.editError)).check(matches(withText("Missing required field: name")));
    }

    @Test
    public void editProfileEmptyOldPasswordTest() {
        onView(withId(R.id.editEmail))
                .perform(typeText("email"), closeSoftKeyboard());
        onView(withId(R.id.editName))
                .perform(typeText("name"), closeSoftKeyboard());
        onView(withId(R.id.oldPassword))
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.editPassword))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.editConfirmPassword))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.editProfileButton)).perform(click());

        onView(withId(R.id.editError)).check(matches(withText("Missing required field: old password")));
    }

    @Test
    public void editProfileEmptyPasswordTest() {
        onView(withId(R.id.editEmail))
                .perform(typeText("email"), closeSoftKeyboard());
        onView(withId(R.id.editName))
                .perform(typeText("name"), closeSoftKeyboard());
        onView(withId(R.id.oldPassword))
                .perform(typeText("oldPassword"), closeSoftKeyboard());
        onView(withId(R.id.editPassword))
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.editConfirmPassword))
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.editProfileButton)).perform(click());

        onView(withId(R.id.editError)).check(matches(withText("Missing required field: password")));
    }

    @Test
    public void editProfilePasswordsMatchTest() {
        onView(withId(R.id.editEmail))
                .perform(typeText("email"), closeSoftKeyboard());
        onView(withId(R.id.editName))
                .perform(typeText("name"), closeSoftKeyboard());
        onView(withId(R.id.oldPassword))
                .perform(typeText("oldPassword"), closeSoftKeyboard());
        onView(withId(R.id.editPassword))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.editConfirmPassword))
                .perform(typeText("pwd"), closeSoftKeyboard());
        onView(withId(R.id.editProfileButton)).perform(click());

        onView(withId(R.id.editError)).check(matches(withText("Your password and confirm password must match")));
    }

    @Test
    public void editProfileIncorrectOldPasswordTest() {
        onView(withId(R.id.editEmail))
                .perform(typeText("email"), closeSoftKeyboard());
        onView(withId(R.id.editName))
                .perform(typeText("name"), closeSoftKeyboard());
        onView(withId(R.id.oldPassword))
                .perform(typeText("incorrectPassword"), closeSoftKeyboard());
        onView(withId(R.id.editPassword))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.editConfirmPassword))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.editProfileButton)).perform(click());

        onView(withId(R.id.editError)).check(matches(withText("Old password is incorrect")));
    }
}
