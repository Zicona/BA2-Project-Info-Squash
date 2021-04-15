package com.example.test02.bonus

import android.graphics.RectF
import com.example.test02.game.Balle
import com.example.test02.game.DrawingView

class LifeUp(x: Float, y: Float, diametre: Float,isGentle: Boolean = true) : SpecialObject(x,y,diametre,isGentle) {
    override fun Activate(b: Balle, view: DrawingView) {
        super.Activate(b, view)
        view.lives +=1
    }
}