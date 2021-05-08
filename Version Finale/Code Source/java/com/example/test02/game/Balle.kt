package com.example.test02.game

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.example.test02.bonus.SizeModifier
import com.example.test02.bonus.SpecialObject
import kotlinx.android.synthetic.main.activity_main.*
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
    var asChanged = false

    val minSize = 50F
    val maxSize = 300F
    var initSpeed = 1F
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

    fun reset(width: Float, height: Float, view: DrawingView) {
        hitbox.offsetTo(width, height)
        changeTaille(0)
        for (i in view.lesBonus) {
            if (i is SizeModifier) {
                if (i.isGentle) i.mustChangeBonus = false else i.mustChangeMalus = false
            }
        }
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
            if (!i.isPlaying || !i.isActive) i.returnNormal(view, lesBonus)
        }
    }

    fun getVitesseNormed(): Float {
        return speed * sqrt(dx * dx + dy * dy)
    }

    fun gereDirectionBalle(x: Float){
        var change = true
        val precision = 50 // Nombre de section dans la balle, entier impair
        val fov = 120*(PI/180) // Champ de vision en RADIAN, double à cause de PI
        val depart = -30*(PI/180) // Départ du champ de vision (à droite) (Attention: Y vers le bas)
        // -30 pour aller vers l'avant, 150 pour aller vers l'arrière
        val x0 = hitbox.right - hitbox.left
        val xStep = x0/precision
        val angleStep = fov/precision

        for (i in 0 until precision)
            if (x <= hitbox.left + i * xStep && change) {
                changeDirectionTouch((depart - i * angleStep).toFloat())
                change = false
            }
    }

    fun gereVitesseBalle(view: DrawingView, sign: String, value: Float) {
        when(sign) {
            "+" -> {speed = initSpeed + view.score*value}
            "-" -> {speed = initSpeed - view.score*value}
        }
    }
    fun changeTaille(select: Int): Boolean {
        var asChanged = false
        when (select) {
            0 -> { // Retour à la normale
                diametre = initSize
                hitbox.right = hitbox.left + diametre
                hitbox.bottom = hitbox.top + diametre
                asChanged = true
            }
            1 -> { // Augmenter la taille (bonus)
                if (diametre + sizeUpAdd <= maxSize) {
                    diametre += sizeUpAdd
                    hitbox.right += sizeUpAdd
                    hitbox.bottom += sizeUpAdd
                    asChanged = true
                }
            }
            2 -> { // Diminuer la taille (bonus)
                if (diametre - sizeDownAdd >= minSize) {
                    diametre -= sizeDownAdd
                    hitbox.right -= sizeDownAdd
                    hitbox.bottom -= sizeDownAdd
                    asChanged = true
                }
            }
            3 -> { // Perdre le bonus
                if (diametre - sizeUpAdd >= minSize) {
                    diametre -= sizeUpAdd
                    hitbox.right -= sizeUpAdd
                    hitbox.bottom -= sizeUpAdd
                }
            }
            4 -> { // Perdre le malus
                if (diametre + sizeDownAdd <= maxSize) {
                    diametre += sizeDownAdd
                    hitbox.right += sizeDownAdd
                    hitbox.bottom += sizeDownAdd
                }
            }
        }
        return asChanged
    }

    fun changeDirectionParoi(x: Boolean) {
        if (x) {
            this.dy = -dy
        } else {
            this.dx = -dx
        }
        hitbox.offset((3.0F + modif*speed) * dx, (3.0F + modif*speed) * dy)
    }
    fun changeDirectionTouch(angle: Float) {
        dx = cos(angle); dy = sin(angle)
        hitbox.offset((3.0F + modif) * dx, (3.0F + modif) * dy)
    }
}
