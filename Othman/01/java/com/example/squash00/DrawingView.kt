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

    lateinit var lesParois: Array<Paroi>

    private val backgroundPaint = Paint()
    private val random = Random()

    //var balle = Balle(random.nextFloat()*500, random.nextFloat()*1000, 50f)
    //var balle = Balle(300f, 300f, 50f)

    // now using arrayList of Balle instead of unique var balle
    var lesBalles = ArrayList<Balle>()

    //Score
    var score = 0
    var textPaint = Paint() // used in canvas.drawText
    var center_ball_y = 0f // shows y coordinate of ball center

    // Vie
    var life_left = 3
    var current_life = 3
    // icon for remaining lives (we start at the end of the list)
    var life_icon = listOf(R.drawable.number_4,R.drawable.number_1,R.drawable.number_2,R.drawable.number_3)


    // used to track y coordinate of ball on screen
    lateinit var current_ball: Balle
    // used to see if ball leaves screen (Paroi)
    var screenHeight = 0f
    var screenWidth = 0f

    init {

        textPaint.textSize = 50f
        textPaint.color = Color.BLACK

        // add first ball
        lesBalles.add(Balle(300f,300f,50f))

    }

    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            backgroundPaint.color = Color.WHITE
            canvas.drawRect(0F, 0F, canvas.width *1F,
                canvas.height *1F, backgroundPaint)

            //Ball
            for (b in lesBalles)
                if (b.isOnScreen) { // added condition to draw ball
                    b.bouge(lesParois)
                    b.draw(canvas)

                    if (!b.isOnScreen) { // if the ball left the screen
                        life_left -= 1
                        if (life_left !=0){
                            lesBalles.add(Balle(300f,300f,50f))
                        }
                    }
                    // used for information text
                    current_ball = b
                }
            //Paroi
            for (p in lesParois) {
                if(p.isOnScreen) {
                    p.draw(canvas)
                }
            }

            //Score
            //canvas.drawText(screenHeight.toString() /*score.toString()*/,30f,50f,textPaint)
            //canvas.drawText(score.toString(),30f,90f,textPaint)

            center_ball_y = current_ball.current_center_y
            // texte du haut
            canvas.drawText("Y coordinate is $center_ball_y",30f,50f,textPaint)

            // texte juste en dessous
            canvas.drawText("Le score est de $score",30f,90f,textPaint)

            // lives
            canvas.drawText("Vie : $life_left",450f,90f,textPaint)
            
            // Image resolution !CAREFUL! if resolution is too high, it slows down the app
            
            
            var image = BitmapFactory.decodeResource(resources,life_icon[current_life])

            var r = Rect(0, 0, screenWidth.toInt() + 2000, screenHeight.toInt() + 1500) // Rectangle contains our image
            
            var l = Rect(screenWidth.toInt() - 350, 0, screenWidth.toInt() - 200, 125) // Fits precedent rectangle in this one

            canvas.drawBitmap(image, r, l, null)

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

        // used to detect when ball leaves screen + always useful anyway
        screenHeight = h.toFloat()
        screenWidth = w.toFloat()

        lesParois = arrayOf(
            Paroi(0f, y, la, y+e,this),  // Nord
            Paroi(la-e, y, la, l,this),      // Est
            Paroi(0f, l, la, l+e,this),  // Sud
            Paroi(0f, y, e, l,this))         // Ouest
    }
    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }
    override fun surfaceCreated(holder: SurfaceHolder) {
        thread = Thread(this)
        thread.start()
    }
    override fun surfaceDestroyed(holder: SurfaceHolder) {
        thread.join()
    }
}
