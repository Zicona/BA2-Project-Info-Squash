package com.example.squash03.bonus

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.example.squash03.game.Balle
import com.example.squash03.game.DrawingView

abstract class SpecialObject (x: Float, y: Float, diametre: Float, var isGentle : Boolean){
    // Special objects activator hitbox
    private val paint = Paint()
    var colorBonus = Color.GREEN
    var colorMalus = Color.RED
    val hitbox = RectF(x, y, x + diametre, y + diametre)

    /** GESTION PAR LES PARAMÈTRES, est-ce que ce bonus/malus peut apparaître? **/
    var isActive = true // Gère l'effet du switch dans les options
    var byPass = false // Permet le byPass d'une option en cas d'envie
    /** Exemple :
     *  Le bonus SafeWall quand il est désactivé (isActive = false) fait disparaître le mur du bas
     *  donc "byPass" peut contrer cela en disant que même si le bonus SafeWall est inactif,
     *  le mur du bas doit quand même apparaître.
     * **/

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
    open fun updateTimerPlaying(elapsedTimeMS: Double, drawingView: DrawingView) { // Timer pendant lequel le bonus est actif
        val interval = elapsedTimeMS / 1000.0
        playingTimeLeft -= interval
        if (playingTimeLeft <= 0.0) {
            resetObject()
        }
    }

    open fun gereBalle(b: Balle, view: DrawingView, i: SpecialObject) {
        if (onScreen && isShowing) {if (RectF.intersects(hitbox, b.hitbox)) Activate(b, view)}
    }

    open fun Activate(b: Balle, view: DrawingView) {
        playingTimeLeft = playTimer
        isPlaying = true // Démarre le bonus/malus
        onScreen = false // Arrête de dessiner le bonus
        isShowing = false // Arrête l'activabilité
    }

    fun returnNormal(bonus: SpecialObject, view: DrawingView) {
        when(bonus) {
            is SizeModifier -> {view.balle.changeTaille(0)} // Peut-être couper en deux (Bonus/Malus) pour la gestion par les paramètres
            is DoublePoints -> {for (p in view.lesParois) p.doublePoint = byPass}
            is SafeWall -> {view.lesParois[2].onScreen = bonus.byPass}
        }
    }

}