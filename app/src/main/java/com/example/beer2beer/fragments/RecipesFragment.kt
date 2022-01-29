package com.example.beer2beer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.beer2beer.R
import com.example.beer2beer.SharedViewModel
import com.example.beer2beer.adapters.EquipmentAdapter
import com.example.beer2beer.databinding.FragmentRecipesBinding

class RecipesFragment : Fragment() {
    private lateinit var binding: FragmentRecipesBinding
    private val viewModel: SharedViewModel by activityViewModels()
    private val adapter = EquipmentAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        recipesFragmentSetup(inflater, container)

        viewModel.equipment.observe(viewLifecycleOwner) { equipmentList ->
            adapter.submitList(equipmentList)
        }
        binding.equipmentRecyclerView.adapter = adapter


        binding.addEquipmentFab.setOnClickListener {
            findNavController().navigate(R.id.addEquipmentFragment)
        }

        return binding.root
    }

    private fun recipesFragmentSetup(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }
}