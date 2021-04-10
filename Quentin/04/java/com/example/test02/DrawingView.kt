package com.example.test02

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
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

    var balle = Balle(500f, 700f, 500f)

    init {}

    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            backgroundPaint.color = Color.WHITE
            textColor.color = Color.BLACK
            canvas.drawRect(0F, 0F, canvas.width * 1F,
                    canvas.height * 1F, backgroundPaint)
            val vitesse = sqrt(balle.dx*balle.dx+balle.dy*balle.dy)
            canvas.drawText("$vitesse",
                    30f, 50f, textColor)
            balle.bouge(lesParois)
            balle.draw(canvas)
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
                if (balle.hitbox.intersects(x-precisionClic,y-precisionClic,x+precisionClic,y+precisionClic)) {
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

    fun showGameOverDialog() {
        activity.runOnUiThread(
                Runnable {
                    val manager = activity.supportFragmentManager
                    val transaction = manager.beginTransaction()
                    val prev = manager.findFragmentByTag("dialog")
                    if (prev != null) {
                        transaction.remove(prev)
                    }
                    val pause = PauseFragment(activity, this)
                    transaction.addToBackStack(null) // Permet de revenir au jeu en-cas de retour en arrière
                    pause.show(transaction, "dialog")

                }

        )
    }

    fun newGame() {
        val width = canvas.width*1F
        val height = canvas.height*1F
        balle.reset(500F, 700F)
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
        val l = h*0.75f   // Longueur de la boite

        lesParois = arrayOf(
                Paroi(0f, y, la, y + e),  // Nord
                Paroi(la - e, y, la, l),      // Est
                Paroi(0f, l, la, l + e),  // Sud
                Paroi(0f, y, e, l))         // Ouest
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