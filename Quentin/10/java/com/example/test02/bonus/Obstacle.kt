package com.example.test02.bonus

import android.graphics.Canvas
import android.graphics.Color
import com.example.test02.game.DrawingView
import com.example.test02.game.Paroi

class Obstacle(x1: Float, y1: Float, x2:Float, y2:Float, view: DrawingView, scoring:Boolean = false) : Paroi(x1, y1, x2, y2, scoring) {

    // Dimensions

    init {
        onScreen = false
    }

    override fun draw(canvas: Canvas) {
        paint.color = Color.LTGRAY
        canvas.drawRect(hitbox, paint)
    }
}