package com.example.squash03.bonus

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.example.squash03.game.DrawingView
import java.util.*


class Cible  (x: Float, y: Float, var diametre: Float,var view: DrawingView) {


    var random = Random()
    val paint = Paint()
    var color = Color.YELLOW

    var hitbox = RectF(x, y, x + diametre, y + diametre)

    var x_position = 0f
    var y_position = 0f


    var onScreen = true

    init {
    }



    fun draw(canvas: Canvas?) {
        paint.color = color
        canvas?.drawOval(hitbox, paint)
    }

    fun giveRandomPosition(){

        x_position = random.nextFloat()*view.canvas.width
        y_position = random.nextFloat()*view.canvas.height

        if(x_position > 0 && x_position < view.canvas.width-diametre && y_position >125f && y_position<view.canvas.height-diametre){
            hitbox.offsetTo(x_position,y_position)
        }
        else{
            giveRandomPosition()
        }
    }


    fun onClick() {
        if(onScreen) {
            view.osuCibleTouched += 1
            onScreen = false

            // Récompense de osu
            if(view.osuCibleTouched ==3){
                view.lives +=1
                view.osuCibleTouched = 0
            }
        }
    }
}