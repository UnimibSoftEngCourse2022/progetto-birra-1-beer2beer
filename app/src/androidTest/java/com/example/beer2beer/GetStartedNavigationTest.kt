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
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GetStartedNavigationTest {

    val USERNAME = "Gianpiero"

    @Test
    fun getStartedNavigationTest() {

        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        val scenario = launchFragmentInContainer<GetStartedFragment>()

        scenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            navController.navigate(R.id.getStartedFragment)
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

        onView(withId(R.id.nameEditText)).perform(typeText(USERNAME), closeSoftKeyboard())
        onView(withId(R.id.continueButton)).perform(ViewActions.click())
        assert(navController.currentDestination?.id == R.id.homeFragment)

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val sharedPreferences = appContext
            .getSharedPreferences("com.example.beer2beer", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")!!
        assert(username == USERNAME)
    }

    @After
    fun checkUsername(){

    }

    class TitleScreen : Fragment(R.layout.fragment_get_started) {
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            val navController = view.findNavController()
            view.findViewById<BottomNavigationView>(R.id.bottomNavigationView).setupWithNavController(navController)
        }
    }
}