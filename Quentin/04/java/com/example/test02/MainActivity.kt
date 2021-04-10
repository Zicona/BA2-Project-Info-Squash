package com.example.test02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide() // Sert à cacher la barre de titre dans l'application
        setContentView(R.layout.activity_main)

        button.setOnClickListener(this)
    }
    override fun onClick(v: View){
        onPause()
        Toast.makeText(v.context,"Partie mise en pause!", Toast.LENGTH_SHORT).show()
        drawingView.showGameOverDialog()
    }
    override fun onPause() {
        super.onPause()
        drawingView.pause()
    }
    override fun onResume() {
        super.onResume()
        drawingView.resume()
    }
}