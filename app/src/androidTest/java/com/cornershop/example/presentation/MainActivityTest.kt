package com.cornershop.example.presentation

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.cornershop.example.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun checkGetButtonTextIsCorrect() {
        onView(withId(R.id.btn_get)).check(matches(withText("Get task")))
    }

    @Test
    fun checkInsetButtonTextIsCorrect() {
        onView(withId(R.id.btn_insert)).check(matches(withText("Insert task")))
    }

    @Test
    fun performClickOnInsert(){
        onView(withId(R.id.btn_insert)).perform(click())
        onView(withId(R.id.task_text)).check(matches(withText("Data inserted")))
    }

    @Test
    fun performClickOnGet(){
        onView(withId(R.id.btn_get)).perform(click())
        onView(withId(R.id.task_text)).check(matches(withText("[Task(name=TASK1), Task(name=TASK2)]")))
    }
}