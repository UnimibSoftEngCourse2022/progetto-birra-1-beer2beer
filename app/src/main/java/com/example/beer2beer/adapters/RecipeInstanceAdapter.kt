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
import com.example.beer2beer.database.entities.RecipeInstance

class RecipeInstanceAdapter : ListAdapter<RecipeInstance, RecipeInstanceAdapter.RecipeInstanceViewHolder>(RecipeInstanceDiffcallback()) {
    class RecipeInstanceViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val instanceDateTextView: TextView = view.findViewById(R.id.dateTextView)
        val quantityTextView: TextView = view.findViewById(R.id.instanceQuantityTextView)
        val noteTextView: TextView = view.findViewById(R.id.noteTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeInstanceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_instance_item, parent, false)
        return RecipeInstanceViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeInstanceViewHolder, position: Int) {
        holder.instanceDateTextView.text = getItem(position).date.toString()
        holder.quantityTextView.text = getItem(position).quantity.toString()
        holder.noteTextView.text = getItem(position).note
    }
}

class RecipeInstanceDiffcallback : DiffUtil.ItemCallback<RecipeInstance>(){
    override fun areItemsTheSame(oldItem: RecipeInstance, newItem: RecipeInstance): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RecipeInstance, newItem: RecipeInstance): Boolean {
        return oldItem == newItem
    }
}