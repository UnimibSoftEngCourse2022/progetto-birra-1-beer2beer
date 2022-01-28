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
import com.google.android.gms.common.api.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class RecipeRepository (
    private val dao: RecipeDao,
    private val coroutineContext: CoroutineScope
) {

    fun createRecipe(recipe: Recipe) {
        coroutineContext.launch(Dispatchers.IO) {
            dao.insert(recipe)
        }
    }

    fun updateRecipe(recipe: Recipe) {
        coroutineContext.launch(Dispatchers.IO) {
            dao.update(recipe)
        }
    }

    fun deleteRecipe(recipe: Recipe) {
        coroutineContext.launch(Dispatchers.IO) {
            dao.delete(recipe)
        }
    }

    fun getAllRecipes(): LiveData<List<Recipe>> =
        liveData(Dispatchers.IO) {
            emitSource(dao.getAll())
        }
}