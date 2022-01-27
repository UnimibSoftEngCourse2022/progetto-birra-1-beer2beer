package com.example.beer2beer.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.beer2beer.database.entities.Equipment

@Dao
interface EquipmentDao {
    @Insert
    fun insert(equipment: Equipment)

    @Update
    fun update(equipment: Equipment)

    @Delete
    fun delete(equipment: Equipment)

    @Query("SELECT * FROM equipment")
    fun getAll(): LiveData<List<Equipment>>
}