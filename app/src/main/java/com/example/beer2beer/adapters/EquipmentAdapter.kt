package com.example.beer2beer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.beer2beer.R
import com.example.beer2beer.database.entities.Equipment
import com.example.beer2beer.databinding.EquipmentItemBinding
import com.example.beer2beer.dialogs.EditEquipmentDialogFragment

class EquipmentAdapter(private val childFragmentManager: FragmentManager) : ListAdapter<Equipment, EquipmentAdapter.EquipmentViewHolder>(EquipmentDiffCallback()) {
    class EquipmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = EquipmentItemBinding.bind(view)
        val equipmentNameTextView: TextView = binding.equipmentNameTextView
        val equipmentCapacityTextView: TextView = binding.capacityTextView
        val editEquipment: ImageButton = binding.editButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.equipment_item, parent, false)
        return EquipmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: EquipmentViewHolder, position: Int) {
        holder.equipmentNameTextView.text = getItem(position).name

        val capacity = getItem(position).capacity.toString() + "L"
        holder.equipmentCapacityTextView.text = capacity

        holder.editEquipment.setOnClickListener {
            val dialog = EditEquipmentDialogFragment(position)
            dialog.show(childFragmentManager, "EditEquipmentDialog")
        }
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