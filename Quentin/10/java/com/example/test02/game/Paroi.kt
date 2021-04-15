package com.example.test02.game

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.widget.Toast
import com.example.test02.bonus.DoublePoints
import com.example.test02.bonus.SpecialObject
import com.example.test02.game.Balle
import com.example.test02.game.DrawingView

open class Paroi (x1: Float, y1: Float, x2: Float, y2: Float, val scoring: Boolean) {
    val hitbox = RectF(x1, y1, x2, y2)
    val paint = Paint()

    var onScreen = true
    var doublePoint = false

    open fun draw(canvas: Canvas) {
        paint.color = Color.GRAY
        canvas.drawRect(hitbox, paint)
    }

    fun gereBalle(b: Balle, view: DrawingView, lesBonus: ArrayList<SpecialObject>) {
        if (RectF.intersects(hitbox,b.hitbox) and onScreen) {
            if (hitbox.width() > hitbox.height()) {
                b.changeDirectionParoi(true)
            }
            else {
                b.changeDirectionParoi(false)
            }
            if (this.scoring) {
                for (i in lesBonus) if (i is DoublePoints && i.isPlaying) doublePoint = true // Gestion du bonus DoublePoint
                if (doublePoint) {view.score += 2} else view.score += 1
                view.firstApparition = true // Permet l'apparition d'un bonus
            }
        } else {
            if (b.currentY > view.canvas.height + b.size        // Sort par en bas
                    || b.currentX > view.canvas.width + b.size  // Sort par la droite
                    || b.currentX < 0 - b.size                  // Sort par la gauche
            ) {
                b.onScreen = false
            } else if (b.currentY < 0 - b.size) {               // Sort par le haut, bug on reset la balle
                val w = view.canvas.width.toFloat()
                val h = view.canvas.height.toFloat()
                b.reset(w/2 - b.size/2, h/2 - b.size/2)
            }
        }
    }
}