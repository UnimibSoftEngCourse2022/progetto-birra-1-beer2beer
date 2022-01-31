package com.example.beer2beer.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.beer2beer.R
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
            if (binding.nameEditText.text.isBlank())
                binding.nameEditText.error = resources.getString(R.string.blankUsernameError)
            else {
                binding.nameEditText.error = null

                val sharedPreferences = this.requireActivity()
                    .getSharedPreferences("com.example.beer2beer", Context.MODE_PRIVATE)
                sharedPreferences.edit()
                    .putString("username", binding.nameEditText.text.toString())
                    .apply()

                val action = GetStartedFragmentDirections.actionGetStartedToHome()
                findNavController().navigate(action)
            }
        }

        return binding.root
    }

    private fun getStartedFragmentSetup(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        binding = FragmentGetStartedBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }
}