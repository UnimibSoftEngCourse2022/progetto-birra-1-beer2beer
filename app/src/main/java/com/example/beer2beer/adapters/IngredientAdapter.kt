package com.example.beer2beer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.beer2beer.R
import com.example.beer2beer.database.entities.Ingredient

class IngredientAdapter :
    ListAdapter<Ingredient, IngredientAdapter.IngredientViewHolder>(IngredientDiffCallback()) {
    class IngredientViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ingredientNameTextView: TextView = view.findViewById(R.id.ingredientTextView)
        val quantityEditText: EditText = view.findViewById(R.id.quantityEditText)
        val unitOfMeasureTextView: TextView = view.findViewById(R.id.unitOfMeasureTextView)
        val addButton: Button = view.findViewById(R.id.addButton)
        val subButton: Button = view.findViewById(R.id.subButton)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false)
        return IngredientViewHolder(view)
    }

    var ingredientQuantities = DoubleArray(6)
    var ingredientNames = Array(6){"it = $it"}

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        ingredientNames[position] = getItem(position).name
        holder.ingredientNameTextView.text = getItem(position).name
        holder.unitOfMeasureTextView.text = getItem(position).unitOfMeasure
        holder.quantityEditText.setText(0.0.toString())

        holder.addButton.setOnClickListener {
            val currentValue = holder.quantityEditText.text.toString().toDouble()
            val newValue = currentValue + 1.0
            holder.quantityEditText.setText(newValue.toString())
        }

        holder.subButton.setOnClickListener {
            val currentValue = holder.quantityEditText.text.toString().toDouble()
            val newValue = currentValue - 1.0
            if (newValue >= 0.0)
                holder.quantityEditText.setText(newValue.toString())
        }

        holder.quantityEditText.addTextChangedListener { s ->
            if (s.toString().isEmpty())
                ingredientQuantities[position] = 0.0
            else
                ingredientQuantities[position] = s.toString().toDouble()
        }
    }
}

class IngredientDiffCallback : DiffUtil.ItemCallback<Ingredient>() {
    override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
        return oldItem == newItem
    }
}