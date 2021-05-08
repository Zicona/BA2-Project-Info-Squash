package com.example.test02.bonus


import android.graphics.RectF
import com.example.test02.game.Balle
import com.example.test02.game.DrawingView

class SizeModifier(x: Float,y: Float,diametre: Float,isGentle: Boolean) : SpecialObject(x,y,diametre, isGentle) {
    /** Objectif : changer la taille de la balle
     *  Héritage : hérite de specialObject, permet de créer les objets activables bonus/malus **/

    // Attribut hasChanged, vrai si la balle a subit une modification, permet de garder une trace des modifications subies
    var mustChangeBonus = false
    var mustChangeMalus = false

    init{}

    override fun Activate(b: Balle, view: DrawingView) {
        super.Activate(b, view)
        if (isGentle) {
            mustChangeBonus = b.changeTaille(1) // SizeUp Bonus
        } else {
            mustChangeMalus = b.changeTaille(2) // SizeDown Malus
        }
    }
}