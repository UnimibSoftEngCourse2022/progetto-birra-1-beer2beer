package com.example.beer2beer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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
        val splashScreen = installSplashScreen()
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

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.welcomeFragment || destination.id == R.id.getStartedFragment){
                binding.bottomNavigationView.visibility = View.GONE
            } else {
                binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }
}