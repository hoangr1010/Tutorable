package com.example.tutorapp395project.screen.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withTagValue
import androidx.test.espresso.matcher.ViewMatchers.withText
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

    /*
     * Purpose: Test if the LoginPage composable is displayed correctly
     */
    @Test
    fun testLoginPageDisplayed() {
        onView(withTagValue(Matchers.equalTo("loginPage"))).check(matches(isDisplayed()))
    }

    /*
     * Purpose: Test if the fields are displayed correctly
     */
    @Test
    fun testFieldsDisplayed() {
        onView(withTagValue(Matchers.equalTo("emailField"))).check(matches(isDisplayed()))
        onView(withTagValue(Matchers.equalTo("passwordField"))).check(matches(isDisplayed()))
    }
    /*
     * Purpose: Test if the login button is displayed correctly
     */
    @Test
    fun testLoginButtonDisplayed() {
        onView(withText("Login")).check(matches(isDisplayed()))
    }

    /*
     * Purpose: Test if the register text is displayed correctly
     */
    @Test
    fun testRegisterTextDisplayed() {
        onView(withText("Don't have an Account? Register Here!")).check(matches(isDisplayed()))
    }

    /*
     * Purpose: Test if the role dropdown is displayed correctly
     */
    @Test
    fun testRoleDropdownDisplayed() {
        onView(withTagValue(Matchers.equalTo("roleField"))).check(matches(isDisplayed()))
    }

    /*
     * Purpose: Test if the email label is displayed correctly
     */
    @Test
    fun testEmailLabelDisplayed() {
        onView(withText("Email")).check(matches(isDisplayed()))
    }

    /*
     * Purpose: Test if the password label is displayed correctly
     */
    @Test
    fun testPasswordLabelDisplayed() {
        onView(withText("Password")).check(matches(isDisplayed()))
    }

    /*
     * Purpose: Test if the role options are displayed correctly
     */
    @Test
    fun testRoleOptionsDisplayed() {
        onView(withText("student")).check(matches(isDisplayed()))
        onView(withText("tutor")).check(matches(isDisplayed()))
    }

    /*
     * Purpose: Test if the logo image is displayed correctly
     */
    @Test
    fun testLogoImageDisplayed() {
        onView(withContentDescription("tutorapple-logo")).check(matches(isDisplayed()))
    }
}