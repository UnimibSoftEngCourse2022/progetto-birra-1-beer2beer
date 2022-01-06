package com.example.beer2beer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.beer2beer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = activityMainBindingSetup()

        setContentView(binding.root)
    }

    private fun activityMainBindingSetup(): ActivityMainBinding {
        // Set the ViewModel to the Activity
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Setup binding object to inflate the activity
        val binding = ActivityMainBinding.inflate(layoutInflater)

        // Bind the viewModel in the layout to the viewModel class
        binding.viewModel = viewModel

        // Makes LiveData update the UI correctly
        binding.lifecycleOwner = this
        return binding
    }
}