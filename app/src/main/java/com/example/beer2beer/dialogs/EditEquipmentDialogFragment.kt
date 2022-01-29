package com.example.beer2beer.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.beer2beer.R
import com.example.beer2beer.SharedViewModel
import com.example.beer2beer.database.entities.Equipment
import com.example.beer2beer.databinding.DialogEditEquipmentBinding

class EditEquipmentDialogFragment (private val position: Int) : DialogFragment() {

    // Use this instance of the interface to deliver action events
    private val viewModel: SharedViewModel by activityViewModels()
    private lateinit var binding: DialogEditEquipmentBinding


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val equipmentName = viewModel.equipment.value?.get(position)?.name
        val equipmentCapacity = viewModel.equipment.value?.get(position)?.capacity
        val equipmentCategory = viewModel.equipment.value?.get(position)?.category

        // Set up the binding object
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_edit_equipment, null, false)

        // Set up the info to display in the dialog
        binding.equipmentNameEditText.setText(equipmentName)

        binding.quantityEditText.setText(equipmentCapacity.toString())

        // Create the buttons click listeners
        binding.addButton.setOnClickListener {
            val currentValue = binding.quantityEditText.text.toString().toDouble()
            val newValue = currentValue + 1.0
            binding.quantityEditText.setText(newValue.toString())
        }

        binding.subButton.setOnClickListener {
            val currentValue = binding.quantityEditText.text.toString().toDouble()
            val newValue = currentValue - 1.0
            if (newValue >= 0.0)
                binding.quantityEditText.setText(newValue.toString())
        }

        val categories = arrayOf(
            "Fermentatore",
            "Fermentatore per travaso",
            "Bilancia",
            "Misuratore"
        )

        binding.spinner.adapter = context?.let { ArrayAdapter(it, R.layout.spinner_dropdown_item, categories) }
        
        for(i in 0..3){
            if(categories[i] == equipmentCategory) {
                Log.d("TAG", "onCreateDialog: " + equipmentCategory)
                binding.spinner.setSelection(i)
                break
            }
        }

        return activity?.let {
            val builder = AlertDialog.Builder(it, R.style.CustomAlertDialog)

            builder.setView(binding.root)
                .setPositiveButton(R.string.save) { _, _ ->
                    val quantity: Double
                    if (binding.quantityEditText.text.toString().isEmpty())
                        quantity = 0.0
                    else
                        quantity = binding.quantityEditText.text.toString().toDouble()

                    val equipment = viewModel.equipment.value?.get(position)?.let { it1 ->
                        Equipment(
                            it1.id,
                            binding.spinner.selectedItem.toString(),
                            binding.equipmentNameEditText.text.toString(),
                            quantity)
                    }

                    if (equipment != null) {
                        viewModel.updateEquipment(equipment)
                    }
                }
                .setNegativeButton(R.string.discard) { _, _ ->
                    dialog?.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}