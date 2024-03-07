package com.example.tutorapp395project.screen.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withTagValue
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tutorapp395project.screen.MainActivity
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginPageKtTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testLoginPageDisplayed() {
        onView(withTagValue(Matchers.equalTo("loginPage"))).check(matches(isDisplayed()))
    }

    @Test
    fun testFieldsDisplayed() {
        onView(withTagValue(Matchers.equalTo("emailField"))).check(matches(isDisplayed()))
        onView(withTagValue(Matchers.equalTo("passwordField"))).check(matches(isDisplayed()))
    }
}