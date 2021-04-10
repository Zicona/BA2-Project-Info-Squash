package com.example.squash00

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import java.util.*

class Balle(x: Float, y: Float, val diametre: Float) {
    val paint = Paint()
    var color = Color.RED


    // paroi gereballe()
    var current_center_y = 0f
    // Score
    var isOnScreen = true

    val hitbox = RectF(x, y, x + diametre, y + diametre)

    var dx = 0.3f
    var dy = -1

    init {
        hitbox.offsetTo(250f,300f)
    }

    fun draw(canvas: Canvas?) {
        paint.color = color
        canvas?.drawOval(hitbox, paint)
    }

    fun bouge(lesParois: Array<Paroi>) {

        if (this.isOnScreen) {
            hitbox.offset(2 * 5.0F * dx, 2 * 5.0F * dy)

            // information text
            current_center_y = hitbox.centerY()

            for (p in lesParois) {
                p.gereBalle(this)
            }

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