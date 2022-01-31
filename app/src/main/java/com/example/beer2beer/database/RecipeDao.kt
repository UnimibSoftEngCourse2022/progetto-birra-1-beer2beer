package com.example.beer2beer.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.*
import com.example.beer2beer.database.entities.Recipe
import com.example.beer2beer.database.entities.RecipeIngredients
import com.example.beer2beer.database.entities.RecipeHasIngredient
import com.example.beer2beer.database.entities.RecipeInstance

@Dao
interface RecipeDao {
    @Insert(onConflict = REPLACE)
    fun insert(recipe: Recipe): Long

    @Insert(onConflict = REPLACE)
    fun insertInstance(recipeInstance: RecipeInstance)

    @Insert(onConflict = REPLACE)
    fun insertIngredient(recipeHasIngredient: RecipeHasIngredient)

    @Query("UPDATE recipeinstance SET note = :newNote WHERE id = :id")
    fun updateRecipeInstance(id: Int, newNote: String)

    @Update
    fun update(recipe: Recipe)

    @Query("DELETE FROM recipe WHERE id = :id")
    fun delete(id: Int)

    @Query("DELETE FROM recipe")
    fun deleteAll()

    @Query("SELECT * FROM recipe")
    fun getAll(): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipehasingredient")
    fun getRecipeHasIngredients(): LiveData<List<RecipeHasIngredient>>

    @Query("SELECT * FROM recipeinstance")
    fun getRecipeInstances(): LiveData<List<RecipeInstance>>

    @Query(
        "SELECT ri.ratio AS ratio, i.name AS name, i.quantity AS quantity " +
                "FROM recipe AS r INNER JOIN recipehasingredient AS ri ON r.id = ri.recipe " +
                "INNER JOIN ingredient as i on ri.ingredient = i.name " +
                "WHERE r.id = :recipe"
    )
    fun getRecipeIngredients(recipe: Int): List<RecipeIngredients>
}