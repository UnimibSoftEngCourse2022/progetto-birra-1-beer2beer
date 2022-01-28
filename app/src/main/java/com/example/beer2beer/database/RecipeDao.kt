package com.example.beer2beer.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.*
import com.example.beer2beer.database.entities.Recipe
import com.example.beer2beer.database.entities.RecipeIngredients

@Dao
interface RecipeDao {
    @Insert(onConflict = REPLACE)
    fun insert(recipe: Recipe)

    @Update
    fun update(recipe: Recipe)

    @Delete
    fun delete(recipe: Recipe)

    @Query("SELECT * FROM recipe")
    fun getAll(): LiveData<List<Recipe>>

    @Query("SELECT ri.ratio AS ratio, i.name AS name, i.quantity AS quantity " +
            "FROM recipe AS r INNER JOIN recipehasingredient AS ri ON r.id = ri.recipe " +
            "INNER JOIN ingredient as i on ri.ingredient = i.name " +
            "WHERE r.id = :recipe")
    fun getRecipeIngredients(recipe: Int): List<RecipeIngredients>
}