package com.example.squash00

import android.graphics.Canvas
import android.content.Context

import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


open class Paroi (x1: Float, y1: Float, x2: Float, y2: Float,var view: DrawingView, val scoring: Boolean) {

    val hitbox = RectF(x1, y1, x2, y2)
    val paint = Paint()
    var isOnScreen = true

    // Double Points
    var doublepoints = false

    init {
        paint.color = Color.GREEN
    }


    fun draw(canvas: Canvas) {
        canvas.drawRect(hitbox, paint)
    }

     open fun gereBalle(b: Balle, lesBonus: ArrayList<SpecialObject>) {

        if (isOnScreen) {
            if (RectF.intersects(hitbox, b.hitbox)) {

                // Rebounds
                if (hitbox.width() > hitbox.height()) { // paroi horizontale
                    b.changeDirection(true)

                } else {
                    b.changeDirection(false)
                }

                // Score
                if (this.scoring) {
                    for (bonus in lesBonus) {
                        if (bonus is DoublePoints && bonus.bonusOn){
                            doublepoints = true
                        }
                    }
                    if (doublepoints) {
                        view.score += 2
                    }
                    else{view.score +=1}
                    doublepoints = false

                    // Tous les 3 points, on accélère
                    if (view.score%3 == 0){
                        b.updateMaxSpeed()
                        b.accelere(2f, "mult")
                    }
                }


            }

            /** DEBUG
            if (b.current_center_x == 1135.0f && b.current_center_y> 1300){
                //Thread.sleep(500)
            } **/

            // si la balle sort du terrain
            if (b.current_center_y > view.screenHeight + b.maxSpeed + 25f|| b.current_center_y < 125f - b.maxSpeed -25f||  //25 = epaisseur  //sort par le bas  || sort par le haut
                    b.current_center_x > view.screenWidth + b.maxSpeed + 25f|| b.current_center_x <0 - b.maxSpeed -25f){                     //sort par la droite || sort par la gauche

                /** DEBUG
                /*if (b.current_center_y > view.screenHeight + b.diametre + 25f){b.leftBy = "bas" }
                else if (b.current_center_y < 125f - b.diametre - 25f){b.leftBy = "haut"}
                else if (b.current_center_x > view.screenWidth + b.diametre + 25f){
                    b.leftBy =(b.current_center_x).toString() + "|" + (view.screenWidth + b.diametre + 25f).toString()
                }
                else {b.leftBy = "gauche"}*/ **/

                b.isOnScreen = false

            }

        }


    }

}