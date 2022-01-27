package com.example.beer2beer.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ingredient(
    @PrimaryKey val name: String,

    val unitOfMeasure: String,
    val quantity: Double
)