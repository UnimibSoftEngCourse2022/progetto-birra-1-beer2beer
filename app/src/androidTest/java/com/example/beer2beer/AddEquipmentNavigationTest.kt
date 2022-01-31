package com.example.beer2beer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.beer2beer.database.AppDatabase
import com.example.beer2beer.fragments.AddEquipmentFragment
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AddEquipmentNavigationTest {

    val EQUIPMENT_NAME = "Test Strumentazione"
    val EQUIPEMNT_CATEGORY = "Fermentatore"

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Test
    fun addEquipmentNavigationTest() {

        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())
        val scenario = launchFragmentInContainer<AddEquipmentFragment>()

        scenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            navController.navigate(R.id.addEquipmentFragment)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.equipmentNameEditText)).perform(typeText(EQUIPMENT_NAME), closeSoftKeyboard())
        onView(withId(R.id.addButton)).perform(click())
        onView(withId(R.id.saveEquipmentButton)).perform(click())

        assert(navController.currentDestination?.id == R.id.recipesFragment)

        var yetToBeTested = true
        val equipmentLD = AppDatabase.getInstance(ApplicationProvider.getApplicationContext())
            .equipmentDao().getAll()
        equipmentLD.observeForever{
            val equipment = equipmentLD.value?.stream()?.filter{ equipment ->
                equipment.name == EQUIPMENT_NAME && equipment.category == EQUIPEMNT_CATEGORY
                        && equipment.capacity == 1.0
            }?.findFirst()?.orElse(null)
            if (yetToBeTested){

                assert(equipment != null)

                if (equipment != null)
                    AppDatabase.getInstance(ApplicationProvider.getApplicationContext())
                        .equipmentDao().delete(equipment.id)

                yetToBeTested = false
            }

        }
    }

}