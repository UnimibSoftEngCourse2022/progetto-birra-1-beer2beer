package com.example.beer2beer.repository

import androidx.lifecycle.liveData
import com.example.beer2beer.callback.Callback
import com.example.beer2beer.database.RecipeDao
import com.example.beer2beer.database.entities.Recipe
import kotlinx.coroutines.Dispatchers


class RecipeRepository(override val dao: RecipeDao) : IRecipeRepository {

    override fun createRecipe(recipe: Recipe, callback: Callback) {
        liveData<Recipe>(Dispatchers.IO) {
            dao.insert(recipe)
            callback.onSuccess("SUCCESS")
        }
    }

    override fun updateRecipe(recipe: Recipe, callback: Callback) {
        liveData<Recipe>(Dispatchers.IO) {
            dao.update(recipe)
            callback.onSuccess("SUCCESS")
        }
    }

    override fun deleteRecipe(recipe: Recipe, callback: Callback) {
        liveData<Recipe>(Dispatchers.IO) {
            dao.delete(recipe)
            callback.onSuccess("SUCCESS")
        }
    }

    override fun getAllRecipes(callback: Callback) {
        liveData<Recipe>(Dispatchers.IO) {
            callback.onSuccess(dao.getAll().value)
        }
    }

}