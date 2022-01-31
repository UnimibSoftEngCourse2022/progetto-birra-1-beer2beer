package com.example.beer2beer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.beer2beer.SharedViewModel
import com.example.beer2beer.databinding.FragmentCartBinding
import com.example.beer2beer.dialogs.AddIngredientsDialogFragment

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        cartFragmentSetup(inflater, container)

        // Bind ingredients quantities
        viewModel.ingredients.observe(viewLifecycleOwner) {
            val ingList = viewModel.ingredients.value
            binding.waterQtyTextView.text = ingList?.get(0)?.quantity.toString()
            binding.maltsQtyTextView.text = ingList?.get(1)?.quantity.toString()
            binding.hopsQtyTextView.text = ingList?.get(2)?.quantity.toString()
            binding.yeastsQtyTextView.text = ingList?.get(3)?.quantity.toString()
            binding.sugarsQtyTextView.text = ingList?.get(4)?.quantity.toString()
            binding.additivesQtyTextView.text = ingList?.get(5)?.quantity.toString()
        }

        // Set click listeners
        binding.addWaterButton.setOnClickListener {
            val qnt = viewModel.ingredients.value?.get(0)?.quantity
            makeAddIngredientsDialog("Water", qnt)
        }
        binding.addMaltsButton.setOnClickListener {
            val qnt = viewModel.ingredients.value?.get(1)?.quantity
            makeAddIngredientsDialog("Malts", qnt)
        }
        binding.addHopsButton.setOnClickListener {
            val qnt = viewModel.ingredients.value?.get(2)?.quantity
            makeAddIngredientsDialog("Hops", qnt)
        }
        binding.addYeastsButton.setOnClickListener {
            val qnt = viewModel.ingredients.value?.get(3)?.quantity
            makeAddIngredientsDialog("Yeast", qnt)
        }
        binding.addSugarsButton.setOnClickListener {
            val qnt = viewModel.ingredients.value?.get(4)?.quantity
            makeAddIngredientsDialog("Sugars", qnt)
        }
        binding.addAdditivesButton.setOnClickListener {
            val qnt = viewModel.ingredients.value?.get(5)?.quantity
            makeAddIngredientsDialog("Additives", qnt)
        }

        return binding.root
    }

    private fun makeAddIngredientsDialog(ingredientName: String, qnt: Double?) {
        if (qnt != null) {
            val dialog = AddIngredientsDialogFragment(ingredientName, qnt)
            dialog.show(childFragmentManager, "WaterQuantityDialog")
        }
    }

    private fun cartFragmentSetup(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }
}