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
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        settingsFragmentSetup(inflater, container)

        val sharedPreferences = this.requireActivity()
            .getSharedPreferences("com.example.beer2beer", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("username", "NoName")
        val hiName = "Hi $name!"
        binding.hiNameTextView.text = hiName

        binding.buttonClearName.setOnClickListener {
            sharedPreferences.edit().clear().apply()
            viewModel.resetDatabase()
            this.requireActivity().finishAffinity()

            activity?.deleteDatabase("BeerDatabase")
        }

        return binding.root
    }

    private fun settingsFragmentSetup(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }
}
