package com.example.beer2beer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.beer2beer.SharedViewModel
import com.example.beer2beer.adapters.EquipmentAdapter
import com.example.beer2beer.databinding.FragmentRecipesBinding
import com.example.beer2beer.utils.SwipeToDeleteCallback

class RecipesFragment : Fragment() {
    private lateinit var binding: FragmentRecipesBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        recipesFragmentSetup(inflater, container)

        val adapter = EquipmentAdapter(childFragmentManager)

        viewModel.equipment.observe(viewLifecycleOwner) { equipmentList ->
            adapter.submitList(equipmentList)
        }
        binding.equipmentRecyclerView.adapter = adapter

        binding.addEquipmentFab.setOnClickListener {
            val action = RecipesFragmentDirections.actionRecipesToAddEquipment()
            findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val swipeHandler = object : SwipeToDeleteCallback(view.context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val equipment = viewModel.equipment.value?.get(viewHolder.adapterPosition)

                if (equipment != null) {
                    viewModel.deleteEquipment(equipment)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.equipmentRecyclerView)
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