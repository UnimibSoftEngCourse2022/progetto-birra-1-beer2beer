package com.example.beer2beer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.beer2beer.database.EquipmentDao
import com.example.beer2beer.database.IngredientDao
import com.example.beer2beer.database.entities.Equipment
import com.example.beer2beer.database.entities.Ingredient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IngredientRepository (
    private val dao: IngredientDao,
    private val coroutineContext: CoroutineScope
) {

    fun createIngredient(ingredient: Ingredient) {
        coroutineContext.launch(Dispatchers.IO) {
            dao.insert(ingredient)
        }
    }

    fun updateIngredient(name: String, newQuantity: Double) {
        coroutineContext.launch(Dispatchers.IO) {
            dao.update(name, newQuantity)
        }
    }

    fun deleteIngredient(ingredient: Ingredient) {
        coroutineContext.launch(Dispatchers.IO) {
            dao.delete(ingredient)
        }
    }

    fun getAllIngredients(): LiveData<List<Ingredient>> =
        liveData(Dispatchers.IO) {
            emitSource(dao.getAll())
        }
}