package com.example.test02

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import kotlin.system.exitProcess

class StopFragment(drawer: DrawingView) : DialogFragment() {
    val drawingView = drawer // permet d'importer le drawingView (La méthode standard ne fonctionne pas avec les Fragments apparement)
    override fun onCreateDialog(bundle: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Alternative Choice")
        builder.setMessage(
                "Do you want to stop the App or show fragment"
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
        drawingView.resumeGame() // Permet d'arrêter la pause avec un retour en arrière
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