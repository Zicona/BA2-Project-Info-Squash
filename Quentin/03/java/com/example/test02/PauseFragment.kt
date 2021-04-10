package com.example.test02

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity

class PauseFragment(fragment: FragmentActivity, drawer: DrawingView): DialogFragment() {
    val activityFragment = fragment // permet d'importer le fragment qu'on utilise dans le drawingView
    val drawingView = drawer // permet d'importer le drawingView (La méthodé standard ne fonctionne pas avec les Fragments apparement)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreate(savedInstanceState)



        val builder = AlertDialog.Builder(activity)
        builder.setTitle(resources.getString(R.string.pause))
        builder.setMessage(
                resources.getString(
                        R.string.text_pause
                )
        )
        builder.setPositiveButton(R.string.restart_game) { _, _ -> triggerStopFragment() }
        builder.setNegativeButton(R.string.reset_game) { _, _ -> resume() }

        return builder.create()
    }

    fun resume() {
        drawingView.resumeGame()
    }
    fun triggerStopFragment() {
        activityFragment.runOnUiThread(
                Runnable {
                    val manager = activityFragment.supportFragmentManager
                    val transaction = manager.beginTransaction()
                    val prev = manager.findFragmentByTag("dialog")
                    if (prev != null) {
                        transaction.remove(prev)
                    }
                    val alternative = StopFragment(drawingView)
                    // La ligne suivante pose le problème suivant:
                    // Si on décide de lancer une nouvelle partie, revient sur le Fragment
                    // "Menu de Pause" après avoir lancé une nouvelle partie
//                    transaction.addToBackStack(null) // Permet de revenir sur le fragment précédent en-cas de retour en arrière
                    alternative.show(transaction, "dialog")
                }

        )
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