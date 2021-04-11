package com.example.squash00

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class Paroi (x1: Float, y1: Float, x2: Float, y2: Float,var view: DrawingView, val scoring: Boolean) {
    val hitbox = RectF(x1, y1, x2, y2)
    val paint = Paint()
    var isOnScreen = true

    fun draw(canvas: Canvas) {
        paint.color = Color.GREEN
        canvas.drawRect(hitbox, paint)
    }

    fun gereBalle(b: Balle) {

        if (isOnScreen) {
            if (RectF.intersects(hitbox, b.hitbox)) {
                if (hitbox.width() > hitbox.height()) { // paroi horizontale
                    b.changeDirection(true)

                } else {
                    b.changeDirection(false)
                }

                if (this.scoring) {
                    view.score += 1
                }
            }
        }

        // si la balle sort du terrain
        if (b.current_center_y > view.screenHeight || b.current_center_y < 125f||  //125 car interstice = 125  //sort par le bas  || sort par le haut
                b.current_center_x > view.screenWidth|| b.current_center_x<0){                                 //sort par la droite || sort par la gauche
            b.isOnScreen = false

        }
    }
}