package com.example.beer2beer

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
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.beer2beer.fragments.WelcomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WelcomeNavigationTest {

    @Test
    fun welcomeNavigationTest() {

        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        val scenario = launchFragmentInContainer<WelcomeFragment>()

        scenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        //val scenario = launchFragmentInContainer {
        //    TitleScreen().also { fragment ->
        //        fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
        //            if (viewLifecycleOwner != null) {
        //                // The fragment’s view has just been created
        //                navController.setGraph(R.navigation.nav_graph)
        //                Navigation.setViewNavController(fragment.requireView(), navController)
        //            }
        //        }
        //    }
        //}

        onView(withId(R.id.getStartedButton)).perform(click())
        assert(navController.currentDestination?.id == R.id.getStartedFragment)
    }

    class TitleScreen : Fragment(R.layout.fragment_welcome) {
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            val navController = view.findNavController()
            view.findViewById<BottomNavigationView>(R.id.bottomNavigationView).setupWithNavController(navController)
        }
    }
}