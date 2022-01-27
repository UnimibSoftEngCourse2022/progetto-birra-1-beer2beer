package com.example.beer2beer.repository

import com.example.beer2beer.callback.Callback
import com.example.beer2beer.database.RecipeDao
import com.example.beer2beer.database.entities.Recipe
import java.sql.Timestamp

interface IRecipeRepository {

    abstract val dao: RecipeDao

    fun createRecipe(
        recipe: Recipe,
        callback: Callback
    )

    fun updateRecipe(
        recipe: Recipe,
        callback: Callback
    )

    fun deleteRecipe(
        recipe: Recipe,
        callback: Callback
    )

    fun getAllRecipes(callback: Callback)
}