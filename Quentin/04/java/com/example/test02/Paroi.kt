package com.example.test02

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class Paroi (x1: Float, y1: Float, x2: Float, y2: Float) {
    private val hitbox = RectF(x1, y1, x2, y2)
    private val paint = Paint()

    fun draw(canvas: Canvas) {
        paint.color = Color.GREEN
        canvas.drawRect(hitbox, paint)
    }

    fun gereBalle(b: Balle) {
        if (RectF.intersects(hitbox,b.hitbox)) {
            if (hitbox.width() > hitbox.height()) {
                b.changeDirectionParoi(true)
            }
            else {
                b.changeDirectionParoi(false)
            }
        }
    }
}