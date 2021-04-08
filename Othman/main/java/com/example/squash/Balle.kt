package com.example.squash

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import java.util.*

class Balle (x1: Float, y1: Float, val diametre: Float){

    val r = RectF(x1, y1, x1 + diametre, y1 + diametre)
    val random = Random()
    val paint = Paint()
    var color = Color.argb(255, random.nextInt(256),
        random.nextInt(256), random.nextInt(256))
    var dx: Int
    var dy: Int

    var dead = false

    init {
        if (random.nextDouble() > 0.5) dx = 1 else dx = -1
        if (random.nextDouble() < 0.5) dy = 1 else dy = -1
    }

    fun draw (canvas: Canvas) {
        paint.color = color
        canvas.drawOval(r, paint)
    }

    fun changeDirection(x: Boolean) {
        if (x) {  // si x est true alors on a heurté une parois horizontale et donc on change la vitesse en y
            this.dy = -dy
        }
        else {    // si x est false alors on a heurté une parois verticale et donc on change la vitesse en x
            this.dx = -dx
        }
        r.offset(3.0F*dx, 3.0F*dy)
    }

    fun changeCouleur() {
        color = Color.argb(255, random.nextInt(256),
            random.nextInt(256), random.nextInt(256))
    }

    fun bouge(lesParois: Array<Obstacle>, lesBalles: Array<Balle>) {

        // la balle bouge d'un petit offset
        r.offset(5.0F * dx, 5.0F * dy)

        // On vérifie si la balle touche une parois à chaque mouvement
        for (p in lesParois) {
            p.gereBalle(this)  // Vérifie si la balle heurte une paroi (si oui alors sa directino est changé dans la classe parois)
        }

        // On vérifie si la balle touche une autre balle et si la balle qu'elle touche n'a pas déjà été tuée
        for (b in lesBalles) {
            if (this !== b && RectF.intersects(r, b.r) && !b.dead) { // si c'est le cas:
                b.rebondit()        // La balle qui nous a heurté rebondit et change de couleur
                b.changeCouleur()
                rebondit()          // Notre balle rebondit et change de couleur
                changeCouleur()
                break               // dés qu'on a heurté une balle, la direction change, pas besoin de vérifier si on touche d'autre balles
            }
        }


    }

    fun rebondit() {
        dx = -dx
        dy = -dy
        r.offset(3.0F*dx, 3.0F*dy)
    }
}