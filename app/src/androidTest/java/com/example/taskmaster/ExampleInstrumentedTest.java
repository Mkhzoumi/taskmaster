package com.example.taskmaster;

import android.content.Context;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest

public class ExampleInstrumentedTest {


    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule =
            new ActivityScenarioRule<>(MainActivity.class);
    @Test
    public void testButtonsNaming() {
        onView(withText("Settings")).check(matches(isDisplayed()));
        onView(withText("All tasks")).check(matches(isDisplayed()));
        onView(withText("Add task")).check(matches(isDisplayed()));
    }

    @Test
    public void testSetting() {
        onView(withId(R.id.settingsButton)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.usernameField)).check(matches(isDisplayed())).perform(typeText("Espresso"));
        onView(withId(R.id.settingUsernameButton)).check(matches(isDisplayed())).perform(click());
        onView(withText("Espresso's Tasks")).check(matches(isDisplayed()));

    }



    @Test
    public void testAllTasks() {
        onView(withId(R.id.allTasks)).check(matches(isDisplayed())).perform(click());
        onView(withText("All Tasks")).check(matches(isDisplayed()));
        onView(withId(R.id.backFromAllTasks)).check(matches(isDisplayed())).perform(click());
    }

    @Test
    public void testAddingTask() {
        onView(withId(R.id.addTask)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.taskTitle)).check(matches(isDisplayed())).perform(typeText("Espresso task"));
        onView(withId(R.id.descreption)).check(matches(isDisplayed())).perform(typeText("Espresso test description"));
        onView(withId(R.id.stateOfTaskField)).check(matches(isDisplayed())).perform(typeText("new"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.add)).check(matches(isDisplayed())).perform(click());
        onView(withText("Espresso task")).check(matches(isDisplayed()));

    }





    // all in one function , nevermind TA this is just for me, its not for the grading.


//    @Test
//    public void testAll() throws InterruptedException {
//    onView(withId(R.id.settingsButton)).check(matches(isDisplayed())).perform(click());
//    onView(withId(R.id.usernameField)).check(matches(isDisplayed())).perform(typeText("Espresso"));
//    onView(withId(R.id.settingUsernameButton)).check(matches(isDisplayed())).perform(click());
//    onView(withText("Espresso's Tasks")).check(matches(isDisplayed()));
//
//        Thread.sleep(2000);
//
//    onView(withId(R.id.addTask)).check(matches(isDisplayed())).perform(click());
//    onView(withId(R.id.taskTitle)).check(matches(isDisplayed())).perform(typeText("Espresso task"));
//    onView(withId(R.id.descreption)).check(matches(isDisplayed())).perform(typeText("Espresso test description"));
//    onView(withId(R.id.stateOfTaskField)).check(matches(isDisplayed())).perform(typeText("new"));
//        Espresso.closeSoftKeyboard();
//    onView(withId(R.id.add)).check(matches(isDisplayed())).perform(click());
//    onView(withText("Espresso task")).check(matches(isDisplayed()));
//
//        Thread.sleep(2000);
//
//    onView(withId(R.id.allTasks)).check(matches(isDisplayed())).perform(click());
//    onView(withText("All Tasks")).check(matches(isDisplayed()));
//    onView(withId(R.id.backFromAllTasks)).check(matches(isDisplayed())).perform(click());
//
//        Thread.sleep(2000);
//
//    onView(withText("Espresso task")).check(matches(isDisplayed())).perform(click());
//        Thread.sleep(2000);
//    }
}