package com.example.test02

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import kotlin.math.*

class Balle(x: Float, y: Float, val diametre: Float) {
    val paint = Paint()
    var color = Color.RED
    var hitbox = RectF(x, y, x + diametre, y + diametre)

    val tailleInit = 150F
    var taille = tailleInit
    val tailleMax = 50F
    val tailleMin = 250F
    val vitesseInit = 1F
    var vitesse = vitesseInit
    val modif = 1F
    var dx = 0F
    var dy = 0F

    init {}

    fun draw(canvas: Canvas?) {
        paint.color = color
        hitbox.right
        canvas?.drawOval(hitbox, paint)
    }

    fun reset(width: Float, height: Float) {
        hitbox.offsetTo(width,height)
        dx = 0F; dy = 0F
    }

    fun bouge(lesParois: Array<Paroi>, view: DrawingView) {
        hitbox.offset(5*vitesse*dx, 5*vitesse*dy)
        for (p in lesParois) {
            p.gereBalle(this, view)
        }
    }

    fun getVitesseNormed(): Float {
        return vitesse * sqrt(dx*dx+dy*dy)
    }

    fun changeDirectionParoi(x: Boolean) {
        if (x) {
            this.dy = -dy
        } else {
            this.dx = -dx
        }
        hitbox.offset((3.0F + modif)*dx, (3.0F + modif)*dy)
    }
    fun changeDirectionTouch(angle: Float) {
        dx = cos(angle); dy = sin(angle)
        hitbox.offset((3.0F + modif)*dx, (3.0F + modif)*dy)
    }
}