package com.example.squash03.bonus

import android.graphics.Canvas
import android.graphics.Color
import com.example.squash03.game.DrawingView
import com.example.squash03.game.Paroi

class Obstacle(x1: Float, y1: Float, x2:Float, y2:Float, view: DrawingView, scoring:Boolean = false) : Paroi(x1, y1, x2, y2, scoring) {

    // Dimensions

    init {
        onScreen = false
        paint.color = Color.LTGRAY
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRect(hitbox, paint)
    }
}