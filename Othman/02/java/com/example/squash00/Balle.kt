package com.example.squash00

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import java.util.*
import kotlin.collections.ArrayList

class Balle(x: Float, y: Float, var diametre: Float) {
    val paint = Paint()
    var color = Color.RED

    // Dimension
    val maxSize = 500f // max diameter
    val minSize = 50f  // min diameter

    // paroi gereballe()
    var current_center_y = 0f
    var current_center_x = 0f
    // Score
    var isOnScreen = true

    var hitbox = RectF(x, y, x + diametre, y + diametre)

    var dx = 0.3f
    var dy = -1

    init {
        hitbox.offsetTo(250f,300f)
    }

    fun draw(canvas: Canvas?) {
        paint.color = color
        canvas?.drawOval(hitbox, paint)
    }

    fun bouge(lesParois: Array<Paroi>, lesBonus: ArrayList<SpecialObject>) {

        if (this.isOnScreen) {
            hitbox.offset(2 * 5.0F * dx, 2 * 5.0F * dy)

            // information text
            current_center_y = hitbox.centerY()
            current_center_x = hitbox.centerX()


            // SpecialObject interactions
            for (i in lesBonus){
                i.gereBalle(this)
            }


            // Parois rebounds/score
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