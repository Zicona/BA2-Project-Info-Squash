package com.example.test02.game

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.example.test02.bonus.SpecialObject

open class Obstacle(x1: Float, y1: Float, x2:Float, y2:Float) {
    val hitbox = RectF(x1, y1, x2, y2)
    val paint = Paint()

    open var onScreen = false

    init {paint.color = Color.LTGRAY}

    open fun draw(canvas: Canvas) {
        canvas.drawRect(hitbox, paint)
    }
    open fun gereBalle(b: Balle, view: DrawingView, lesBonus: ArrayList<SpecialObject>) {}
}