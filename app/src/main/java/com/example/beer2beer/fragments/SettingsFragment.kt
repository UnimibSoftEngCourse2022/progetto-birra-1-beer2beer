package com.example.beer2beer.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.beer2beer.SharedViewModel
import com.example.beer2beer.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private lateinit var  binding: FragmentSettingsBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        settingsFragmentSetup(inflater, container)

        binding.buttonClearName.setOnClickListener {
            val sharedPreferences = this.requireActivity().getSharedPreferences("com.example.beer2beer", Context.MODE_PRIVATE)
            sharedPreferences.edit().clear().apply()
            this.requireActivity().finishAffinity()
        }

        return binding.root
    }

    private fun settingsFragmentSetup(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}