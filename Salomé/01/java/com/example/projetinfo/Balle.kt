package com.example.projetinfo

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import java.util.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.*

class Balle(x: Float, y: Float, val diametre: Float) {
    val paint = Paint()
    var color = Color.RED
    val hitbox = RectF(x, y, x + diametre, y + diametre)

    var dx = 0F
    var dy = 0F

    init {}

    fun draw(canvas: Canvas?) {
        paint.color = color
        canvas?.drawOval(hitbox, paint)
    }

    fun reset(width: Float, height: Float) {
        hitbox.offsetTo(width,height)
        dx = 0F; dy = 0F
    }

    fun bouge(lesParois: Array<Paroi>, view: DrawingView) {
        hitbox.offset(5.0F*dx, 5.0F*dy)
        for (p in lesParois) {
            p.gereBalle(this, view)
        }
    }

    fun changeDirectionParoi(x: Boolean) {
        if (x) {
            this.dy = -dy
        } else {
            this.dx = -dx
        }
        hitbox.offset(3.0F*dx, 3.0F*dy)
    }
    fun changeDirectionTouch(angle: Float) {
        dx = cos(angle); dy = sin(angle)
        hitbox.offset(3.0F*dx, 3.0F*dy)
    }
}