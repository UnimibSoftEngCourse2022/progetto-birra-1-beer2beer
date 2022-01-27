package com.example.beer2beer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.beer2beer.SharedViewModel
import com.example.beer2beer.adapters.IngredientAdapter
import com.example.beer2beer.databinding.FragmentAddRecipeBinding

class AddRecipeFragment : Fragment() {
    private lateinit var  binding: FragmentAddRecipeBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private val adapter = IngredientAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        recipesFragmentSetup(inflater, container)

        binding.ingredientsRecyclerView.adapter = adapter
        viewModel.ingredients.observe(viewLifecycleOwner){ ingredientsList ->
            adapter.submitList(ingredientsList)
        }

        binding.saveRecipeButton.setOnClickListener {
            val quantities = adapter.ingredientQuantities
        }

        return binding.root
    }

    private fun recipesFragmentSetup(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        binding = FragmentAddRecipeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }
}