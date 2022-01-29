package com.example.beer2beer

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.testing.TestNavHostController
import androidx.navigation.ui.setupWithNavController
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WelcomeSkipTest {

    val USERNAME = "Pippo"

    @Test
    fun welcomeSkipTest() {

        val sharedPreferences = InstrumentationRegistry.getInstrumentation().targetContext
            .getSharedPreferences("com.example.beer2beer", Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .putString("username", USERNAME)
            .apply()

        val scenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.homeFragment)).check(matches(isDisplayed()))

        scenario.close()
    }

    @Test
    fun welcomeNoSkipTest(){
        val sharedPreferences = InstrumentationRegistry.getInstrumentation().targetContext
            .getSharedPreferences("com.example.beer2beer", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()

        ActivityScenario.launch(MainActivity::class.java)

         onView(withId(R.id.homeFragment)).check(matches(not(isDisplayed())))


    }

}