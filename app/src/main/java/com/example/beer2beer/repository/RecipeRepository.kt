package com.example.beer2beer.repository

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.room.Database
import com.example.beer2beer.callback.Resource
import com.example.beer2beer.database.RecipeDao
import com.example.beer2beer.database.entities.Recipe
import com.google.android.gms.common.api.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class RecipeRepository(private val dao: RecipeDao, override val coroutineContext: CoroutineContext): CoroutineScope {
    fun createRecipe(recipe: Recipe) {
        launch {
            dao.insert(recipe)
        }
    }

    fun updateRecipe(recipe: Recipe) {
        launch {
            dao.update(recipe)
        }
    }

    fun deleteRecipe(recipe: Recipe) {
        launch {
            dao.delete(recipe)
        }
    }

    fun getAllRecipes() = performGetOperation(
        databaseQuery = { dao.getAll() }
    )

    private fun <T> performGetOperation(databaseQuery: () -> LiveData<T>): LiveData<Resource<T>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val source = databaseQuery.invoke().map { Resource.success(it) }
            emitSource(source)
        }


}