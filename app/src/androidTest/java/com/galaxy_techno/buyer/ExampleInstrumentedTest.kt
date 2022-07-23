package com.galaxy_techno.buyer

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.galaxy_techno.buyer.presentation.single_activity.MainActivity
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.view.View
import androidx.test.espresso.UiController

import androidx.test.espresso.matcher.ViewMatchers

import androidx.test.espresso.ViewAction
import com.google.android.material.internal.NavigationMenuItemView
import com.google.android.material.internal.NavigationMenuView
import org.hamcrest.Matcher


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule()
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.galaxy_techno.buyer", appContext.packageName)
    }

    @Test
    fun registerTest() {
        /**Enter phone number, get OTP and click verify*/
        onView(withId(R.id.dest_top_profile)).perform(click());
        onView(withText("Login"))
            .perform(click());
        onView(withId(R.id.et_phone)).perform(typeText("797465871"))
        onView(withId(R.id.btn_register)).perform(click())
    //    onView(withId(R.id.btn_register_get_code)).perform(click())
        Thread.sleep(4000L)
//        onView(withId(R.id.et_otp_phone)).perform(typeText("797456854"))
//        onView(withId(R.id.btn_otp_code)).perform(click())
        Thread.sleep(5000L)
        pressBack()
        onView(withId(R.id.btn_register_otp_verify)).perform(click())
        Thread.sleep(2000L)
        /**Enter register form and click register*/
        onView(withId(R.id.et_register_name)).perform(typeText("U San Dar Naw"))
        Thread.sleep(1000L)
        onView(withId(R.id.et_register_email)).perform(typeText("sandar@gmail.com"))
        pressBack()
        Thread.sleep(1000L)
        onView(withId(R.id.et_register_password)).perform(typeText("123456789"))
        pressBack()
        onView(withId(R.id.et_register_confirm_password)).perform(typeText("123456789"))
        pressBack()
        onView(withId(R.id.rb_register_male)).perform(click())
        onView(withId(R.id.cb_terms)).perform(click())
        Thread.sleep(2000L)
        onView(withId(R.id.btn_confirm_register))
            .perform(myClick())  /** Custom click event*/
        Thread.sleep(50000L)
        /**Skip in favourite category*/
//        onView(withId(R.id.btn_skip)).perform(click())
        /**Click Country button and get country list*/
//        onView(withId(R.id.btn_country)).perform(click())
//        Thread.sleep(50000L)
    }


}
private fun myClick(): ViewAction? {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return isEnabled() // no constraints, they are checked above
        }

        override fun getDescription(): String {
            return "null"
        }

        override fun perform(uiController: UiController?, view: View) {
            view.performClick()
        }
    }
}