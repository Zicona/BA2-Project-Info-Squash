package com.example.test02

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import java.util.*

class Balle(x: Float, y: Float, val diametre: Float) {
    val paint = Paint()
    var color = Color.RED
    val hitbox = RectF(x, y, x + diametre, y + diametre)

    var dx = 0
    var dy = 1

    init {
        hitbox.offsetTo(250f,300f)
    }

    fun draw(canvas: Canvas?) {
        paint.color = color
        canvas?.drawOval(hitbox, paint)
    }

    fun bouge(lesParois: Array<Paroi>) {
        hitbox.offset(5.0F*dx, 5.0F*dy)
        for (p in lesParois) {
            p.gereBalle(this)
        }
    }

    fun changeDirection(x: Boolean) {
        if (x) {
            this.dy = -dy
        } else {
            this.dx = -dx
        }
        hitbox.offset(3.0F*dx, 3.0F*dy)
    }
}