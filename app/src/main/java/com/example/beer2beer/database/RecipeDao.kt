package com.example.beer2beer.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.*
import com.example.beer2beer.database.entities.Recipe
import com.example.beer2beer.database.entities.RecipeIngredients
import com.example.beer2beer.database.entities.RecipeHasIngredient

@Dao
interface RecipeDao {
    @Insert(onConflict = REPLACE)
    fun insert(recipe: Recipe): Long

    @Insert(onConflict = REPLACE)
    fun insertIngredient(recipeHasIngredient: RecipeHasIngredient)

    @Update
    fun update(recipe: Recipe)

    @Query("DELETE FROM recipe WHERE id = :id")
    fun delete(id: Int)

    @Query("SELECT * FROM recipe")
    fun getAll(): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipehasingredient")
    fun getRecipeHasIngredients(): LiveData<List<RecipeHasIngredient>>

    @Query("SELECT ri.ratio AS ratio, i.name AS name, i.quantity AS quantity " +
            "FROM recipe AS r INNER JOIN recipehasingredient AS ri ON r.id = ri.recipe " +
            "INNER JOIN ingredient as i on ri.ingredient = i.name " +
            "WHERE r.id = :recipe")
    fun getRecipeIngredients(recipe: Int): List<RecipeIngredients>
}