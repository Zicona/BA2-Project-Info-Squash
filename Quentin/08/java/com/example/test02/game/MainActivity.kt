package com.example.test02.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.test02.R
import com.example.test02.dialogFragment.PauseDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide() // Sert à cacher la barre de titre dans l'application
        setContentView(R.layout.activity_main)

        button.setOnClickListener(this)
//        on_off.setOnClickListener {
//            drawingView.invalidate()
//            // Hide/Show bottom paroi
//            drawingView.lesParois[2].onScreen = !drawingView.lesParois[2].onScreen
//            drawingView.lesParois[1].onScreen = !drawingView.lesParois[1].onScreen
//            drawingView.lesParois[0].onScreen = !drawingView.lesParois[0].onScreen
//            drawingView.lesParois[3].onScreen = !drawingView.lesParois[3].onScreen
//        }
    }
    override fun onClick(v: View){
        onPause()
        // Prévient de la mise en pause de la partie
        Toast.makeText(v.context,"Partie mise en pause!", Toast.LENGTH_SHORT).show()
        // Lance un DialogFragment customisé de pause ("PauseDialog")
        val dialog = PauseDialog(drawingView, this)
        dialog.isCancelable = true
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