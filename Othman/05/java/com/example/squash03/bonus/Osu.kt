package com.example.squash03.bonus

import android.graphics.Canvas
import android.graphics.RectF
import com.example.squash03.game.*
import android.util.AttributeSet
import android.content.Context
import androidx.appcompat.app.AppCompatActivity

class Osu(x:Float,y:Float,diametre:Float,isGentle:Boolean = true,var view: DrawingView) : SpecialObject(x,y,diametre,isGentle) {



    override fun Activate(b: Balle,view: DrawingView) {
        super.Activate(b, view)
        playingTimeLeft = 5.0
        view.OsuIsActive = true
        for (i in 0..2){// pour ajouter 3 cibles
            view.lesCibles[i] = Cible(x=15f,y=15f,diametre = 156f,view = view)
            view.lesCibles[i].giveRandomPosition()
        }
    }

    override fun gereBalle(b: Balle,view: DrawingView, i: SpecialObject) {
        super.gereBalle(b, view, i)
        view.raquettetime = playingTimeLeft

    }

    override fun updateTimerPlaying(elapsedTimeMS: Double, drawingView: DrawingView) {
        super.updateTimerPlaying(elapsedTimeMS, drawingView)
        if (playingTimeLeft <=0) {
            view.OsuIsActive = false
            for (c in view.lesCibles){
                c.onScreen = false
            }
            view.osuCibleTouched = 0
        }
    }



    fun drawCible(canvas: Canvas?){
        for (i in 0..2){
            if(view.lesCibles[i].onScreen) {
                view.lesCibles[i].draw(canvas)
            }
        }

    }



}