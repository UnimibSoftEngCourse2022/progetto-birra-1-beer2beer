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
import com.example.beer2beer.database.entities.Recipe
import com.example.beer2beer.databinding.FragmentRecipeDetailBinding

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

        binding.recipeNameTextView.text = recipeName
        //Todo: binda il nome della ricetta alla textview

        viewModel.recipeHasIngredient.observe(viewLifecycleOwner){ recipeIngredientsList ->
            //TODO: Filtra in base al nome della ricetta!
            ingredientsAdapter.submitList(viewModel.filterIngredientsList(recipeIngredientsList, recipeId))
        }
        binding.ingredientsRecyclerView.adapter = ingredientsAdapter


        viewModel.recipeInstances.observe(viewLifecycleOwner){ recipeInstancesList ->
            instancesAdapter.submitList(recipeInstancesList)
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