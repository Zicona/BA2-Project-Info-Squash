package com.example.squash03.bonus

import android.graphics.RectF
import com.example.squash03.game.Balle
import com.example.squash03.game.DrawingView

class LifeUp(x: Float, y: Float, diametre: Float,isGentle: Boolean = true) : SpecialObject(x,y,diametre,isGentle) {

    override fun Activate(b: Balle, view: DrawingView) {
        super.Activate(b, view)
        view.lives +=1
    }
}