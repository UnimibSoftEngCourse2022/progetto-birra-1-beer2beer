package com.example.beer2beer.welcome

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.beer2beer.MainViewModel
import com.example.beer2beer.R
import com.example.beer2beer.databinding.ActivityMainBinding
import com.example.beer2beer.databinding.WelcomeFragmentBinding

class WelcomeFragment : Fragment() {

    companion object {
        fun newInstance() = WelcomeFragment()
    }

    private lateinit var viewModel: WelcomeViewModel
    private lateinit var binding: WelcomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        welcomeFragmentSetup(inflater, container)
        return binding.root

    }

    private fun welcomeFragmentSetup(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        viewModel = ViewModelProvider(this).get(WelcomeViewModel::class.java)
        binding = WelcomeFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}