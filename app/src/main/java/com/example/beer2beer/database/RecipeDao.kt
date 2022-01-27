package com.example.beer2beer.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.*
import com.example.beer2beer.database.entities.Recipe

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
}