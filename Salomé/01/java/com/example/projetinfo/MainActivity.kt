package com.example.projetinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide() // Sert à cacher la barre de titre dans l'application
        setContentView(R.layout.activity_main)
        button.setOnClickListener(this)
        val dialog = MenuDialog(drawingView, this)
        dialog.isCancelable = false
        dialog.show(supportFragmentManager, "menuDialog")
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
//        button.performClick()
        drawingView.resume()
    }
}