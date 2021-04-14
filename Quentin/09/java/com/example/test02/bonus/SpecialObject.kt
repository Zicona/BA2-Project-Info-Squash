package com.example.test02.bonus

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.example.test02.R
import com.example.test02.game.Balle
import com.example.test02.game.DrawingView

abstract class SpecialObject (x: Float, y: Float, diametre: Float, var isGentle : Boolean){
    // Special objects activator hitbox
    private val paint = Paint()
    var colorBonus = Color.GREEN
    var colorMalus = Color.RED
    val hitbox = RectF(x, y, x + diametre, y + diametre)

    // Should the code draw it or not?
    var onScreen = false

    // Timer of the bonus
    var chrono = false
    var timer = 10.0
    var timeLeft = timer

    init {}

    fun draw(canvas: Canvas?) {
        if (isGentle) paint.color = colorBonus else paint.color = colorMalus // Color choosing, function of Bonus/Malus
        canvas?.drawOval(hitbox, paint)
    }

    fun launchObject() {
        timeLeft = timer
        setPlayBonus(true)
        setShowBonus(true)
    }
    fun resetObject() { // Permet de reset les objets
        timeLeft = timer
        setPlayBonus(false)
        setShowBonus(false)
    }
    fun setPlayBonus(wePlay: Boolean) { // Permet de mettre le chrono en pause ou de l'allumer
        chrono = wePlay
    }
    fun setShowBonus(weShow: Boolean) { // Permet de faire apparaitre ou non le bonus/malus
        onScreen = weShow
    }


    fun updateTimer(elapsedTimeMS: Double, drawingView: DrawingView) {
        val interval = elapsedTimeMS / 1000.0
        timeLeft -= interval
        if (timeLeft <= 0.0) {
            onScreen = false
        }
    }

    fun gereBalle(b: Balle) {
        if (onScreen){
            if (RectF.intersects(hitbox, b.hitbox)) {
                Activate(b)
                //isOnScreen = false // Meme si le bonus n'est pas appliqué (Ex: > maxSize) le bonus disparait
            }

        }
    }

    open fun Activate(b: Balle) {}
}