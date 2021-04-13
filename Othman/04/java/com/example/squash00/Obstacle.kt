package com.example.squash00

import android.graphics.Color

class Obstacle(x1: Float,y1: Float, x2:Float,y2:Float,view: DrawingView,scoring:Boolean = false) : Paroi(x1, y1, x2, y2, view, scoring) {

    init {
        isOnScreen = false
        paint.color = Color.LTGRAY
    }

}