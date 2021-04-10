package com.example.squash00

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.security.AccessController.getContext
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    val random = Random()
    lateinit var drawingView: DrawingView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // cf. PetitBillard pour voir ce que j'ai retiré

        button.setOnClickListener(this)

        drawingView = findViewById<DrawingView>(R.id.drawingView)




    }
    override fun onClick(v: View){
        drawingView.invalidate()

        // Hide/Show bottom paroi
        drawingView.lesParois[2].isOnScreen = !drawingView.lesParois[2].isOnScreen
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