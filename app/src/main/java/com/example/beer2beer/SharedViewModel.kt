package com.example.beer2beer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.beer2beer.database.AppDatabase
import com.example.beer2beer.database.entities.Equipment
import com.example.beer2beer.database.entities.Ingredient
import com.example.beer2beer.database.entities.Recipe
import com.example.beer2beer.database.entities.RecipeHasIngredient
import com.example.beer2beer.repository.EquipmentRepository
import com.example.beer2beer.repository.IngredientRepository
import com.example.beer2beer.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    // get the Database instance
    private val db = AppDatabase.getInstance(getApplication<Application>().applicationContext)

    private val recipeRepository = RecipeRepository(db.recipeDao(), viewModelScope)
    private val ingredientRepository = IngredientRepository(db.ingredientDao(), viewModelScope)
    private val equipmentRepository = EquipmentRepository(db.equipmentDao(), viewModelScope)


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
            // Se sto processando l'acqua, la converto in grammi
            if (index == 0)
                total += q * 1000
            else
                total += q
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

    fun updateIngredient(name: String, newQuantity: Double) {
        ingredientRepository.updateIngredient(name, newQuantity)
    }

    //EQUIPMENTS

    fun createEquipment(equipment: Equipment) {
        equipmentRepository.createEquipment(equipment)
    }

    fun deleteEquipment(equipment: Equipment) {
        equipmentRepository.deleteEquipment(equipment)
    }

    fun updateEquipment(equipment: Equipment) {
        equipmentRepository.updateEquipment(equipment)
    }

}
