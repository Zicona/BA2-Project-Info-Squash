package com.example.test02.dialogFragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.DialogFragment
import com.example.test02.game.DrawingView
import com.example.test02.game.MainActivity
import kotlin.system.exitProcess

class StopFragment(drawer: DrawingView, mainActivity: MainActivity, dismiss: Boolean) : DialogFragment() {
    /** Objectif : S'assurer que l'utilisateur veut bien quitter l'application
    * Argument : Reçoit le drawingView
    * Retour : Pop-up avec deux choix, soit quitter, soit annuler la dernière manipulation **/

    private val drawingView = drawer // permet d'importer le drawingView (La méthode standard ne fonctionne pas avec les Fragments apparement)
    private val activity = mainActivity
    private val weDismiss = dismiss

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
            if (weDismiss) {
                dismiss()
            } else {
                triggerMenuDialog()
            }
        }
        return builder.create()
    }

    private fun triggerMenuDialog() {
        val dialog = MenuDialog(drawingView, activity)
        dialog.isCancelable = false
        dialog.show(activity.supportFragmentManager, "menuDialog")
    }

    override fun onPause() {
        super.onPause()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
    override fun onDestroy() {
        super.onDestroy()
        if (weDismiss) {
            dismiss()
        } else {
            triggerMenuDialog()
        }
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