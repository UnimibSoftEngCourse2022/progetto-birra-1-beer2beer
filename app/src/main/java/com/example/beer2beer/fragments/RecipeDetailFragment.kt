package com.example.beer2beer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.beer2beer.SharedViewModel
import com.example.beer2beer.adapters.RecipeIngredientsAdapter
import com.example.beer2beer.adapters.RecipeInstanceAdapter
import com.example.beer2beer.databinding.FragmentRecipeDetailBinding
import com.example.beer2beer.dialogs.AddRecipeInstanceDialogFragment

class RecipeDetailFragment : Fragment() {
    private lateinit var binding: FragmentRecipeDetailBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private val ingredientsAdapter = RecipeIngredientsAdapter()
    private val instancesAdapter = RecipeInstanceAdapter()
    private val args: RecipeDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        recipeInstanceFragmentSetup(inflater, container)

        val recipeId = args.recipeId
        val recipeName = args.recipeName
        val recipeDescription = args.recipeDescription

        binding.recipeNameTextView.text = recipeName
        binding.recipeDescriptionTextView.text = recipeDescription

        binding.addInstanceFab.setOnClickListener {
            val dialog = AddRecipeInstanceDialogFragment(recipeId)
            dialog.show(childFragmentManager, "AddInstanceDialog")
        }

        viewModel.recipeHasIngredient.observe(viewLifecycleOwner){ recipeIngredientsList ->
            ingredientsAdapter.submitList(viewModel.filterIngredientsList(recipeIngredientsList, recipeId))
        }
        binding.ingredientsRecyclerView.adapter = ingredientsAdapter


        viewModel.recipeInstances.observe(viewLifecycleOwner){ recipeInstancesList ->
            instancesAdapter.submitList(viewModel.filterRecipeInstancesList(recipeInstancesList, recipeId))
        }
        binding.recipeInstancesRecyclerView.adapter = instancesAdapter

        return binding.root
    }

    private fun recipeInstanceFragmentSetup(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }
}