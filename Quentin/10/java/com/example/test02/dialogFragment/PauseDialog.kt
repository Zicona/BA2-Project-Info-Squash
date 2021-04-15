package com.example.test02.dialogFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.test02.game.DrawingView
import com.example.test02.game.MainActivity
import com.example.test02.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.pause_dialog.view.*

class PauseDialog(view: DrawingView, mainActivity: MainActivity): DialogFragment() {
    /** Objectif : Mettre le jeu en pause et fournir un choix à l'utilisateur
     * Arguments : - Constructeur : Le drawingView ainsi que la mainActivity
     * Retour : Pop-up de pause avec un choix multiple, soit reprendre, soit recommencer, soit quitter
     *          + Un bouton pour les réglages
     * NOTE : Les composants graphiques se trouvent dans le dossier Ressource : res/layout/dialog_perso **/
    private val drawingView = view
    private val activity = mainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.pause_dialog,container, false)

        rootView.resume.setOnClickListener{
            drawingView.resumeGame()
            dismiss()
        }
        rootView.restart.setOnClickListener{
            drawingView.newGame()
            dismiss()
        }
        rootView.quit.setOnClickListener {
            triggerWarningFragment()
        }
        rootView.settings.setOnClickListener {
            drawingView.triggerSettingsDialog()
        }
        rootView.menu_button.setOnClickListener {
            triggerMenuDialog()
            dismiss()
        }
        return rootView
    }

    private fun triggerWarningFragment() {
        val dialog = StopFragment(drawingView, activity, true)
        dialog.show(activity.supportFragmentManager, "stopDialog")
    }

    private fun triggerSettingsFragment() {
//        val dialog = SettingsDialog(drawingView, activity)
        drawingView.settingsDialog.show(activity.supportFragmentManager, "settingsDialog")
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
//        drawingView.resumeGame() // Permet d'arrêter la pause avec un retour en arrière
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