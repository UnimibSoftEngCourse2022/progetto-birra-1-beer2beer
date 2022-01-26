package com.example.beer2beer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.beer2beer.SharedViewModel
import com.example.beer2beer.adapters.RecipeAdapter
import com.example.beer2beer.database.entities.Recipe
import com.example.beer2beer.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var  binding: FragmentHomeBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeFragmentSetup(inflater, container)

        viewModel.testDb()

        val recipe1 = Recipe(0, "ricetta1")
        val recipe2 = Recipe(0, "ricetta2")
        val lista = listOf<Recipe>(recipe1, recipe2)

        binding.homeRecyclerView.adapter = RecipeAdapter(lista)

        return binding.root
    }

    private fun homeFragmentSetup(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }
}