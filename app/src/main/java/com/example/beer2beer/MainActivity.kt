package com.example.beer2beer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.beer2beer.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: SharedViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivitySetup()
        setupBottomNavigationView()
        setContentView(binding.root)
    }

    private fun mainActivitySetup() {
        viewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        // Setup binding object to inflate the activity
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Bind the viewModel in the layout to the viewModel class
        binding.viewModel = viewModel

        // Makes LiveData update the UI correctly
        binding.lifecycleOwner = this
    }

    private fun setupBottomNavigationView() {
        val navHostFragment = this.supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        navView = binding.bottomNavigationView
        NavigationUI.setupWithNavController(navView, navController)
        //binding.bottomNavigationView.setupWithNavController(navController)
    }
}