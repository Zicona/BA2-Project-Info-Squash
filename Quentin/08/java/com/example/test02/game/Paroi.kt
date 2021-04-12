package com.example.test02.game

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import com.example.test02.game.Balle
import com.example.test02.game.DrawingView

class Paroi (x1: Float, y1: Float, x2: Float, y2: Float, val scoring: Boolean) {
    private val hitbox = RectF(x1, y1, x2, y2)
    private val paint = Paint()

    var onScreen = true

    fun draw(canvas: Canvas) {
        paint.color = Color.GREEN
        canvas.drawRect(hitbox, paint)
    }

    fun gereBalle(b: Balle, view: DrawingView) {
        if (RectF.intersects(hitbox,b.hitbox)) {
            if (hitbox.width() > hitbox.height()) {
                b.changeDirectionParoi(true)
            }
            else {
                b.changeDirectionParoi(false)
            }
            if (this.scoring) {
                view.score += 1
            }
        } else {
            if (b.currentY > view.canvas.height) {
                b.onScreen = false
            }
        }
    }
}