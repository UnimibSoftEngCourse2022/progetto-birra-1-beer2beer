package com.example.beer2beer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.beer2beer.database.AppDatabase
import com.example.beer2beer.database.entities.Equipment
import com.example.beer2beer.database.entities.Recipe
import com.example.beer2beer.database.entities.RecipeHasIngredient
import com.example.beer2beer.repository.EquipmentRepository
import com.example.beer2beer.repository.IngredientRepository
import com.example.beer2beer.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.commons.math3.linear.OpenMapRealVector
import org.apache.commons.math3.linear.RealVector
import org.apache.commons.math3.optim.MaxIter
import org.apache.commons.math3.optim.OptimizationData
import org.apache.commons.math3.optim.linear.*
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val DEFAULT_MAX_ITER = MaxIter(100)

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

    fun whatShouldIBrewToday(): String {

        var bestRecipe: Recipe? = null
        var bestQuantity = 0.0
        val ss = SimplexSolver()

        viewModelScope.launch(Dispatchers.IO) {
            recipes.value?.forEach { recipe ->
                val recipeIngredients = db.recipeDao().getRecipeIngredients(recipe.id)
                val constraint = ArrayList<LinearConstraint>()
                val coeff = DoubleArray(recipeIngredients.size + 1)

                for (i in recipeIngredients.indices) {
                    coeff[i] = -recipeIngredients.get(i).ratio
                    val c = DoubleArray(recipeIngredients.size + 1)
                    c.fill(0.0)
                    c[i] = 1.0
                    if(recipeIngredients.get(i).name == "Water"){
                        constraint.add(
                            LinearConstraint(
                                c,
                                Relationship.LEQ,
                                recipeIngredients.get(i).quantity * 1000
                            )
                        )
                    }else{
                        constraint.add(
                            LinearConstraint(
                                c,
                                Relationship.LEQ,
                                recipeIngredients.get(i).quantity
                            )
                        )
                    }

                }

                coeff[coeff.lastIndex] = 1.0
                constraint.add(LinearConstraint(coeff, Relationship.EQ, 0.0))

                val c = DoubleArray(recipeIngredients.size + 1)
                c.fill(0.0)
                c[c.lastIndex] = 1.0

                val fObb = LinearObjectiveFunction(c, 0.0)
                val constr = LinearConstraintSet(constraint)

                val solution =
                    ss.optimize(
                        DEFAULT_MAX_ITER, fObb, constr,
                        GoalType.MAXIMIZE, NonNegativeConstraint(true)
                    ).value

                if (bestRecipe == null) {
                    bestRecipe = recipe
                    bestQuantity = solution

                } else if (solution > bestQuantity) {
                    bestRecipe = recipe
                    bestQuantity = solution
                }

            }


        }

        return bestRecipe?.name ?: ""
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

}
