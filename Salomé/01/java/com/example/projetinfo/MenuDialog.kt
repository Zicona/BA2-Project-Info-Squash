package com.example.projetinfo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.menu_dialog.view.*
import kotlin.system.exitProcess

class MenuDialog(view: DrawingView, mainActivity: MainActivity): DialogFragment() {

    private val drawingView = view
    private val activity = mainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.menu_dialog,container, false)

        rootView.button_easy.setOnClickListener{
            drawingView.newGame()
            dismiss()
        }
        rootView.button_medium.setOnClickListener{
            drawingView.newGame()
            dismiss()
        }
        rootView.button_hard.setOnClickListener {
            drawingView.newGame()
            dismiss()
        }
//        rootView.settings.setOnClickListener {
//            triggerSettingsFragment()
//        }
        return rootView
    }

    private fun triggerWarningFragment() {
        val dialog = StopFragment(drawingView)
        dialog.show(activity.supportFragmentManager, "stopDialog")
    }

    private fun triggerSettingsFragment() {
        val dialog = SettingsDialog(drawingView, activity)
        dialog.show(activity.supportFragmentManager, "settingsDialog")
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