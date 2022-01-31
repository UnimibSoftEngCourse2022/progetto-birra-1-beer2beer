package com.example.beer2beer.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.*

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Recipe::class,
            parentColumns = ["id"],
            childColumns = ["recipe"],
            onDelete = CASCADE
        )
    ]
)
data class RecipeInstance(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val recipe: Int,

    val date: String,
    val note: String,
    val quantity: Double
)

//class Converters{
//    @TypeConverter
//    fun fromTimestamp(value: Long?): Date? {
//        return value?.let { Date(it) }
//    }
//
//    @TypeConverter
//    fun dateToTimestamp(date: Date?): Long? {
//        return date?.time
//    }
//}