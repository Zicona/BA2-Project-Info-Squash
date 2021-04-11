package com.example.squash00

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

abstract class SpecialObject (var x: Float, var y: Float,var diametre: Float, var isGentle : Boolean){

    val paint = Paint()
    var color = Color.BLACK
    val hitbox = RectF(x, y, x + diametre, y + diametre)

    // Score
    var isOnScreen = true


    init {
        if (isGentle){color = Color.CYAN} else{color = Color.RED} // SpecialObject color depending on malus/bonus

    }


    fun draw(canvas: Canvas?) {
        paint.color = color
        canvas?.drawOval(hitbox, paint)
    }

    open fun Activate(b:Balle){
    }

    open fun gereBalle(b:Balle){

    }
}
