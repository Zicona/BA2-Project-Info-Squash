package com.example.squash00


import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.*

class DrawingView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes, defStyleAttr),
    SurfaceHolder.Callback, Runnable {
    lateinit var canvas: Canvas
    lateinit var thread : Thread
    var drawing = true


    private val backgroundPaint = Paint()
    private val random = Random()


    //Paroi
    lateinit var lesParois: Array<Paroi>
    //Balles
    var lesBalles = ArrayList<Balle>()
    //Bonus
    var lesBonus = ArrayList<SpecialObject>()

    //Score
    var score = 0
    var textPaint = Paint() // used in canvas.drawText
    var center_ball_y = 0f // shows y coordinate of ball center
    var center_ball_x = 0f // shows x coordinate of ball center

    // Vie
    var life_left = 3
    var current_life = 3
    // icon for remaining lives (we start at the end of the list)
    var life_icon = listOf(R.drawable.number_4,R.drawable.number_1,R.drawable.number_2,R.drawable.number_3)


    // text y coordinate
    lateinit var current_ball: Balle
    // used to see if ball leaves screen (Paroi)
    var screenHeight = 0f
    var screenWidth = 0f

    init {

        textPaint.textSize = 50f
        textPaint.color = Color.BLACK

        // add first ball
        lesBalles.add(Balle(300f,300f,50f))
        // add 1 bonus
        lesBonus.add(SizeModifier(350f,200f,100f,true))
        // add 1 malus
        lesBonus.add(SizeModifier(150f,500f,100f,false))


    }

    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            backgroundPaint.color = Color.WHITE
            canvas.drawRect(0F, 0F, canvas.width *1F,
                canvas.height *1F, backgroundPaint)


            //Paroi
            for (p in lesParois) {
                if(p.isOnScreen) {
                    p.draw(canvas)
                }
            }
            //Bonus
            for (i in lesBonus){
                if(i.isOnScreen){
                    i.draw(canvas)
                }
            }
            //Ball
            for (b in lesBalles)
                if (b.isOnScreen) {
                    b.bouge(lesParois,lesBonus)
                    b.draw(canvas)

                    if (!b.isOnScreen) {
                        //if b ...
                        life_left -= 1
                        if (life_left !=0){
                            lesBalles.add(Balle(300f,300f,50f))
                        }
                    }
                    // used for information text
                    current_ball = b
                }


            //if score 100 --> addr random bonus dans lesBonus

            //Score
            //canvas.drawText(screenHeight.toString() /*score.toString()*/,30f,50f,textPaint)
            //canvas.drawText(score.toString(),30f,90f,textPaint)

            center_ball_y = current_ball.current_center_y
            center_ball_x = current_ball.current_center_x
            // texte du haut
            canvas.drawText("X :%.0f".format(center_ball_x),30f,50f,textPaint)
            canvas.drawText("Y :%.0f".format(center_ball_y),250f,50f,textPaint)

            // texte juste en dessous
            canvas.drawText("Le score est de $score",30f,100f,textPaint)

            // lives
            canvas.drawText("Vie : $life_left",450f,100f,textPaint)
            // Image resolution slows down the app

            /*var image = BitmapFactory.decodeResource(resources,life_icon[current_life])

            var r = Rect(0, 0, screenWidth.toInt() + 2000, screenHeight.toInt() + 1500)

            var l = Rect(screenWidth.toInt() - 350, 0, screenWidth.toInt() - 200, 125)

            canvas.drawBitmap(image, r, l, null)*/



            if (life_left != current_life) {
                current_life = life_left
            }




            holder.unlockCanvasAndPost(canvas)



        }
    }

    override fun run() {
        while (drawing) {
            draw()
        }
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                val x = e.rawX.toInt()
                val y = e.rawY.toInt() - 150
            }
        }
        return true
    }

    fun pause() {
        drawing = false
        thread.join()
    }
    fun resume() {
        drawing = true
        thread = Thread(this)
        thread.start()
    }

    override fun onSizeChanged(w: Int,h: Int,oldw: Int,oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val y = 125f    // Taille de l'interstice avec le haut de l'écran
        val e = 25f     // Épaisseur des parois
        val la = w*1f   // Largeur de la boite
        val l = h*1f-25f  //0.75f   // Longueur de la boite

        // Score ( if ball leaves screen)
        screenHeight = h.toFloat()
        screenWidth = w.toFloat()


        lesParois = arrayOf(
            Paroi(0f, y, la, y+e,this,true),   // Nord
            Paroi(la-e, y, la, l,this, false),      // Est
            Paroi(0f, l, la, l+e,this,false),    // Sud
            Paroi(0f, y, e, l,this,false)            // Ouest
        )
    }


    override fun surfaceCreated(holder: SurfaceHolder) {
        thread = Thread(this)
        thread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        TODO("Not yet implemented")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        thread.join()
    }
}