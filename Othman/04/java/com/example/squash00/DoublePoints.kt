package com.example.squash00

import android.graphics.RectF

class DoublePoints(x: Float,y: Float,diametre: Float,isGentle: Boolean = true) : SpecialObject(x,y,diametre, isGentle) {


    init {
        hasTimer = true
        bonusTimeLeft = 10.0
    }



    override fun Activate(b: Balle) {
        bonusOn = true
    }

    override fun gereBalle(b: Balle) {
        if (isOnScreen){
            if (RectF.intersects(hitbox, b.hitbox)) {
                Activate(b)
                isOnScreen = false // Meme si le bonus n'est pas appliqué (Ex: > maxSize) le bonus disparait au contact
            }

        }
        /** DEBUG
        b.BonusTime = bonusTimeLeft**/
    }
}