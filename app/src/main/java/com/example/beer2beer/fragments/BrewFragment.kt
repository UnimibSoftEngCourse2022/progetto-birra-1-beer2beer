package com.example.beer2beer.fragments

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.beer2beer.SharedViewModel
import com.example.beer2beer.database.entities.Recipe
import com.example.beer2beer.databinding.FragmentBrewBinding
import org.apache.commons.math3.optim.MaxIter
import org.apache.commons.math3.optim.linear.*
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType

class BrewFragment : Fragment() {
    private lateinit var binding: FragmentBrewBinding
    private val viewModel: SharedViewModel by activityViewModels()

    private var bestRecipe: Recipe? = null

    private val DEFAULT_MAX_ITER = MaxIter(100)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {

        brewFragmentSetup(inflater, container)

        whatShouldIBrewToday()

        return binding.root
    }

    private fun brewFragmentSetup(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        binding = FragmentBrewBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun whatShouldIBrewToday() {
        var bestQuantity = 0.0
        val ss = SimplexSolver()

        viewModel.recipes.observe(viewLifecycleOwner) { recipes ->
            recipes.forEach { recipe ->

                viewModel.getRecipeIngredients(recipe.id)
                    .observe(viewLifecycleOwner) { recipeIngredients ->
                        val constraint = ArrayList<LinearConstraint>()
                        val coeff = DoubleArray(recipeIngredients.size + 1)

                        for (i in recipeIngredients.indices) {
                            coeff[i] = -recipeIngredients.get(i).ratio
                            val c = DoubleArray(recipeIngredients.size + 1)
                            c.fill(0.0)
                            c[i] = 1.0
                            if (recipeIngredients.get(i).name == "Water") {
                                constraint.add(
                                    LinearConstraint(
                                        c,
                                        Relationship.LEQ,
                                        recipeIngredients.get(i).quantity * 1000
                                    )
                                )
                            } else {
                                constraint.add(
                                    LinearConstraint(
                                        c,
                                        Relationship.LEQ,
                                        recipeIngredients.get(i).quantity
                                    )
                                )
                            }

                        }

                        coeff[coeff.lastIndex] = 1.0
                        constraint.add(LinearConstraint(coeff, Relationship.EQ, 0.0))

                        val c = DoubleArray(recipeIngredients.size + 1)
                        c.fill(0.0)
                        c[c.lastIndex] = 1.0

                        val fObb = LinearObjectiveFunction(c, 0.0)
                        val constr = LinearConstraintSet(constraint)

                        val solution =
                            ss.optimize(
                                DEFAULT_MAX_ITER, fObb, constr,
                                GoalType.MAXIMIZE, NonNegativeConstraint(true)
                            ).value

                        if (bestRecipe == null || solution > bestQuantity) {
                            bestRecipe = recipe
                            bestQuantity = solution
                        }
                    }
            }
        }
        executeResult()
    }

    private fun executeResult() {
        val handler = Handler()

        val runnable: Runnable = object : Runnable {
            override fun run() {
                if (bestRecipe == null) {
                    handler.post(this)
                } else {
                    Log.d("Brew", "onCreateView: $bestRecipe")

                    val action =
                        bestRecipe?.let {
                            BrewFragmentDirections.actionHomeToRecipeDetail(
                                it.id,
                                it.name,
                                it.description
                            )
                        }

                    if (action != null) {
                        findNavController().navigate(action)
                    }
                }
            }
        }
        handler.post(runnable)
    }
}