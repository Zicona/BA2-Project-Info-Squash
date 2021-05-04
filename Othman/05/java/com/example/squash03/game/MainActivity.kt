package com.example.squash03.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.squash03.R
import com.example.squash03.dialogFragment.MenuDialog
import com.example.squash03.dialogFragment.PauseDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide() // Sert à cacher la barre de titre dans l'application
        setContentView(R.layout.activity_main)

        val dialog = MenuDialog(drawingView, this)
        dialog.isCancelable = false
        dialog.show(supportFragmentManager, "menuDialog")

        button.setOnClickListener(this)
        on_off.setOnClickListener {
            drawingView.invalidate()
            // Hide/Show les différentes parois
            drawingView.lesParois[2].onScreen = !drawingView.lesParois[2].onScreen
//            drawingView.lesParois[1].onScreen = !drawingView.lesParois[1].onScreen
//            drawingView.lesParois[0].onScreen = !drawingView.lesParois[0].onScreen
//            drawingView.lesParois[3].onScreen = !drawingView.lesParois[3].onScreen
        }
    }

    fun changeLife(lives: Int){
        // TODO
    }

    override fun onClick(v: View){
        drawingView.pauseGame()
        // Lance un DialogFragment customisé de pause ("PauseDialog")
        val dialog = PauseDialog(drawingView, this)
        dialog.isCancelable = false
        dialog.show(supportFragmentManager, "pauseDialog")
    }
    override fun onPause() {
        super.onPause()
        drawingView.pause()
    }
    override fun onResume() {
        super.onResume()
        drawingView.resume()
//        button.performClick()
    }
}