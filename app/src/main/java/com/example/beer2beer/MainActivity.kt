package com.example.beer2beer

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.beer2beer.database.entities.RecipeInstance
import com.example.beer2beer.databinding.ActivityMainBinding
import com.example.beer2beer.dialogs.AddIngredientsDialogFragment
import com.example.beer2beer.dialogs.AddRecipeInstanceDialogFragment
import com.example.beer2beer.dialogs.EditRecipeInstanceDialogFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity(),
    AddIngredientsDialogFragment.AddIngredientsDialogListener,
    AddRecipeInstanceDialogFragment.AddRecipeInstanceDialogListener,
    EditRecipeInstanceDialogFragment.EditRecipeInstanceDialogListener {

    private lateinit var viewModel: SharedViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var navView: BottomNavigationView

    private var isFirstTime = true
    private var isAlreadyStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Check if the username is already set
        isFirstTime = !isUsernameIn()

        installSplashScreen()

        mainActivitySetup()
        setupBottomNavigationView()
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        if (!isFirstTime && !isAlreadyStarted) {
            findNavController(R.id.navHostFragment).navigate(R.id.action_global_home)
        }
        isAlreadyStarted = true
    }

    private fun mainActivitySetup() {
        //viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))[SharedViewModel::class.java]
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[SharedViewModel::class.java]

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

        var d = 0
        navController.addOnDestinationChangedListener { _, destination, _ ->
            d = destination.id
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
            if(d != R.id.brewFragment)
                navController.navigate(R.id.action_global_brew)
        }
    }

    private fun isUsernameIn() : Boolean {
        val sharedPreferences = this.getSharedPreferences("com.example.beer2beer", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")!!
        return username.isNotEmpty()
    }

    override fun onDialogIngredientSaveClick(name: String, quantity: Double) {
        viewModel.updateIngredient(name, quantity)
    }

    override fun onDialogRecipeInstanceSaveClick(recipeId: Int, note: String, quantity: Double) {
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val today = now.format(formatter)

        val recipeInstance = RecipeInstance(0, recipeId, today, note, quantity)

        if (!viewModel.createRecipeInstance(recipeInstance))
            Toast.makeText(applicationContext, resources.getString(R.string.overMaxCapacityError), Toast.LENGTH_LONG).show()
    }

    override fun onEditRecipeInstance(instanceId: Int, newDescription: String) {
        viewModel.updateRecipeInstance(instanceId, newDescription)
    }
}