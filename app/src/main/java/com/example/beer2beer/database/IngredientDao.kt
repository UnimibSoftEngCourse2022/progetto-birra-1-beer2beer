package com.example.beer2beer.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.beer2beer.database.entities.Ingredient

@Dao
interface IngredientDao {
    @Insert
    fun insert(ingredient: Ingredient)

    @Query("UPDATE Ingredient SET quantity = :quantity WHERE name = :name")
    fun update(name: String, quantity: Double)

    @Delete
    fun delete(ingredient: Ingredient)

    @Query("SELECT * FROM ingredient")
    fun getAll(): LiveData<List<Ingredient>>
}