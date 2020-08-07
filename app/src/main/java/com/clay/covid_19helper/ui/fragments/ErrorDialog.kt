package com.clay.covid_19helper.ui.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.clay.covid_19helper.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ErrorDialog : DialogFragment() {

    private var yesListener: (() -> Unit)? = null

    fun setYesListener(listener: () -> Unit) {
        yesListener = listener
    }

    private var noListener: (() -> Unit)? = null

    fun setNoListener(listener: () -> Unit) {
        noListener = listener
    }

    var title: String = ""
    var message: String = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
            .setTitle(title)
            .setMessage(message)
            .setIcon(R.drawable.ic_warning)
            .setPositiveButton("Retry") { _, _ ->
                yesListener?.let { yes ->
                    yes()
                }
            }
            .setNegativeButton("Exit") { dialogInterface, _ ->
                noListener?.let { no ->
                    no()
                }
            }.create()
    }
}