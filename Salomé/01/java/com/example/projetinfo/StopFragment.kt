package com.example.projetinfo

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import kotlin.system.exitProcess

class StopFragment(drawer: DrawingView) : DialogFragment() {
    /** Objectif : S'assurer que l'utilisateur veut bien quitter l'application
     * Argument : Reçoit le drawingView
     * Retour : Pop-up avec deux choix, soit quitter, soit annuler la dernière manipulation **/

    val drawingView = drawer // permet d'importer le drawingView (La méthode standard ne fonctionne pas avec les Fragments apparement)

    override fun onCreateDialog(bundle: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Alerte")
        builder.setMessage(
            "Êtes-vous sûr.e de vouloir quitter?"
        )
        builder.setPositiveButton("Quitter") { dialog, which ->
            exitProcess(0)
        }
        builder.setNegativeButton("Annuler") { dialog, which ->
            dismiss()
        }
        return builder.create()
    }

    override fun onPause() {
        super.onPause()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
    override fun onDestroy() {
        super.onDestroy()
    }
    override fun onDetach() {
        super.onDetach()
    }
    override fun onStart() {
        super.onStart()
    }
    override fun onStop() {
        super.onStop()
    }
}