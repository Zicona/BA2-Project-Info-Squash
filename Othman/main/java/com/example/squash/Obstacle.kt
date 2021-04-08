package com.example.squash

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class Obstacle (x1: Float, y1: Float, x2: Float, y2: Float) {


    val r = RectF(x1, y1, x2, y2)
    val paint = Paint()

    fun draw(canvas: Canvas) {
        paint.color = Color.GREEN
        canvas.drawRect(r, paint)
    }

    fun gereBalle(b: Balle) {
        if (RectF.intersects(r,b.r)) {

            if (r.width() > r.height()) { // if parois horizontal
                b.changeDirection (true)
            }
            else { // parois verticale
                b.changeDirection(false)
            }
        }
    }
}