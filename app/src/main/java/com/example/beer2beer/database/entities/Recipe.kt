package com.example.beer2beer.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int,

    val name: String
)