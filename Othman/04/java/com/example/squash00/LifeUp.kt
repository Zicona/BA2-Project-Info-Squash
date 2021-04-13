package com.example.squash00

import android.graphics.RectF

class LifeUp(x: Float, y: Float, diametre: Float,isGentle: Boolean = true) : SpecialObject(x,y,diametre,isGentle) {


    override fun Activate(b: Balle) {
        b.view.life_left +=1
    }

    override fun gereBalle(b: Balle) {
        if(isOnScreen){
            if (RectF.intersects(hitbox, b.hitbox)) {
                Activate(b)
                isOnScreen = false // Meme si le bonus n'est pas appliqué (Ex: > maxSize) le bonus disparait au contact
            }

        }
    }
}