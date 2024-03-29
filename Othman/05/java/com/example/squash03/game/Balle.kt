package com.example.squash03.game

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.example.squash03.bonus.SpecialObject
import kotlin.math.*

class Balle(x: Float, y: Float, var diametre: Float) {
    // Ball creation
    val paint = Paint()
    var color = Color.BLUE
    var hitbox = RectF(x, y, x + diametre, y + diametre)
    var onScreen = true

    // Difficulty variables
    val initSize = 150F
    var size = initSize
    val minSize = 50F
    val maxSize = 650F
    val initSpeed = 3f//1F
    var speed = initSpeed

    // SizeUp Bonus
    var sizeUpAmplificator = 4f // > 1
    var sizeUpAdd = 100F

    //SizeDown Malus
    var sizeDownAmplificator = 0.5f // < 1
    var sizeDownAdd = 50F

    // Size modifying
    private var oldDiameter = 0F
    private var differenceDiameter = 0F

    // Mouvement/Position variables
    val modif = 1F
    var dx = 0F
    var dy = 0F
    var currentX = 0F
    var currentY = 0F

    init {
    }

    fun draw(canvas: Canvas?) {
        paint.color = color
        hitbox.right
        canvas?.drawOval(hitbox, paint)
    }

    fun reset(width: Float, height: Float) {
        hitbox.offsetTo(width, height)
        changeTaille(0)
        dx = 0F; dy = 0F
        setShowBalle(true)
    }

    fun setShowBalle(weShow: Boolean) { // Permet de faire apparaitre ou non le bonus/malus
        onScreen = weShow
    }

    fun bouge(lesParois: Array<Paroi>, lesBonus: ArrayList<SpecialObject>, view: DrawingView) {
        // Movement and update position variable
        hitbox.offset(5 * speed * dx, 5 * speed * dy)
        currentX = hitbox.centerX()
        currentY = hitbox.centerY()

        // Checking all dependencies
        for (p in lesParois) {
            p.gereBalle(this, view, lesBonus)
        }
        for (i in lesBonus) {
            i.gereBalle(this, view, i)
            if (!i.isPlaying || !i.isActive) i.returnNormal(i, view)
        }
    }

    fun getVitesseNormed(): Float {
        return speed * sqrt(dx * dx + dy * dy)
    }

    fun changeTaille(select: Int) {
        when (select) {
            0 -> { // Retour à la normale
                diametre = initSize
                hitbox.right = hitbox.left + diametre
                hitbox.bottom = hitbox.top + diametre
            }
            1 -> { // diminuer la taille, augmentation de difficulté progressive
                // TODO
            }
            2 -> { // Augmenter la taille (bonus)
                if (diametre * sizeUpAmplificator <= maxSize) {
                    diametre += sizeUpAdd
                    hitbox.right += sizeUpAdd
                    hitbox.bottom += sizeUpAdd
                }
            }
            3 -> { // Diminuer la taille (bonus)
                if (diametre * sizeDownAmplificator >= minSize) {
                    diametre -= sizeDownAdd
                    hitbox.right -= sizeDownAdd
                    hitbox.bottom -= sizeDownAdd
                }
            }
        }
    }

    fun changeDirectionParoi(x: Boolean) {
        if (x) {
            this.dy = -dy
        } else {
            this.dx = -dx
        }
        hitbox.offset((3.0F + modif) * dx, (3.0F + modif) * dy)
    }

    fun changeDirectionTouch(angle: Float) {
        dx = cos(angle); dy = sin(angle)
        hitbox.offset((3.0F + modif) * dx, (3.0F + modif) * dy)
    }

    fun changeDirectionRaquette(x: Boolean) {
        if (x) {
            this.dy = -dy
        } else {
            this.dx = -dx
        }
        hitbox.offset((120) * dx, (120) * dy)
    }
}