package com.example.beer2beer.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class RecipeIngredients(

    val name: String,
    val ratio: Double,
    val quantity: Double
)