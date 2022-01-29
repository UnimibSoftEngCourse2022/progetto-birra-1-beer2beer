package com.example.beer2beer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.beer2beer.R
import com.example.beer2beer.database.entities.Recipe

class RecipeAdapter() : ListAdapter<Recipe, RecipeAdapter.RecipeViewHolder>(RecipeDiffcallback()) {
    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeNameTextView: TextView = view.findViewById(R.id.RecipeNameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.recipeNameTextView.text = getItem(position).name
    }

}

class RecipeDiffcallback : DiffUtil.ItemCallback<Recipe>(){
    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem == newItem
    }
}