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
public class CreateInvitationEspressoTest {

    CountingIdlingResource idlingResource = new CountingIdlingResource("LoginFirebaseCalls");

    @Rule
    public ActivityScenarioRule<CreateInvitationPage> activityRule =
            new ActivityScenarioRule<>(CreateInvitationPage.class);

    @Before
    public void setup() {
        User testUser = new User();
        testUser.setEmail("testEmail");
        testUser.setName("testName");
        testUser.setPassword("password");
        activityRule.getScenario().onActivity(activity -> {
            activity.user = testUser;
            activity.idlingResource = idlingResource;
            IdlingRegistry.getInstance().register(idlingResource);
        });
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(idlingResource);
    }

    @Test
    public void createInvitationEmptyFieldTest() {
        onView(withId(R.id.createInvAddress))
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.createInvDistance))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.createInvBio))
                .perform(typeText("bio"), closeSoftKeyboard());
        onView(withId(R.id.createInvUniversity))
                .perform(typeText("university"), closeSoftKeyboard());
        onView(withId(R.id.createInvButton)).perform(click());

        onView(withId(R.id.createInvError)).check(matches(withText("Missing required field(s) (All fields required)")));
    }

    @Test
    public void createInvitationNumericalFieldTest() {
        onView(withId(R.id.createInvAddress))
                .perform(typeText("9584 Figueroa St"), closeSoftKeyboard());
        onView(withId(R.id.createInvDistance))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.createInvBeds))
                .perform(typeText("ten"), closeSoftKeyboard());
        onView(withId(R.id.createInvRent))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.createInvBedrooms))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.createInvBathrooms))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.createInvDeadline))
                .perform(typeText("06/01/2023"), closeSoftKeyboard());
        onView(withId(R.id.createInvPets))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.createInvDistance))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.createInvUtilities))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.createInvBio))
                .perform(typeText("bio"), closeSoftKeyboard());
        onView(withId(R.id.createInvUniversity))
                .perform(typeText("university"), closeSoftKeyboard());
        onView(withId(R.id.createInvButton)).perform(click());

        onView(withId(R.id.createInvError)).check(matches(withText("Incorrect numerical field, please try again.")));
    }

    @Test
    public void createInvitationExistingAddressTest() {
        onView(withId(R.id.createInvAddress))
                .perform(typeText("1234 First st"), closeSoftKeyboard());
        onView(withId(R.id.createInvDistance))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.createInvBeds))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.createInvRent))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.createInvBedrooms))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.createInvBathrooms))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.createInvDeadline))
                .perform(typeText("06/01/2023"), closeSoftKeyboard());
        onView(withId(R.id.createInvPets))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.createInvDistance))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.createInvUtilities))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.createInvBio))
                .perform(typeText("bio"), closeSoftKeyboard());
        onView(withId(R.id.createInvUniversity))
                .perform(typeText("university"), closeSoftKeyboard());
        onView(withId(R.id.createInvButton)).perform(click());

        onView(withId(R.id.createInvError)).check(matches(withText("Invitation for this address already exists")));
    }

    @Test
    public void createInvitationPastDeadlineTest() {
        onView(withId(R.id.createInvAddress))
                .perform(typeText("1234 CreateInvitationPastDeadlineTest St"), closeSoftKeyboard());
        onView(withId(R.id.createInvDistance))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.createInvBeds))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.createInvRent))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.createInvBedrooms))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.createInvBathrooms))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.createInvDeadline))
                .perform(typeText("06/01/2022"), closeSoftKeyboard());
        onView(withId(R.id.createInvPets))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.createInvDistance))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.createInvUtilities))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.createInvBio))
                .perform(typeText("bio"), closeSoftKeyboard());
        onView(withId(R.id.createInvUniversity))
                .perform(typeText("university"), closeSoftKeyboard());
        onView(withId(R.id.createInvButton)).perform(click());

        onView(withId(R.id.createInvError)).check(matches(withText("Invalid deadline (must be within one year from now)")));
    }
}
