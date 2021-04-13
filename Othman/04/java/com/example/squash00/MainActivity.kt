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

    //lateinit var drawingView: DrawingView

    var x = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide() // Sert à cacher la barre de titre dans l'application

        button.setOnClickListener(this)
        //drawingView = findViewById<DrawingView>(R.id.drawingView)




    }
    override fun onClick(v: View){

        /** DEBUG
        // Hide/Show bottom paroi
        //drawingView.lesParois[2].isOnScreen = !drawingView.lesParois[2].isOnScreen
        //drawingView.lesParois[1].isOnScreen = !drawingView.lesParois[1].isOnScreen
        //drawingView.lesParois[0].isOnScreen = !drawingView.lesParois[0].isOnScreen
        //drawingView.lesParois[3].isOnScreen = !drawingView.lesParois[3].isOnScreen **/

        if (x == 1) {
        onPause()
        x = 2
        }
        else{
            onResume()
            x = 1
        }
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