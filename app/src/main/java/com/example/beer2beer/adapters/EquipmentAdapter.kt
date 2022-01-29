package com.example.beer2beer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.beer2beer.R
import com.example.beer2beer.database.entities.Equipment

class EquipmentAdapter : ListAdapter<Equipment, EquipmentAdapter.EquipmentViewHolder>(EquipmentDiffCallback()) {
    class EquipmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val equipmentNameTextView: TextView = view.findViewById(R.id.equipmentNameTextView)
        val equipmentCapacityTextView: TextView = view.findViewById(R.id.capacityTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.equipment_item, parent, false)
        return EquipmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: EquipmentViewHolder, position: Int) {
        holder.equipmentNameTextView.text = getItem(position).name

        val capacity = getItem(position).capacity.toString() + "L"
        holder.equipmentCapacityTextView.text = capacity
    }


}

class EquipmentDiffCallback : DiffUtil.ItemCallback<Equipment>(){
    override fun areItemsTheSame(oldItem: Equipment, newItem: Equipment): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Equipment, newItem: Equipment): Boolean {
        return oldItem == newItem
    }

}