package com.example.beer2beer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.beer2beer.SharedViewModel
import com.example.beer2beer.databinding.FragmentGetStartedBinding

class GetStartedFragment : Fragment() {
    private lateinit var binding: FragmentGetStartedBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        getStartedFragmentSetup(inflater, container)

        binding.continueButton.setOnClickListener {
            val action = GetStartedFragmentDirections.actionGetStartedToHome()
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun getStartedFragmentSetup(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        binding = FragmentGetStartedBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}