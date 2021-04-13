package com.example.squash00

import android.graphics.RectF

class SizeModifier(x: Float,y: Float,diametre: Float,isGentle: Boolean) : SpecialObject(x,y,diametre, isGentle) {



    override fun Activate(b:Balle) {
        // SizeUp Bonus
        if(isGentle) {
            b.changeTaille("Incr","add", this)
        }
        // SizeDown Malus
        else{
            b.changeTaille("Decr","mult",this)
        }

    }



    override fun gereBalle(b: Balle) {
        if (isOnScreen){
            if (RectF.intersects(hitbox, b.hitbox)) {
                Activate(b)
                //isOnScreen = false // Meme si le bonus n'est pas appliqué (Ex: > maxSize) le bonus disparait au contact
            }

        }
    }


}