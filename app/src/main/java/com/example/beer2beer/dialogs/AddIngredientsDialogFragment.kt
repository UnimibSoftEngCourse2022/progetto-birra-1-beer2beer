package com.example.beer2beer.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.beer2beer.R
import com.example.beer2beer.databinding.DialogAddIngredientsBinding

class AddIngredientsDialogFragment(private val ingredientName: String, private val ingredientQuantity: Double) : DialogFragment() {

    // Use this instance of the interface to deliver action events
    internal lateinit var listener: DialogListener
    interface DialogListener {
        fun onDialogSaveClick(name: String, quantity: Double)
    }

    // Override the Fragment.onAttach() method to instantiate the DialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = context as DialogListener
        } catch (e: ClassCastException){
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException(("$context must implement DialogListener"))
        }
    }

    private lateinit var binding: DialogAddIngredientsBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // Set up the binding object
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.dialog_add_ingredients, null, false)

        // Set up the info to display in the dialog
        binding.ingredientTextView.text = ingredientName
        binding.quantityEditText.setText(ingredientQuantity.toString())

        // Create the buttons click listeners
        binding.addButton.setOnClickListener {
            val currentValue = binding.quantityEditText.text.toString().toDouble()
            val newValue = currentValue + 1.0
            binding.quantityEditText.setText(newValue.toString())
        }

        binding.subButton.setOnClickListener {
            val currentValue = binding.quantityEditText.text.toString().toDouble()
            val newValue = currentValue - 1.0
            if (newValue >= 0.0)
                binding.quantityEditText.setText(newValue.toString())
        }

        binding.quantityEditText.addTextChangedListener { s ->
            if (s.toString().isEmpty())
                binding.quantityEditText.setText(0.0.toString())
        }

        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setView(binding.root)
                .setPositiveButton(R.string.saveDialogButton) { dialog, id ->
                    listener.onDialogSaveClick(ingredientName, binding.quantityEditText.text.toString().toDouble())
                }
                .setNegativeButton(R.string.discardDialogButton) { _, _ ->
                    dialog?.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}