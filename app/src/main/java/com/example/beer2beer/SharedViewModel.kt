package com.example.beer2beer

import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.beer2beer.databinding.ActivityMainBinding

class SharedViewModel : ViewModel() {

    fun setupBottomNavigationView(activity: MainActivity, binding: ActivityMainBinding) {

        val navHostFragment =
            activity.supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)
    }

}