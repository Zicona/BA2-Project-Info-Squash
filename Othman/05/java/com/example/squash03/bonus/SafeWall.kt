package com.example.squash03.bonus

import com.example.squash03.game.Balle
import com.example.squash03.game.DrawingView
import kotlinx.android.synthetic.main.activity_main.*

class SafeWall(x: Float, y: Float, diametre: Float, isGentle: Boolean): SpecialObject(x, y, diametre, isGentle) {
    override fun Activate(b: Balle, view: DrawingView) {
        super.Activate(b, view)
        if (!view.lesParois[2].onScreen) view.lesParois[2].onScreen = true
    }
}