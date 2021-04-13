package com.example.squash00

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import kotlin.math.pow
import java.lang.Math.abs
import java.util.*
import kotlin.collections.ArrayList

class Balle(x: Float, y: Float, var diametre: Float, v:DrawingView) {

    val paint = Paint()
    var color = Color.RED
    var view = v

    // Dimension
    val maxSize = 660f // max diameter
    val minSize = 50f  // min diameter

    /**
    //DEBUG
    //Debug Bonus
    var BonusTime = 0.0
    // Debug
    var leftBy = ""
    var gauche =0f
    var droite = 0f**/

    // Y/C coordinate
    var current_center_y = 0f
    var current_center_x = 0f
    // Score
    var isOnScreen = true
    var score = view.score

    // Screen dimension
    var paroiWidth = view.paroiWidth

    var hitbox = RectF(x, y, x + diametre, y + diametre)

    //Vitesse sign
    var dx = 1f
    var dy = -1f
    var maxSpeed = diametre*(3f/4f)

    // Déplacement
    var x_move = 25f // initial move (=initial speed)
    var y_move = 25f // initial move (=initial speed)

    init {
        hitbox.offsetTo(300f,500f)
    }

    fun updateMaxSpeed(){
        maxSpeed = diametre*(3f/4f)
    }

    fun draw(canvas: Canvas?) {
        paint.color = color
        canvas?.drawOval(hitbox, paint)
    }

    fun bouge(lesParois: Array<Paroi>, lesBonus: ArrayList<SpecialObject>) {

        if (this.isOnScreen) {

            hitbox.offset(x_move*dx,y_move *dy)

            // information text
            current_center_y = hitbox.centerY()
            current_center_x = hitbox.centerX()

            // SpecialObject interactions
            for (i in lesBonus){
                i.gereBalle(this)
            }

            // Parois rebounds/score
            for (p in lesParois) {
                p.gereBalle(this,lesBonus)
            }

        }
    }

    fun changeDirection(x: Boolean) {
        if (x) {
            this.dy = -dy
        } else if(!x) {
            this.dx = -dx
        }

        if (x_move > maxSpeed){x_move = maxSpeed}
        //if(x_move < diametre + 25f){x_move = diametre + 25f} // DEBUG

        if (y_move > maxSpeed){y_move = maxSpeed}
        //if(y_move < diametre + 25f){y_move = diametre + 25f} // DEBUG


        /** DEBUG
        /*if(p == view.lesParois[view.lesParoisLength-1]) {
            if (x_move >= diametre + 25f) {
                hitbox.offset((diametre + 25f) * dx, (diametre + 25f) * dy)
            }
            if(x_move < diametre + 25f) {
                hitbox.offset(x_move * dx, y_move * dy)
            }
        }*/
        **/
    }



    fun accelere(accelerationAmplificator: Float, s: String) {

        // Multiply
        if (s == "mult") {
            if (x_move * accelerationAmplificator < maxSpeed) {
                x_move *= accelerationAmplificator
            } else {
                x_move = maxSpeed
            }

            if (y_move * accelerationAmplificator < maxSpeed) {
                y_move *= accelerationAmplificator
            } else {
                y_move = maxSpeed
            }
        }

        // Addition
        if (s == "add") {
            if (x_move + accelerationAmplificator < maxSpeed) {
                x_move += accelerationAmplificator
            } else {
                x_move = maxSpeed
            }

            if (y_move + accelerationAmplificator < maxSpeed) {
                y_move += accelerationAmplificator
            } else {
                y_move = maxSpeed
            }
        }
    }


    var old_diameter = 150f
    var difference_diameter = 0f

    // SizeUp Bonus
    var sizeUpAmplificator = 4f // > 1    // if mult is used
    var sizeIncr = 50f                    // if add is used
    //SizeDown Malus
    var sizeDownAmplificator= 0.5f // < 1 // if mult is used
    var sizeDecr = -15f                   // if add is used

    fun changeTaille(select: String, s :String, b : SpecialObject) {
        when (select) {
            "reset" -> { // Retour à la normale
                diametre = old_diameter
                hitbox.right = hitbox.left + diametre
                hitbox.bottom = hitbox.top + diametre
            }
            "progressive" -> { // diminuer la taille, augmentation de difficulté progressive
                // TODO
            }
            "Incr" -> { // Augmenter la taille (bonus)

                when (s) {
                    "mult" ->{ // Augmenter par une multiplication
                        if (diametre * sizeUpAmplificator < maxSize) {

                            // new diameter
                            diametre = old_diameter * sizeUpAmplificator
                            // adds the difference between old and new diameter
                            difference_diameter = diametre - old_diameter
                            // Increases ball diameter
                            hitbox.right += difference_diameter
                            hitbox.bottom += difference_diameter

                            updateMaxSpeed()

                        }
                    }
                    "add" -> { // Augmenter par une addition
                        if(diametre + sizeIncr <maxSize){
                            // new diameter
                            diametre = old_diameter + sizeIncr
                            // adds the difference between old and new diameter
                            difference_diameter = diametre - old_diameter
                            // Increases ball diameter
                            hitbox.right += difference_diameter
                            hitbox.bottom += difference_diameter
                            updateMaxSpeed()
                        }
                    }
                }
                b.isOnScreen = false

            }
            "Decr" -> { // Diminuer la taille (bonus)

                when (s) {
                    "mult" ->{ // diminuer par une multiplication (division)
                        if (diametre * sizeDownAmplificator > minSize) {

                            // new diameter
                            diametre = old_diameter * sizeDownAmplificator
                            // adds the difference between old and new diameter
                            difference_diameter = old_diameter- diametre
                            // Increases ball diameter
                            hitbox.right -= difference_diameter
                            hitbox.bottom -= difference_diameter

                            updateMaxSpeed()

                        }
                    }
                    "add" -> { // augmenter par une addition (soustraction)
                        if (diametre + sizeDecr > minSize) {

                            // new diameter
                            diametre = old_diameter + sizeDecr
                            // adds the difference between old and new diameter
                            difference_diameter = old_diameter - diametre
                            // Increases ball diameter
                            hitbox.right -= difference_diameter
                            hitbox.bottom -= difference_diameter

                            updateMaxSpeed()

                        }
                    }
                }

                b.isOnScreen = false
            }
        }
    }

}