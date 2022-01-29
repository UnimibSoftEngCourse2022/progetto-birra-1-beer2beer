package com.example.beer2beer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.beer2beer.SharedViewModel
import com.example.beer2beer.database.entities.Equipment
import com.example.beer2beer.databinding.FragmentAddEquipmentBinding

class AddEquipmentFragment : Fragment() {

    private lateinit var binding: FragmentAddEquipmentBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        equipmentsFragmentSetup(inflater, container)

        val categories = arrayOf(
            "Fermentatore",
            "Fermentatore per travaso",
            "Bilancia",
            "Misuratore"
        )


        binding.quantityEditText.setText(0.0.toString())

        binding.spinner.adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, categories) }

        var quantity = 0.0
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

        binding.quantityEditText.addTextChangedListener { s ->
            if (s.toString().isEmpty())
                quantity = 0.0
            else
                quantity = s.toString().toDouble()
        }

        binding.saveEquipmentButton.setOnClickListener {
            if(binding.equipmentNameEditText.text.isBlank())
                binding.equipmentNameEditText.error = "Insert equipment name"
            else {
                viewModel.createEquipment(
                    Equipment(
                        0,
                        category = binding.spinner.selectedItem.toString(),
                        binding.equipmentNameEditText.text.toString(),
                        quantity
                    )
                )

            }
        }

        return binding.root
    }

    private fun equipmentsFragmentSetup(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        binding = FragmentAddEquipmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }
}
