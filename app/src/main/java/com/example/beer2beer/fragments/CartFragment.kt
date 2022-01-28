package com.example.beer2beer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.beer2beer.SharedViewModel
import com.example.beer2beer.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private lateinit var  binding: FragmentCartBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        cartFragmentSetup(inflater, container)

        viewModel.ingredients.observe(viewLifecycleOwner){
            val ingList = viewModel.ingredients.value

            binding.waterQtyTextView.text = ingList?.get(0)?.quantity.toString()
            binding.maltsQtyTextView.text = ingList?.get(1)?.quantity.toString()
            binding.hopsQtyTextView.text = ingList?.get(2)?.quantity.toString()
            binding.yeastsQtyTextView.text = ingList?.get(3)?.quantity.toString()
            binding.sugarsQtyTextView.text = ingList?.get(4)?.quantity.toString()
            binding.additivesQtyTextView.text = ingList?.get(0)?.quantity.toString()
        }

        return binding.root
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