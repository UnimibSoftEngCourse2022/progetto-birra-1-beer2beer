package com.example.beer2beer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beer2beer.R
import com.example.beer2beer.database.entities.Recipe

class RecipeAdapter(private val dataSet: List<Recipe>) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeNameTextView: TextView

        init {
            recipeNameTextView = view.findViewById(R.id.RecipeNameTextView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.recipeNameTextView.text = dataSet[position].name
    }

    override fun getItemCount(): Int = dataSet.size
}