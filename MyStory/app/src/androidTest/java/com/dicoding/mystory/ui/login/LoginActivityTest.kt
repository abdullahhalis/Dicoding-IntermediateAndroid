package com.dicoding.mystory.ui.login

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.dicoding.mystory.R
import com.dicoding.mystory.ui.main.MainActivity
import com.dicoding.mystory.ui.welcome.WelcomeActivity
import com.dicoding.mystory.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginActivityTest {
    private val invalidEmail = "invalidEmail"
    private val invalidPassword = "pass"

    private val validEmail = "test@response.com"
    private val validPassword = "testresponse"

    @get:Rule
    val activity = ActivityScenarioRule(LoginActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        Intents.release()
    }

    @Test
    fun loginWithInvalidEmailAndPassword() {
        onView(withId(R.id.emailEditText)).perform(typeText(invalidEmail), closeSoftKeyboard())
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        onView(withId(R.id.emailEditText)).check(matches(hasErrorText(context.getString(R.string.invalid_email))))

        onView(withId(R.id.passwordEditText)).perform(typeText(invalidPassword), closeSoftKeyboard())
        onView(withId(R.id.passwordEditText)).check(matches(hasErrorText(context.getString(R.string.invalid_password))))
    }

    @Test
    fun loginWithValidEmailAndPassword() {
        onView(withId(R.id.emailEditText)).perform(typeText(validEmail), closeSoftKeyboard())
        onView(withId(R.id.passwordEditText)).perform(typeText(validPassword), closeSoftKeyboard())
        onView(withId(R.id.loginButton)).perform(click())

        intended(hasComponent(MainActivity::class.java.name))

        onView(withId(R.id.rvList)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.rvList)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(R.id.menu_logout)).perform(click())

        intended(hasComponent(WelcomeActivity::class.java.name))
    }
}