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
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.beer2beer.fragments.GetStartedFragment
import com.example.beer2beer.fragments.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ResetNavigationTest {

    @Test
    fun getStartedNavigationTest() {

        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        val scenario = launchFragmentInContainer<SettingsFragment>()

        scenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            navController.navigate(R.id.settingsFragment)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.buttonClearName)).perform(click())

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val sharedPreferences = appContext
            .getSharedPreferences("com.example.beer2beer", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")!!
        assert(username == "")
    }
}
