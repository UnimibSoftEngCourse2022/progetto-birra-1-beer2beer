package com.example.beer2beer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.beer2beer.database.AppDatabase
import com.example.beer2beer.database.entities.Equipment
import com.example.beer2beer.database.entities.Ingredient
import com.example.beer2beer.database.entities.Recipe
import com.example.beer2beer.database.entities.RecipeIngredients
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.commons.math3.linear.OpenMapRealVector
import org.apache.commons.math3.linear.RealVector
import org.apache.commons.math3.optim.MaxIter
import org.apache.commons.math3.optim.OptimizationData
import org.apache.commons.math3.optim.linear.*
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val DEFAULT_MAX_ITER = MaxIter(100)

    // get the Database instance
    private val db = Room.databaseBuilder(
        application.applicationContext,
        AppDatabase::class.java,
        "BeerDatabase").createFromAsset("brewday.db")
        .build()

    // Declare all the Daos
    private val recipeDao = db.recipeDao()
    private val ingredientDao = db.ingredientDao()
    private val equipmentDao = db.equipmentDao()

    val recipes = recipeDao.getAll()
    val ingredients = ingredientDao.getAll()
    val equipment = equipmentDao.getAll()

    // Test DataBase
    fun testDb() {
        val recipe1 = Recipe(1, "ricetta 1", "")
        val recipe2 = Recipe(2, "ricetta 2", "")
        val recipe3 = Recipe(3, "ricetta 3", "")


        viewModelScope.launch(Dispatchers.IO) {
            recipeDao.insert(recipe1)
            recipeDao.insert(recipe2)
            recipeDao.insert(recipe3)
        }
    }

    fun whatShouldIBrewToday(): String {

        var bestRecipe: Recipe? = null
        var bestQuantity = 0.0
        val ss = SimplexSolver()
        recipes.value?.forEach{ recipe ->
            val recipeIngredients = recipeDao.getRecipeIngredients(recipe.id)
            val constraint = ArrayList<LinearConstraint>()
            val coeff = DoubleArray(recipeIngredients.size)

            for (i in 1 .. recipeIngredients.size){
                coeff[i] = recipeIngredients.get(i).ratio
                val c = DoubleArray(recipeIngredients.size)
                c.fill(0.0)
                c[i] = 1.0
                constraint.add(LinearConstraint(c, Relationship.LEQ, recipeIngredients.get(i).quantity))
            }

            val fObb = LinearObjectiveFunction(coeff, 1.0)
            val constr = LinearConstraintSet(constraint)

            val solution =
                ss.optimize(DEFAULT_MAX_ITER, fObb, constr,
                    GoalType.MAXIMIZE, NonNegativeConstraint(true)).value

            if(bestRecipe == null) {
                bestRecipe = recipe
                bestQuantity = solution

            } else {
                if (solution > bestQuantity){
                    bestRecipe = recipe
                    bestQuantity = solution
                }

            }

        }

        return bestRecipe?.name ?: ""
    }
}