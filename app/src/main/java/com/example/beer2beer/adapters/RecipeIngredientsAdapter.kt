package com.example.beer2beer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.beer2beer.R
import com.example.beer2beer.database.entities.RecipeHasIngredient

class RecipeIngredientsAdapter : ListAdapter<RecipeHasIngredient, RecipeIngredientsAdapter.RecipeIngredientsViewHolder>(RecipeHasIngredientsDiffcallback()){
    class RecipeIngredientsViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val ingredientNameTextView: TextView = view.findViewById(R.id.ingredientNameTextView)
        val quantityTextView: TextView = view.findViewById(R.id.quantityTextView)
        val unitOfMeasureTextView: TextView = view.findViewById(R.id.unitOfMeasureRecipeIngredientTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeIngredientsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_ingredient_item, parent, false)
        return RecipeIngredientsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeIngredientsViewHolder, position: Int) {
        holder.ingredientNameTextView.text = getItem(position).ingredient
        holder.quantityTextView.text = getItem(position).ratio.toString()
        if (getItem(position).ingredient == "Water")
            holder.unitOfMeasureTextView.text = "L"
        else
            holder.unitOfMeasureTextView.text = "g"
    }
}

class RecipeHasIngredientsDiffcallback : DiffUtil.ItemCallback<RecipeHasIngredient>(){
    override fun areItemsTheSame(oldItem: RecipeHasIngredient, newItem: RecipeHasIngredient): Boolean {
        return oldItem.ingredient == newItem.ingredient
    }

    override fun areContentsTheSame(oldItem: RecipeHasIngredient, newItem: RecipeHasIngredient): Boolean {
        return oldItem == newItem
    }
}