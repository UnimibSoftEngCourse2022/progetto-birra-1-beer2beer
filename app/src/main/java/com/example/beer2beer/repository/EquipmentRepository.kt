package com.example.beer2beer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.beer2beer.database.EquipmentDao
import com.example.beer2beer.database.RecipeDao
import com.example.beer2beer.database.entities.Equipment
import com.example.beer2beer.database.entities.Recipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EquipmentRepository (
    private val dao: EquipmentDao,
    private val coroutineContext: CoroutineScope
) {

    fun createEquipment(equipment: Equipment) {
        coroutineContext.launch(Dispatchers.IO) {
            dao.insert(equipment)
        }
    }

    fun updateEquipment(equipment: Equipment) {
        coroutineContext.launch(Dispatchers.IO) {
            dao.update(equipment)
        }
    }

    fun deleteEquipment(equipment: Equipment) {
        coroutineContext.launch(Dispatchers.IO) {
            dao.delete(equipment.id)
        }
    }

    fun getAllEquipments(): LiveData<List<Equipment>> =
        liveData(Dispatchers.IO) {
            emitSource(dao.getAll())
        }
}