package com.example.beer2beer.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Equipment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val category: String,
    val name: String,
    val capacity: Double
)