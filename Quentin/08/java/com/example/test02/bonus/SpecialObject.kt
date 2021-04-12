package com.example.test02.bonus

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.example.test02.game.Balle

abstract class SpecialObject (var x: Float, var y: Float,var diametre: Float, var isGentle : Boolean){
    // Special objects activator hitbox
    private val paint = Paint()
    var colorBonus = Color.BLUE
    var colorMalus = Color.MAGENTA
    val hitbox = RectF(x, y, x + diametre, y + diametre)

    // Should the code draw it or not?
    var onScreen = true

    init {}

    fun draw(canvas: Canvas?) {
        if (isGentle) paint.color = colorBonus else paint.color = colorMalus
        canvas?.drawOval(hitbox, paint)
    }

    open fun Activate(b: Balle) {}

    open fun gereBalle(b: Balle) {}
}