package com.example.test02.bonus

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.widget.Toast
import com.example.test02.game.Balle
import com.example.test02.game.DrawingView

abstract class SpecialObject (x: Float, y: Float, diametre: Float, var isGentle : Boolean){
    // Special objects activator hitbox
    private val paint = Paint()
    var colorBonus = Color.GREEN
    var colorMalus = Color.RED
    val hitbox = RectF(x, y, x + diametre, y + diametre)

    // Gestion des Timer et de l'affichage des bonus/malus
    var onScreen = false // Est-ce qu'on dessine le bonus/malus

    var isShowing = false // Est-ce qu'on fait apparaître l'activable
    var showTimer = 10.0
    var showingTimeLeft = showTimer

    var isPlaying = false // Est-ce que le bonus/malus est actif
    var playTimer = 5.0
    var playingTimeLeft = playTimer

    init {}

    fun draw(canvas: Canvas?) {
        if (isGentle) paint.color = colorBonus else paint.color = colorMalus // Color choosing, function of Bonus/Malus
        canvas?.drawOval(hitbox, paint)
    }

    fun launchObject() { // Permet de faire apparaître un bonus
        showingTimeLeft = showTimer
        setPlayBonus(false)
        setShowBonus(true)
        setDrawBonus(true)
    }
    fun resetObject() { // Permet de reset les objets (Pour le moment c'est le désactiver totalement)
        setPlayBonus(false)
        setShowBonus(false)
        setDrawBonus(false)
    }
    fun setPlayBonus(wePlay: Boolean) { // Gestion du chrono pendant que le bonus est actif
        isPlaying = wePlay
    }
    fun setShowBonus(weShow: Boolean) { // Gestion du chrono quand le bonus n'est pas encore actif
        isShowing = weShow
    }
    fun setDrawBonus(weDraw: Boolean) { // Permet de faire apparaitre ou non le bonus/malus
        onScreen = weDraw
    }


    fun updateTimerShowing(elapsedTimeMS: Double, drawingView: DrawingView) { // Timer pendant lequel on peut récupérer le bonus
        val interval = elapsedTimeMS / 1000.0
        showingTimeLeft -= interval
        if (showingTimeLeft <= 0.0) {
            resetObject()
        }
    }
    fun updateTimerPlaying(elapsedTimeMS: Double, drawingView: DrawingView) { // Timer pendant lequel le bonus est actif
        val interval = elapsedTimeMS / 1000.0
        playingTimeLeft -= interval
        if (playingTimeLeft <= 0.0) {
            resetObject()
        }
    }

    open fun gereBalle(b: Balle, view: DrawingView, i: SpecialObject) {
        if (onScreen && isShowing) {if (RectF.intersects(hitbox, b.hitbox)) Activate(b, view)}
    }

    fun returnNormal(bonus: SpecialObject, view: DrawingView) {
        if (!bonus.isPlaying){
            if (bonus is SizeModifier) {
                view.balle.changeTaille(0)
            } else if (bonus is DoublePoints) {
                for (p in view.lesParois) {
                    p.doublePoint = false
                }
            }
//        } else if() {
//            // TODO
//        }
        }
    }

    open fun Activate(b: Balle, view: DrawingView) {
        playingTimeLeft = playTimer
        isPlaying = true // Démarre le bonus/malus
        onScreen = false // Arrête de dessiner le bonus
        isShowing = false // Arrête l'activabilité
    }
}