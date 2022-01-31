package com.example.beer2beer.dialogs

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.beer2beer.R
import com.example.beer2beer.database.entities.RecipeInstance
import com.example.beer2beer.databinding.DialogEditRecipeInstanceBinding
import java.lang.ClassCastException

class EditRecipeInstanceDialogFragment(private val instance: RecipeInstance) : DialogFragment() {
    //Use this instance of the interface to deliver action events
    internal lateinit var listener: EditRecipeInstanceDialogListener

    interface EditRecipeInstanceDialogListener {
        fun onEditRecipeInstance(instanceId: Int, newDescription: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Verify that the host activity implements the callback interface
        try {
            listener = context as EditRecipeInstanceDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException(("$context must implement DialogListener"))
        }
    }

    private lateinit var binding: DialogEditRecipeInstanceBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // Set up the binding object
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.dialog_edit_recipe_instance,
            null,
            false
        )

        binding.editTextTextMultiLine.setText(instance.note)

        return activity?.let {
            val builder = AlertDialog.Builder(it, R.style.CustomAlertDialog)

            builder.setView(binding.root)
                .setPositiveButton(R.string.save) { _, _ ->
                    val newDescription = binding.editTextTextMultiLine.text.toString()
                    listener.onEditRecipeInstance(instance.id, newDescription)
                }
                .setNegativeButton(R.string.discard) { _, _ ->
                    dialog?.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}