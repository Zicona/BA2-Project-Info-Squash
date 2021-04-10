package com.example.test02

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.*
import kotlin.math.PI
import kotlin.math.sqrt

class DrawingView @JvmOverloads constructor(context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes, defStyleAttr), SurfaceHolder.Callback, Runnable {
    lateinit var canvas: Canvas
    lateinit var thread: Thread
    var drawing = true

    val activity = context as FragmentActivity

    lateinit var lesParois: Array<Paroi>

    private val backgroundPaint = Paint()
    private val touchColor = Paint()
    private val textColor = Paint()
    private val random = Random()
    var score = 0
    var lives = 3

    var balle = Balle(500f, 700f, 150f)

    init {}

    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            // Modifie les objets Paint qui servent aussi à gérer le format du texte
            backgroundPaint.color = Color.WHITE
            textColor.color = Color.BLACK
            textColor.textSize = 70F
            textColor.textAlign = Paint.Align.RIGHT
            // Dessine le fond
            canvas.drawRect(0F, 0F, canvas.width * 1F, canvas.height * 1F, backgroundPaint)
            // Met à jour le compteur vitesse de la balle
            val vitesse = sqrt(balle.dx*balle.dx+balle.dy*balle.dy)
            canvas.drawText("$vitesse", 250f, 100F, textColor)
            //Fait bouger la Balle
            balle.bouge(lesParois,this)
            balle.draw(canvas)
            // Met à jour le compteur de point
            canvas.drawText("$score", (canvas.width*1F)/2, 100F, textColor)
            canvas.drawText("$lives", (canvas.width*1F)/2 + 200F, 100F, textColor)
            // Dessine les parois
            for (p in lesParois) {
                p.draw(canvas)
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
                val x = e.rawX.toFloat() - 0F
                val y = e.rawY.toFloat() - 100F
                val precisionClic = 2.5F
                // sert à tester l'emplacement du curseur
//                touchColor.color = Color.MAGENTA
//                val taille = 200f
//                val touch = RectF(x - taille, y - taille, x + taille, y + taille)
//                canvas?.drawOval(touch, touchColor)
                // en-cas de contact avec la balle
                if (balle.hitbox.intersects(
                                x-precisionClic,
                                y-precisionClic,
                                x+precisionClic,
                                y+precisionClic)){
                    gereDirectionBalle(x)
                }
            }
        }
        return true
    }

    fun gereDirectionBalle(x: Float){
        var change = true
        val precision = 50 // Nombre de section dans la balle, entier impair
        val fov = 120*(PI/180) // Champ de vision en RADIAN, double à cause de PI
        val depart = -30*(PI/180) // Départ du champ de vision (à droite) (Attention: Y vers le bas)
        // -30 pour aller vers l'avant, 150 pour aller vers l'arrière
        val x0 = balle.hitbox.right - balle.hitbox.left
        val xStep = x0/precision
        val angleStep = fov/precision

        for (i in 0 until precision)
            if (x <= balle.hitbox.left + i * xStep && change) {
                balle.changeDirectionTouch((depart - i * angleStep).toFloat())
                change = false
            }
    }

    fun newGame() {
        balle.reset(500F, 700F)
        score = 0
        Toast.makeText(context,"Nouvelle partie!", Toast.LENGTH_SHORT).show()
        resume()
    }
    fun resumeGame() {
        Toast.makeText(context,"Fin de la pause!", Toast.LENGTH_SHORT).show()
        resume()
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

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val y = 140f    // Taille de l'interstice avec le haut de l'écran
        val e = 25f     // Épaisseur des parois
        val la = w*1f   // Largeur de la boite
        val l = (h-e)*1f   // Longueur de la boite

        lesParois = arrayOf(
                Paroi(0f, y, la, y + e, true),  // Nord, qui compte les points
                Paroi(la - e, y, la, l, false),     // Est
                Paroi(0f, l, la, l + e, false), // Sud
                Paroi(0f, y, e, l, false))          // Ouest
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