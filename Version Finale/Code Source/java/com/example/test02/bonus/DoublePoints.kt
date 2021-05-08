package com.example.test02.bonus

import com.example.test02.game.Balle
import com.example.test02.game.DrawingView
import com.example.test02.game.Paroi

class DoublePoints(x: Float, y: Float, diametre: Float, isGentle: Boolean) : SpecialObject(x,y,diametre, isGentle) {
    override fun Activate(b: Balle, view: DrawingView) {
        super.Activate(b, view)
    }
}