package com.example.test02.bonus


import android.graphics.RectF
import com.example.test02.game.Balle

class SizeModifier(x: Float,y: Float,diametre: Float,isGentle: Boolean) : SpecialObject(x,y,diametre, isGentle) {
    /** Objectif : changer la taille de la balle
     *  Héritage : hérite de specialObject, permet de créer les objets activables bonus/malus **/
    override fun Activate(b: Balle) {
        if (isGentle) {
            b.changeTaille(2) // SizeUp Bonus
        } else {
            b.changeTaille(3) // SizeDown Malus
        }
        onScreen = false
    }
}