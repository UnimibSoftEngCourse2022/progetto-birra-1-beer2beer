package com.example.beer2beer

import android.app.Application
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.beer2beer.adapters.IngredientAdapter
import com.example.beer2beer.database.AppDatabase
import com.example.beer2beer.fragments.AddRecipeFragment
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AddRecipeNavigationTest {

    val RECIPE_NAME = "Test Ricetta"
    val RECIPE_DESCRIPTION = "This is a recipe"

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Test
    fun addRecipeNavigationTest() {

        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        val scenario = launchFragmentInContainer<AddRecipeFragment>()

        scenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            navController.navigate(R.id.addRecipeFragment)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.recipeNameEditText)).perform(typeText(RECIPE_NAME), closeSoftKeyboard())

        onView(withId(R.id.ingredientsRecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.ingredientsRecyclerView))
            .perform(actionOnItemAtPosition<IngredientAdapter.IngredientViewHolder>(
                1,
                clickChildViewWithId(R.id.addButton)
            ))

        onView(withId(R.id.descriptionEditText))
            .perform(typeText(RECIPE_DESCRIPTION), closeSoftKeyboard())

        onView(withId(R.id.saveRecipeButton)).perform(click())
        assert(navController.currentDestination?.id == R.id.homeFragment)

        var yetToBeTested = true
        val recipeDao = AppDatabase.getInstance(ApplicationProvider.getApplicationContext())
            .recipeDao()
        recipeDao.getAll().observeForever {
            val recipe = recipeDao.getAll().value?.stream()?.filter{ recipe ->
                recipe.name == RECIPE_NAME && recipe.description == RECIPE_DESCRIPTION
            }?.findFirst()?.orElse(null)

            if(yetToBeTested){
                assert(recipe != null)

                if (recipe != null) {
                    val ingredients = recipeDao.getRecipeIngredients(recipe.id)
                    ingredients.value?.forEach{ingredient ->
                        if (ingredient.name == "Water"){
                            assert(ingredient.ratio == 1.0)
                        } else {
                            assert(ingredient.ratio == 0.0)
                        }
                    }

                    recipeDao.delete(recipe.id)
                }

                yetToBeTested = false
            }

        }

    }

    private fun clickChildViewWithId(id: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController?, view: View) {
                val v = view.findViewById<View>(id)
                v.performClick()
            }
        }
    }
}
