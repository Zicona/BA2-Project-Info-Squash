package com.example.squash03.bonus


import com.example.squash03.game.Balle
import com.example.squash03.game.DrawingView
import com.example.squash03.game.Paroi

class DoublePoints(x: Float, y: Float, diametre: Float, isGentle: Boolean) : SpecialObject(x,y,diametre, isGentle) {
    override fun Activate(b: Balle, view: DrawingView) {
        super.Activate(b, view)
    }
}