package com.example.beer2beer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.beer2beer.database.AppDatabase
import com.example.beer2beer.database.entities.*
import com.example.beer2beer.repository.EquipmentRepository
import com.example.beer2beer.repository.IngredientRepository
import com.example.beer2beer.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    // get the Database instance
    private val db = AppDatabase.getInstance(getApplication<Application>().applicationContext)

    private val recipeRepository = RecipeRepository(db.recipeDao(), viewModelScope)
    private val ingredientRepository = IngredientRepository(db.ingredientDao(), viewModelScope)
    private val equipmentRepository = EquipmentRepository(db.equipmentDao(), viewModelScope)

    // get all necessary lists
    val recipes = recipeRepository.getAllRecipes()
    val ingredients = ingredientRepository.getAllIngredients()
    val equipment = equipmentRepository.getAllEquipments()


    //RECIPES
    fun createNewRecipe(
        ingredientNames: Array<String>,
        quantities: DoubleArray,
        name: String,
        description: String
    ) {
        val recipe = Recipe(0, name, description)

        viewModelScope.launch(Dispatchers.IO) {
            val id = recipeRepository.createRecipe(recipe)
            quantities.forEachIndexed { index, quantity ->
                val rhi = RecipeHasIngredient(id.toInt(), ingredientNames[index], quantity)
                recipeRepository.insertIngredients(rhi)
            }
        }
    }

    //INGREDIENTS

    // This function transform the raw quantities in relative quantities
    fun processQuantities(quantities: DoubleArray): DoubleArray {
        var total = 0.0
        quantities.forEachIndexed { index, q ->
            // Converts water into grams
            total += if (index == 0)
                q * 1000
            else
                q
        }
        val relativeQuantities = DoubleArray(quantities.size)
        quantities.forEachIndexed { index, q ->
            if (index == 0)
                relativeQuantities[index] = (q * 1000) / total
            else
                relativeQuantities[index] = q / total
        }
        return relativeQuantities
    }

    fun deleteRecipeById(id: Int) {
        recipeRepository.deleteRecipe(id)
    }

    fun getRecipeIngredients(id: Int): LiveData<List<RecipeIngredients>> {
        return recipeRepository.getRecipeIngredients(id)
    }

    fun updateIngredient(name: String, newQuantity: Double) {
        ingredientRepository.updateIngredient(name, newQuantity)
        viewModelScope.launch(Dispatchers.IO) {
            db.ingredientDao().update(name, newQuantity)
        }

    }

    //EQUIPMENTS

    fun createEquipment(name: String, category: String, capacity: Double) {
        val equipment = Equipment(0, name, category, capacity)
        equipmentRepository.createEquipment(equipment)
    }

    fun deleteEquipment(equipment: Equipment) {
        equipmentRepository.deleteEquipment(equipment)
    }

    fun updateEquipment(equipment: Equipment) {
        equipmentRepository.updateEquipment(equipment)
    }

    // RECIPE DETAIL

    val recipeHasIngredient = recipeRepository.getRecipeHasIngredients()
    fun filterIngredientsList(
        ingList: List<RecipeHasIngredient>,
        recipeId: Int
    ): List<RecipeHasIngredient> {
        val result = mutableListOf<RecipeHasIngredient>()
        ingList.forEach {
            if (it.ratio > 0.0 && it.recipe == recipeId)
                result.add(it)
        }
        return result.toList()
    }

    val recipeInstances = recipeRepository.getRecipeInstances()
    fun filterRecipeInstancesList(
        insList: List<RecipeInstance>,
        recipeId: Int
    ): List<RecipeInstance> {
        val result = mutableListOf<RecipeInstance>()
        insList.forEach {
            if (it.recipe == recipeId)
                result.add(it)
        }
        return result.toList()
    }

    fun createRecipeInstance(recipeInstance: RecipeInstance): Boolean {
        var equipmentCapacity = 0.0
        equipment.value?.forEach {
            equipmentCapacity += it.capacity
        }

        if (recipeInstance.quantity > equipmentCapacity)
            return false

        recipeRepository.createInstance(recipeInstance)
        return true
    }

    fun updateRecipeInstance(instanceId: Int, newNote: String) {
        recipeRepository.updateRecipeInstance(instanceId, newNote)
    }

    // SETTINGS
    fun resetDatabase() {
        equipmentRepository.deleteAllEquipment()
        recipeRepository.deleteAllRecipe()
        ingredientRepository.resetIngredients()

    }
}
