package com.example.test02

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_perso.view.*
import kotlinx.android.synthetic.main.settings_dialog.*
import kotlin.system.exitProcess

class SettingsDialog(view: DrawingView, mainActivity: MainActivity): DialogFragment() {
    /** Objectif : Mettre le jeu en pause et fournir un choix à l'utilisateur
     * Arguments : - Constructeur : Le drawingView ainsi que la mainActivity
     * Retour : Pop-up de pause avec un choix multiple, soit reprendre, soit recommencer, soit quitter
     *          + Un bouton pour les réglages
     * NOTE : Les composants graphiques se trouvent dans le dossier Ressource : res/layout/dialog_perso **/
    private val drawingView = view
    private val activity = mainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.settings_dialog,container, false)

//        switch1.setOnClickListener {
//            // TODO
//        }
//        switch2.setOnClickListener {
//            // TODO
//        }
//        switch3.setOnClickListener {
//            // TODO
//        }
//        switch4.setOnClickListener {
//            // TODO
//        }

        return rootView
    }

    private fun triggerWarningFragment() {
        val dialog = StopFragment(drawingView)
        dialog.show(activity.supportFragmentManager, "stopDialog")
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