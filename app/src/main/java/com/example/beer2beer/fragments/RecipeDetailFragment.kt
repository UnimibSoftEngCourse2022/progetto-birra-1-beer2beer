package com.example.beer2beer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.beer2beer.SharedViewModel
import com.example.beer2beer.adapters.RecipeIngredientsAdapter
import com.example.beer2beer.database.entities.RecipeHasIngredient
import com.example.beer2beer.databinding.FragmentRecipeDetailBinding

class RecipeDetailFragment : Fragment() {
    private lateinit var binding: FragmentRecipeDetailBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private val ingredientsAdapter = RecipeIngredientsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        recipeInstanceFragmentSetup(inflater, container)

        viewModel.recipeHasIngredient.observe(viewLifecycleOwner){ recipeIngredientsList ->
            //TODO: Filtra in base al nome della ricetta!
            ingredientsAdapter.submitList(viewModel.filterIngredientsList())
        }
        binding.ingredientsRecyclerView.adapter = ingredientsAdapter

        return binding.root
    }

    private fun recipeInstanceFragmentSetup(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }
}