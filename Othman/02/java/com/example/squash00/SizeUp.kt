package com.example.squash00

import android.graphics.RectF

class SizeUp(x: Float,y: Float,diametre: Float,isGentle: Boolean = true) : SpecialObject(x,y,diametre, isGentle) {


    var sizeAmplificator = 4f
    var old_diameter = 0f
    var difference_diameter = 0f

    override fun Activate(b:Balle) {
        if (b.diametre*sizeAmplificator < b.maxSize){

            // old diameter
            old_diameter = b.diametre
            // new diameter
            b.diametre = b.diametre*sizeAmplificator
            // adds the difference between old and new diameter
            difference_diameter = b.diametre - old_diameter
            b.hitbox.right += difference_diameter
            b.hitbox.bottom += difference_diameter

            isOnScreen = false
        }
    }

    override fun gereBalle(b: Balle) {
        if (isOnScreen){
            if (RectF.intersects(hitbox, b.hitbox)) {
                Activate(b)
                isOnScreen = false
            }

        }
    }
}