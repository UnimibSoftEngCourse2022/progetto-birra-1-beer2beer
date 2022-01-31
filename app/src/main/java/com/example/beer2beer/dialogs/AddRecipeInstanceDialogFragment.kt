package com.example.beer2beer.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.text.isDigitsOnly
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.beer2beer.R
import com.example.beer2beer.databinding.DialogAddRecipeInstanceBinding
import java.lang.Exception

class AddRecipeInstanceDialogFragment(val recipeId: Int) : DialogFragment() {
    // Use this instance of the interface to deliver action events
    internal lateinit var listener: AddRecipeInstanceDialogListener

    interface AddRecipeInstanceDialogListener {
        fun onDialogRecipeInstanceSaveClick(recipeId: Int, note: String, quantity: Double)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = context as AddRecipeInstanceDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException(("$context must implement DialogListener"))
        }
    }

    private lateinit var binding: DialogAddRecipeInstanceBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // Set up the binding object
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.dialog_add_recipe_instance,
            null,
            false
        )



        return activity?.let {
            val builder = AlertDialog.Builder(it, R.style.CustomAlertDialog)

            builder.setView(binding.root)
                .setPositiveButton(R.string.save) { _, _ ->
                    val quantity: Double
                    val note = binding.noteEditText.text.toString()

                    if (binding.litersEditText.text.toString().isEmpty())
                        binding.howManyLTextView.error =
                            resources.getString(R.string.litersEditTextError)
                    else {
                        quantity = binding.litersEditText.text.toString().toDouble()
                        listener.onDialogRecipeInstanceSaveClick(recipeId, note, quantity)
                    }
                }
                .setNegativeButton(R.string.discard) { _, _ ->
                    dialog?.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}