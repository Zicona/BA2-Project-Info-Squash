package com.example.test02

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import java.util.*
import kotlinx.android.synthetic.main.activity_main.*

class Balle(x: Float, y: Float, val diametre: Float) {
    val paint = Paint()
    var color = Color.RED
    val hitbox = RectF(x, y, x + diametre, y + diametre)
    var start = false


    var dx = 0
    var dy = 1

    init {}

    fun draw(canvas: Canvas?) {
        paint.color = color
        canvas?.drawOval(hitbox, paint)
    }

    fun reset(width: Float, height: Float) {
        hitbox.offsetTo(width,height)
        dx = 0; dy = 0
    }

    fun bouge(lesParois: Array<Paroi>) {
        hitbox.offset(5.0F*dx, 5.0F*dy)
        for (p in lesParois) {
            p.gereBalle(this)
        }
    }

    fun changeDirection(x: Boolean, side: Int) {
        if (x) {
            when(side) {
                1   -> {this.dy = -dy} // bypass pour les parois
                -60 -> {} // 60° gauche
                -45 -> {dx = -1; dy = -1} // 45° gauche
                -30 -> {} // 30° gauche
                -15 -> {} // 15° gauche
                0   -> {dx = 0; dy = -1} // 0° milieu
                15  -> {} // 15° droite
                30  -> {} // 30° droite
                45  -> {dx = 1; dy = -1} // 45° droite
                60  -> {} // 60° droite
            }
        } else {
            when(side) {
                1 -> {this.dx = -dx} // bypass pour les parois
            }

        }
        hitbox.offset(3.0F*dx, 3.0F*dy)
    }
}