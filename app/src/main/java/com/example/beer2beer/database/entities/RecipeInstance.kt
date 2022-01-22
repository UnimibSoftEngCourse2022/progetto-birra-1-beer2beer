package com.example.beer2beer.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Recipe::class,
            parentColumns = ["id"],
            childColumns = ["recipe"]
        )
    ]
)
data class RecipeInstance(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val recipe: Int,

    val date: Date,
    val note: String,
    val quantity: Double
)