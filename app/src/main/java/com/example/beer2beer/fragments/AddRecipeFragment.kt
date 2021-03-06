package com.example.beer2beer.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.beer2beer.R
import com.example.beer2beer.SharedViewModel
import com.example.beer2beer.adapters.IngredientAdapter
import com.example.beer2beer.databinding.FragmentAddRecipeBinding

class AddRecipeFragment : Fragment() {
    private lateinit var binding: FragmentAddRecipeBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private val adapter = IngredientAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        recipesFragmentSetup(inflater, container)

        viewModel.ingredients.observe(viewLifecycleOwner) { ingredientsList ->
            adapter.submitList(ingredientsList)
        }
        binding.ingredientsRecyclerView.adapter = adapter

        binding.saveRecipeButton.setOnClickListener {
            val quantities = adapter.ingredientQuantities
            val ingredientNames = adapter.ingredientNames

            //Clear previous errors
            binding.recipeNameEditText.error = null
            binding.selectIngredientsTextView.error = null

            //UI checks
            var passed = true
            if (binding.recipeNameEditText.text.isBlank()) {
                binding.recipeNameEditText.error =
                    resources.getString(R.string.recipeNameError2)
                passed = false
            }
            var isAllZero = true
            for (quantity in quantities)
                if (quantity != 0.0)
                    isAllZero = false
            if (isAllZero) {
                binding.selectIngredientsTextView.error =
                    resources.getString(R.string.selectIngredientsError)
                passed = false
            }

            //If all the checks passes, save the new recipe and navigate back to homepage
            if (passed) {
                val relativeQuantities = viewModel.processQuantities(quantities)
                val recipeName = binding.recipeNameEditText.text.toString()
                val recipeDescription = binding.descriptionEditText.text.toString()

                viewModel.createNewRecipe(
                    ingredientNames,
                    relativeQuantities,
                    recipeName,
                    recipeDescription
                )

                val action = AddRecipeFragmentDirections.actionAddRecipeToHome()
                findNavController().navigate(action)
            } else
                binding.recipeNameEditText.error = "Equipment' s capacity not enough"
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