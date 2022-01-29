package com.example.beer2beer.repository

import android.content.Context
import android.provider.ContactsContract
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.room.Database
import com.example.beer2beer.callback.CallBack
import com.example.beer2beer.database.AppDatabase
import com.example.beer2beer.database.RecipeDao
import com.example.beer2beer.database.entities.Recipe
import com.example.beer2beer.database.entities.RecipeHasIngredient
import com.google.android.gms.common.api.Response
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class RecipeRepository (
    private val dao: RecipeDao,
    private val coroutineContext: CoroutineScope
) {

    fun createRecipe(recipe: Recipe) : Long {
        return dao.insert(recipe)
    }

    fun insertIngredients(recipeHasIngredient: RecipeHasIngredient) {
        coroutineContext.launch(Dispatchers.IO) {
            dao.insertIngredient(recipeHasIngredient)
        }
    }

    fun updateRecipe(recipe: Recipe) {
        coroutineContext.launch(Dispatchers.IO) {
            dao.update(recipe)
        }
    }

    fun deleteRecipe(id: Int) {
        coroutineContext.launch(Dispatchers.IO) {
            dao.delete(id)
        }
    }

    fun getAllRecipes(): LiveData<List<Recipe>> =
        liveData(Dispatchers.IO) {
            emitSource(dao.getAll())
        }
}