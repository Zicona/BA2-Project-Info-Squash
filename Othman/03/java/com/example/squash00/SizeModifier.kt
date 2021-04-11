package com.example.squash00

import android.graphics.RectF

class SizeModifier(x: Float,y: Float,diametre: Float,isGentle: Boolean) : SpecialObject(x,y,diametre, isGentle) {

    var old_diameter = 0f
    var difference_diameter = 0f

    // SizeUp Bonus
    var sizeUpAmplificator = 4f // > 1
    //SizeDown Malus
    var sizeDownAmplificator= 0.5f // < 1

    override fun Activate(b:Balle) {

        // SizeUp Bonus
        if(isGentle) {
            if (b.diametre * sizeUpAmplificator < b.maxSize) {

                // old diameter
                old_diameter = b.diametre
                // new diameter
                b.diametre = b.diametre * sizeUpAmplificator
                // adds the difference between old and new diameter
                difference_diameter = b.diametre - old_diameter
                b.hitbox.right += difference_diameter
                b.hitbox.bottom += difference_diameter

                isOnScreen = false
            }
        }
        // SizeDown Malus
        else{
            if (b.diametre * sizeDownAmplificator > b.minSize) {

                // old diameter
                old_diameter = b.diametre
                // new diameter
                b.diametre = b.diametre * sizeDownAmplificator
                // soustraie the difference between old and new diameter
                difference_diameter = old_diameter - b.diametre
                b.hitbox.right -= difference_diameter
                b.hitbox.bottom -= difference_diameter

                isOnScreen = false
            }
        }

    }

    override fun gereBalle(b: Balle) {
        if (isOnScreen){
            if (RectF.intersects(hitbox, b.hitbox)) {
                Activate(b)
                //isOnScreen = false // Meme si le bonus n'est pas appliqué (Ex: > maxSize) le bonus disparait
            }

        }
    }
}