package com.example.beer2beer

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.beer2beer.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class SharedViewModel : ViewModel() {

    public fun setupBottomNavigationView(activity: MainActivity, binding: ActivityMainBinding) {

        val bottomNavigationView: BottomNavigationView = binding.bottomNavigationView

        val navHostFragment = (activity.supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment)

        val navController: NavController = navHostFragment.navController

        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

}