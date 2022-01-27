package com.example.beer2beer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.example.beer2beer.database.AppDatabase

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    // get the Database instance
    private val db = Room.databaseBuilder(application.applicationContext,
        AppDatabase::class.java,
        "BeerDatabase").build()

    // Declare all the Daos
    private val recipeDao = db.recipeDao()

    val recipes = recipeDao.getAll()
}