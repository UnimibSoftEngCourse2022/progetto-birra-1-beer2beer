package com.example.beer2beer.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(
    primaryKeys = ["recipe", "ingredient"],
    foreignKeys = [
        ForeignKey(
            entity = Recipe::class,
            parentColumns = ["id"],
            childColumns = ["recipe"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Ingredient::class,
            parentColumns = ["name"],
            childColumns = ["ingredient"],
            onDelete = CASCADE
        )
    ]
)
data class RecipeHasIngredient(
    val recipe: Int,
    val ingredient: String,

    val ratio: Double
)