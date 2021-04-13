package com.example.squash00

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

abstract class SpecialObject (var x: Float, var y: Float,var diametre: Float, var isGentle : Boolean){

    val paint = Paint()
    var color = Color.BLACK
    val hitbox = RectF(x, y, x + diametre, y + diametre)

    // hasTimer means that the bonus will be running for a definite amount of time (false by default)
    var hasTimer = false
    // If the bonus hasTimer --> bonusOn = true while bonus is still running (timeLeft >0)
    var bonusOn = false

    var timeLeft = 20.0
    var bonusTimeLeft = 0.0 // Time while bonus is active
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

    fun updateBonusTimer(elapsedTimeMS: Double){
        val interval = elapsedTimeMS / 1000.0
        bonusTimeLeft -= interval
        if(bonusTimeLeft <=0){
            bonusOn = false
        }
    }

    fun updateTimer(elapsedTimeMS: Double) {
        val interval = elapsedTimeMS / 1000.0
        timeLeft -= interval
        if (timeLeft <= 0.0) {
            isOnScreen = false
        }
    }
}
