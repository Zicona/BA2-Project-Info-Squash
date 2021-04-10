package com.example.test02

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_perso.view.*
import kotlin.system.exitProcess

class PauseDialog(view: DrawingView, mainActivity: MainActivity): DialogFragment() {
    private val drawingView = view
    private val activity = mainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.dialog_perso,container, false)

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