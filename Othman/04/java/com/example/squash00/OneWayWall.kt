package com.example.squash00

import android.content.Context
import android.graphics.RectF
import kotlinx.android.synthetic.main.activity_main.*

class OneWayWall (x:Float,y: Float, diametre: Float, isGentle:Boolean= false,var view: DrawingView) : SpecialObject(x,y,diametre,isGentle) {

    var screenWidth = view.screenWidth
    var screenHeigt = view.screenHeight
    // Obstacle dimension
    var demi_longueur = 250f
    var demi_epaisseur = 40f


    //Obstacle dimension
    var gauche = 0f
    var haut = 0f

    var droite = 0f
    var bas = 0f

    var o = Obstacle(gauche,haut,droite,bas,view)

    init {
        hasTimer = true
        bonusTimeLeft = 15.0

    }



    override fun Activate(b: Balle) {
        bonusOn = true
        updateDimension()
        activateObstacle(o)

    }

    override fun gereBalle(b: Balle) {
        if (isOnScreen){
            if (RectF.intersects(hitbox, b.hitbox)) {
                Activate(b)
                isOnScreen = false // Meme si le bonus n'est pas appliqué (Ex: > maxSize) le bonus disparait au contact
            }
        }
        else{
            if (bonusOn){
                if (RectF.intersects(o.hitbox, b.hitbox)) {
                    updateDimension()

                    /** DEBUG
                    b.gauche = gauche //screenWidth/2-demi_longueur
                    b.droite = droite //screenWidth/2+demi_longueur**/

                    if (b.dy == -1f && b.current_center_x > gauche && b.current_center_x<droite){b.changeDirection(true)}
                    if (b.dy == -1f && b.current_center_x <=gauche && b.dx ==1f || b.dy == -1f && b.current_center_x >= droite && b.dx ==-1f){b.changeDirection(false)}

                }
            }
            // if bonusOn = false ---> we hide obstacle
            else {o.isOnScreen = false}
        }
        /** DEBUG
        b.BonusTime = bonusTimeLeft**/

    }
    fun activateObstacle(o:Obstacle){
        o.isOnScreen = true
    }

    fun updateDimension(){
        screenWidth = view.screenWidth
        screenHeigt = view.screenHeight

        gauche =screenWidth/2-demi_longueur
        haut = screenHeigt/2+demi_epaisseur
        droite = screenWidth/2+demi_longueur
        bas = screenHeigt/2-demi_epaisseur

        o = Obstacle(gauche,haut,droite,bas,view)
    }

}