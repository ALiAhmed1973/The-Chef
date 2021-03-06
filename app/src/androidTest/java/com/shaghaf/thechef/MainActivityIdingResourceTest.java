package com.shaghaf.thechef;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityIdingResourceTest {

    private static final String RECIPE_NAME = "Nutella Pie";
    private static final String STEP_DESCRIPTION ="Recipe Introduction";
    private static final String NEXT_STEP_DESCRIPTION ="1. Preheat the oven to 350°F. Butter a 9\" deep dish pie pan.";
    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource idlingResource;

    @Before
    public void registerIdlingResource() {
        idlingResource = activityActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }

    @Test
    public void idlingResourceTest() {
        onView(ViewMatchers.withId(R.id.recyclerview_main)).perform(RecyclerViewActions.actionOnItemAtPosition(0,
                click()));

        onView(withText(RECIPE_NAME)).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.recyclerview_recipe_step_description)).perform(RecyclerViewActions.actionOnItemAtPosition(0,
                click()));
        onView(withId(R.id.tv_description)).check(matches(withText(STEP_DESCRIPTION)));

        onView(withId(R.id.bt_next)).perform(click());

        onView(withId(R.id.tv_description)).check(matches(withText(NEXT_STEP_DESCRIPTION)));


    }


    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null) {
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }
}
