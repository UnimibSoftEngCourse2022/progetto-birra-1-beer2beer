package com.example.beer2beer.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["recipe", "ingredient"],
    foreignKeys = [
        ForeignKey(
            entity = Recipe::class,
            parentColumns = ["id"],
            childColumns = ["recipe"]
        ),
        ForeignKey(
            entity = Ingredient::class,
            parentColumns = ["name"],
            childColumns = ["ingredient"]
        )
    ]
)
data class RecipeHasIngredient(
    val recipe: Int,
    val ingredient: String,

    val ratio: Double
)