package com.example.beer2beer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.beer2beer.database.RecipeDao
import com.example.beer2beer.database.entities.Recipe
import com.example.beer2beer.database.entities.RecipeHasIngredient
import com.example.beer2beer.database.entities.RecipeIngredients
import com.example.beer2beer.database.entities.RecipeInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RecipeRepository(
    private val dao: RecipeDao,
    private val coroutineContext: CoroutineScope
) {

    fun createRecipe(recipe: Recipe): Long {
        return dao.insert(recipe)
    }

    fun createInstance(recipeInstance: RecipeInstance) {
        coroutineContext.launch(Dispatchers.IO) {
            dao.insertInstance(recipeInstance)
        }
    }

    fun insertIngredients(recipeHasIngredient: RecipeHasIngredient) {
        coroutineContext.launch(Dispatchers.IO) {
            dao.insertIngredient(recipeHasIngredient)
        }
    }

    fun updateRecipeInstance(recipeId: Int, newNote: String) {
        coroutineContext.launch(Dispatchers.IO) {
            dao.updateRecipeInstance(recipeId, newNote)
        }
    }

    fun deleteRecipe(id: Int) {
        coroutineContext.launch(Dispatchers.IO) {
            dao.delete(id)
        }
    }

    fun deleteAllRecipe() {
        coroutineContext.launch(Dispatchers.IO) {
            dao.deleteAll()
        }
    }

    fun getAllRecipes(): LiveData<List<Recipe>> =
        liveData(Dispatchers.IO) {
            emitSource(dao.getAll())
        }

    fun getRecipeHasIngredients(): LiveData<List<RecipeHasIngredient>> =
        liveData(Dispatchers.IO) {
            emitSource(dao.getRecipeHasIngredients())
        }

    fun getRecipeInstances(): LiveData<List<RecipeInstance>> =
        liveData(Dispatchers.IO) {
            emitSource((dao.getRecipeInstances()))
        }

    fun getRecipeIngredients(recipe: Int): LiveData<List<RecipeIngredients>> =
        liveData(Dispatchers.IO) {
            emitSource((dao.getRecipeIngredients(recipe)))
        }
}