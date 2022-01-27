package com.example.beer2beer.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["recipeInstance", "equipment"],
    foreignKeys = [
        ForeignKey(
            entity = RecipeInstance::class,
            parentColumns = ["id"],
            childColumns = ["recipeInstance"]
        ),
        ForeignKey(
            entity = Equipment::class,
            parentColumns = ["id"],
            childColumns = ["equipment"]
        )
    ]
)
data class RecipeInstanceUseEquipment(
    val recipeInstance: Int,
    val equipment: Int
)