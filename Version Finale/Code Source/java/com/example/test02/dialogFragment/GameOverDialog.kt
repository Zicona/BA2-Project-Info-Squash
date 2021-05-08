package com.example.test02.dialogFragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.test02.game.DrawingView
import com.example.test02.game.MainActivity
import kotlin.system.exitProcess

class GameOverDialog(drawer: DrawingView, mainActivity: MainActivity) : DialogFragment() {
    /** Objectif : S'assurer que l'utilisateur veut bien quitter l'application
     * Argument : Reçoit le drawingView
     * Retour : Pop-up avec deux choix, soit quitter, soit annuler la dernière manipulation **/

    val drawingView = drawer // permet d'importer le drawingView (La méthode standard ne fonctionne pas avec les Fragments apparement)
    val activity = mainActivity

    override fun onCreateDialog(bundle: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Game Over")
        builder.setMessage(
                "Vous avez perdu...?"
        )
        builder.setPositiveButton("Nouvelle Partie") { _, _ ->
            drawingView.playMenuSound()
            drawingView.drawing = true
            drawingView.newGame(drawingView.currentDifficulty)
        }
        builder.setNeutralButton("Quitter") { _,_ ->
            drawingView.playMenuSound()
            triggerWarningFragment()
        }
        builder.setNegativeButton("Retour au Menu") { _, _ ->
            drawingView.playMenuSound()
            triggerMenuDialog()
        }
        return builder.create()
    }

    private fun triggerWarningFragment() {
        val dialog = StopFragment(drawingView, activity, false)
        dialog.show(activity.supportFragmentManager, "stopDialog")
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