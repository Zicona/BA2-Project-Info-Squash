package com.example.squash00

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class Paroi (x1: Float, y1: Float, x2: Float, y2: Float,var view: DrawingView) {
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

                    //if (b.current_center_y < /**/view.screenHeight / 2) { // si paroi du haut  | autre manière ? if(this == view.lesParois[0]){
                    if(this == view.lesParois[0]){
                        //Score
                        view.score += 1
                    }
                } else {
                    b.changeDirection(false)
                }
            }
        }


        // si la balle sort du terrain
        if (b.current_center_y > view.screenHeight){
            b.isOnScreen = false

        }
    }
}