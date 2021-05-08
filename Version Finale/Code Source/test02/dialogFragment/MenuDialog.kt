package com.example.test02.dialogFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.test02.R
import com.example.test02.game.DrawingView
import com.example.test02.game.MainActivity
import kotlinx.android.synthetic.main.menu_dialog.view.*
import kotlinx.android.synthetic.main.pause_dialog.view.*
import kotlin.system.exitProcess

class MenuDialog(view: DrawingView, mainActivity: MainActivity): DialogFragment() {

    private val drawingView = view
    private val activity = mainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.menu_dialog,container, false)

        rootView.button_easy.setOnClickListener{
            drawingView.playMenuSound()
            drawingView.newGame("Easy")
            dismiss()
        }
        rootView.button_medium.setOnClickListener{
            drawingView.playMenuSound()
            drawingView.newGame("Medium")
            dismiss()
        }
        rootView.button_hard.setOnClickListener {
            drawingView.playMenuSound()
            drawingView.newGame("Hard")
            dismiss()
        }
        rootView.settings_menu.setOnClickListener {
            drawingView.playMenuSound()
            drawingView.triggerSettingsDialog()
        }
        rootView.quit_menu.setOnClickListener {
            drawingView.playMenuSound()
            triggerWarningFragment()
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