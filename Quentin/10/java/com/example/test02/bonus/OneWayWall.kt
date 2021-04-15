package com.example.test02.bonus

import android.graphics.RectF
import com.example.test02.game.Balle
import com.example.test02.game.DrawingView
import kotlinx.android.synthetic.main.activity_main.*

class OneWayWall (x:Float,y: Float, diametre: Float, isGentle:Boolean = false, var view: DrawingView) : SpecialObject(x,y,diametre,isGentle) {

    // Obstacle dimension
    var demi_longueur = 250f
    var demi_epaisseur = 40f
    // Obstacle Center
    var x = 500f
    var y = 500f

    // Création de l'objet Obstacle
    var o = view.lesObstacles[0]

    init {}

    override fun Activate(b: Balle, view: DrawingView) {
        super.Activate(b, view)
        playingTimeLeft = 15.0
        updateDimension(x, y)
        activateObstacle(o)
    }

    override fun gereBalle(b: Balle, view: DrawingView, i: SpecialObject) {
        super.gereBalle(b, view, i)
        if (isPlaying) {
            if (RectF.intersects(o.hitbox, b.hitbox)) {
                if (b.dy == -1f && b.currentX > hitbox.left && b.currentX < hitbox.right) {
                    b.changeDirectionParoi(true)
                }
                if (b.dy == -1f && b.currentX <= hitbox.left && b.dx == 1f || b.dy == -1f && b.currentX >= hitbox.right && b.dx == -1f) {
                    b.changeDirectionParoi(false)
                }
            }
        }
        else { // if bonusOn = false ---> we hide obstacle
            o.onScreen = false
        }
    }


    fun activateObstacle(o:Obstacle){
        o.onScreen = true
    }
    fun updateDimension(width: Float, height: Float){
        o.hitbox.top = height + demi_epaisseur
        o.hitbox.left = width - demi_longueur
        o.hitbox.bottom = height - demi_epaisseur
        o.hitbox.right = width + demi_longueur
    }
}