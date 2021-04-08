package com.example.test02

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    val random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // cf. PetitBillard pour voir ce que j'ai retiré

        button.setOnClickListener(this)
    }
    override fun onClick(v: View){
        drawingView.invalidate()
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