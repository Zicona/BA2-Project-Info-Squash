package com.example.test02.game

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.example.test02.R
import com.example.test02.bonus.SizeModifier
import com.example.test02.bonus.SpecialObject
import java.util.*
import kotlin.math.PI

class DrawingView @JvmOverloads constructor(context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes, defStyleAttr), SurfaceHolder.Callback, Runnable {
    lateinit var canvas: Canvas
    lateinit var thread: Thread
    var drawing = true

    // Might be usefull at some point
    val activity = context as FragmentActivity

    // Colors and texts dimensions
    private val backgroundPaint = Paint()
    private val touchColor1 = Paint()
    private val touchColor2 = Paint()
    private val textColor = Paint()
    private val random = Random()

    // Game data
    var startScore = 0
    var score = startScore
    var startLife = 3
    var lives = startLife
    var losingHP = false
    private lateinit var currentBall: Balle
    val size = 150F
    var startX = 450F
    var startY = 700F

    // Create game objects
    var balle = Balle(startX, startY, size)

    // Testing Bonus/Malus
    var bonusSize = SizeModifier(350f,200f,100f,true)
    var malusSize = SizeModifier(150f,500f,100f,false)

    // Arrays of objects used
    lateinit var lesParois: Array<Paroi>
    var lesBalles = arrayListOf<Balle>(balle)
    var lesBonus = arrayListOf<SpecialObject>(bonusSize, malusSize)

    init {
//        // Use arrays to sort objects by type
//        lesBalles.add(balle)
//        lesBonus.add(bonusSize)
//        lesBonus.add(malusSize)
    }

    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()

            // Modify Paint objects and format the future textDraws
            backgroundPaint.color = Color.BLACK
            textColor.color = Color.WHITE
            textColor.textSize = 70F
            textColor.textAlign = Paint.Align.RIGHT

            // Draw background
            canvas.drawRect(0F, 0F, canvas.width * 1F, canvas.height * 1F, backgroundPaint)

            // Draw walls, bonus/malus and then the balls
            for (p in lesParois) {
                p.draw(canvas)
            }
            for (i in lesBonus) {
                if (i.onScreen) {
                    i.draw(canvas)
                }
            }
            losingHP = true
            for (b in lesBalles) {
                if (b.onScreen) {
                    losingHP = false; currentBall = b
                    gereVitesseBalle(b,"+", 0.01F) // Increases speed, proportionnaly to score)
                    b.bouge(lesParois, lesBonus, this)
                    b.draw(canvas)
                }
            }
            // Update lives counter and reset ball if needed
            if (losingHP) {
                lives -= 1
                losingHP = false
                if (lives != 0) {
                    currentBall.reset((width*1F - size)/2, (height*1F - size)/2)
                } else {
                    triggerGameOverDialog()
                }
            }

            // Update speed counter
            val vitesse = balle.getVitesseNormed()
            canvas.drawText("%.2f".format(vitesse), 250f, 100F, textColor)

            // Update point counter
            canvas.drawText("$score", (canvas.width*1F)/2, 100F, textColor)
            canvas.drawText("$lives", (canvas.width*1F)/2 + 200F, 100F, textColor)

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
//                testCursorPosition(x, y)
                // en-cas de contact avec la balle
                if (balle.hitbox.intersects(
                                x-precisionClic, y-precisionClic,
                                x+precisionClic, y+precisionClic)) {
                    gereDirectionBalle(x)
                }
            }
        }
        return true
    }
    fun testCursorPosition(x: Float, y: Float) {
        // J'essaye de faire un truc facile à utiliser pour gérer le positionnement du clic à vue de nez
        touchColor1.color = Color.MAGENTA
        touchColor2.color = Color.RED
        val bigSize = 200F
        val lilSize = 30F
        val touchBig = RectF(x - bigSize, y - bigSize, x + bigSize, y + bigSize)
        val touchLittle = RectF(x - lilSize, y - lilSize, x + lilSize, y + lilSize)
        canvas.drawOval(touchBig, touchColor1)
        canvas.drawOval(touchLittle, touchColor2)
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
    fun gereVitesseBalle(balle: Balle, sign: String, value: Float) {
        when(sign) {
            "+" -> {
                balle.speed = balle.initSpeed + score*value
            }
            "-" -> {
                balle.speed = balle.initSpeed - score*value
            }
        }
    }

    fun triggerGameOverDialog() {
        class GameResult: DialogFragment() {
            override fun onCreateDialog(bundle: Bundle?): Dialog {
                val builder = AlertDialog.Builder(activity)
                builder.setTitle(R.string.game_over)
                builder.setMessage(
                        resources.getString(
                                R.string.results_format, 0, 0F, 0
                        )
                )
                builder.setPositiveButton(R.string.restart_game) { _, _ -> newGame() }
                builder.setNegativeButton(R.string.back_menu) { _, _ -> newGame() }
                return builder.create()
            }
        }

        activity.runOnUiThread {
            val ft = activity.supportFragmentManager.beginTransaction()
            val prev = activity.supportFragmentManager.findFragmentByTag("dialog")
            if (prev != null) {
                ft.remove(prev)
            }
            ft.addToBackStack(null)
            val gameResult = GameResult()
            gameResult.isCancelable = false
            gameResult.show(ft, "dialog")
        }
    }

    fun newGame() {
        val w = width.toFloat()
        val h = height.toFloat()
        balle.reset(w/2 - size/2, h/2 - size/2)
        score = startScore
        lives = startLife

        for (i in lesBonus) {
            i.onScreen = true
        }

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
//                Paroi(0f, l, la, l + e, false), // Sud
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