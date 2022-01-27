package com.example.beer2beer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.beer2beer.database.AppDatabase
import com.example.beer2beer.database.entities.Ingredient
import com.example.beer2beer.database.entities.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedViewModel(application: Application) : AndroidViewModel(application) {
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
}