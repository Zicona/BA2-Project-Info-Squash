package com.example.test02.bonus

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import com.example.test02.game.Balle
import com.example.test02.game.DrawingView
import com.example.test02.game.Obstacle

class Raquette(x:Float,y:Float,diametre:Float, isGentle:Boolean = false, var view: DrawingView) : SpecialObject(x,y,diametre,isGentle) {
    // Raquette dimension
    var demi_longueur = 200f
    var demi_epaisseur = 30f
    // Raquette dimension
    var gauche = 0f
    var haut = 0f
    var droite = 0f
    var bas = 0f
    var TopLeftY = 0f
    var TopLeftX = 0f
    // Raquette translation
    var translation_x = -5f
    var translation_y = -5f

    var o = view.lesObstacles[1]

    init {
        o.paint.color = Color.MAGENTA
    }

    override fun Activate(b: Balle, view: DrawingView) {
        super.Activate(b, view)
        playingTimeLeft = 20.0
        view.canClickBall = false
        updateDimension(view.width/2f,view.height-200f)
        activateObstacle(o)
    }

    override fun gereBalle(b: Balle, view: DrawingView, i: SpecialObject) {
        super.gereBalle(b, view, i)
        /** DEBUG
        view.raquetteIsPlaying = isPlaying
        view.raquetteObstacleOnScreen = o.onScreen
        view.raquettetime = playingTimeLeft */
        if(isPlaying){
            if (RectF.intersects(o.hitbox, b.hitbox)) {
                b.changeDirectionParoi(true)
//                if (b.currentX+b.diametre/2 > gauche && b.currentX-b.diametre/2 < droite){
//                    b.changeDirectionParoi(true)
//                }
//                if (b.currentX <=gauche && b.dx > 0f ||  b.currentX >= droite && b.dx < 0f){
//                    b.changeDirectionParoi(false)
//                }
            }
        }
        else{
            view.canClickBall = true
            o.onScreen = false
        }
    }

    fun activateObstacle(o: Obstacle){
        o.onScreen = true
    }

    fun updatePosition(){
        TopLeftX = translation_x-demi_longueur
        TopLeftY = translation_y-demi_epaisseur

        gauche = translation_x - demi_longueur
        droite =  translation_x + demi_longueur

        o.hitbox.offsetTo(TopLeftX,TopLeftY)

        o.onScreen = true
    }

    fun updateDimension(width: Float, height: Float){
        o.hitbox.top = height + demi_epaisseur
        o.hitbox.left = width - demi_longueur
        o.hitbox.bottom = height - demi_epaisseur
        o.hitbox.right = width + demi_longueur
    }
}