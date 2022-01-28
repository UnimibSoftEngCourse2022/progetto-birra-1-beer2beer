package com.example.beer2beer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.beer2beer.database.AppDatabase
import com.example.beer2beer.database.entities.Recipe
import com.example.beer2beer.database.entities.RecipeHasIngredient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    // get the Database instance
    private val db = AppDatabase.getInstance(getApplication<Application>().applicationContext)

    private val recipeDao = db.recipeDao()
    private val ingredientDao = db.ingredientDao()
    private val equipmentDao = db.equipmentDao()

    val recipes = recipeDao.getAll()
    val ingredients = ingredientDao.getAll()
    val equipment = equipmentDao.getAll()

    fun createNewRecipe(
        ingredientNames: Array<String>,
        quantities: DoubleArray,
        name: String,
        description: String
    ) {
        val recipe = Recipe(0, name, description)

        viewModelScope.launch(Dispatchers.IO) {
            val id = recipeDao.insert(recipe)
            quantities.forEachIndexed { index, quantity ->
                val rhi = RecipeHasIngredient(id.toInt(), ingredientNames[index], quantity)
                recipeDao.insertIngredient(rhi)
            }
        }
    }
    fun deleteRecipeById(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            recipeDao.delete(id)
        }
    }
}