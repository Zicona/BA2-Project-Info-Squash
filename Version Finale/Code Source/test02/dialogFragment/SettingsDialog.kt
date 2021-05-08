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
import kotlinx.android.synthetic.main.settings_dialog.*
import kotlinx.android.synthetic.main.settings_dialog.view.*

class SettingsDialog(view: DrawingView, mainActivity: MainActivity): DialogFragment() {
    /** **/
    private val drawingView = view
    private val activity = mainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.settings_dialog,container, false)

//        switch1.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked){
//                // TODO
//            } else {
//                // TODO
//            }
//        }
//        switch2.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked){
//                // TODO
//            } else {
//                // TODO
//            }
//        }
//        switch3.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked){
//                // TODO
//            } else {
//                // TODO
//            }
//        }
//        switch4.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked){
//                // TODO
//            } else {
//                // TODO
//            }
//        }
        rootView.return_settings.setOnClickListener {
            drawingView.playMenuSound()
            dismiss()
        }

        return rootView
    }

    private fun triggerWarningFragment() {
        val dialog = StopFragment(drawingView, activity, true)
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