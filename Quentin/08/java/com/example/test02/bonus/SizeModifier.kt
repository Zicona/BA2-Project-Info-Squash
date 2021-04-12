package com.example.test02.bonus


import android.graphics.RectF
import com.example.test02.game.Balle

class SizeModifier(x: Float,y: Float,diametre: Float,isGentle: Boolean) : SpecialObject(x,y,diametre, isGentle) {

    var old_diameter = 0f
    var difference_diameter = 0f

    // SizeUp Bonus
    var sizeUpAmplificator = 4f // > 1
    //SizeDown Malus
    var sizeDownAmplificator= 0.5f // < 1

    override fun Activate(b: Balle) {
        if (isGentle) {
            b.changeTaille(2) // SizeUp Bonus
        } else {
            b.changeTaille(3) // SizeDown Malus
        }
        onScreen = false
    }

    override fun gereBalle(b: Balle) {
        if (onScreen){
            if (RectF.intersects(hitbox, b.hitbox)) {
                Activate(b)
                //isOnScreen = false // Meme si le bonus n'est pas appliqué (Ex: > maxSize) le bonus disparait
            }

        }
    }
}