package com.example.beer2beer.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(
    primaryKeys = ["recipeInstance", "equipment"],
    foreignKeys = [
        ForeignKey(
            entity = RecipeInstance::class,
            parentColumns = ["id"],
            childColumns = ["recipeInstance"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Equipment::class,
            parentColumns = ["id"],
            childColumns = ["equipment"],
            onDelete = CASCADE
        )
    ]
)
data class RecipeInstanceUseEquipment(
    val recipeInstance: Int,
    val equipment: Int
)