package com.example.beer2beer

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
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

        // When the condition becomes false, the activity shows
        val condition = SplashScreen.KeepOnScreenCondition{
            // The splash screen stays up for 1 second before disappearing
            Thread.sleep(1000)
            false
        }
        splashScreen.setKeepOnScreenCondition(condition)


        mainActivitySetup()
        setupBottomNavigationView()
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        if (isUsernameIn()) {
            findNavController(R.id.navHostFragment).navigate(R.id.action_global_home)
        }
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
                binding.fabBrew.visibility = View.GONE
                binding.bottomAppBar.visibility = View.GONE
                binding.bottomNavigationView.visibility = View.GONE
            } else {
                binding.fabBrew.visibility = View.VISIBLE
                binding.bottomAppBar.visibility = View.VISIBLE
                binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }

        binding.bottomNavigationView.background = null
        binding.fabBrew.setOnClickListener {
            navController.navigate(R.id.brewFragment);
        }
    }

    private fun isUsernameIn() : Boolean {
        val sharedPreferences = this.getSharedPreferences("com.example.beer2beer", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")!!
        return username.isNotEmpty()
    }
}