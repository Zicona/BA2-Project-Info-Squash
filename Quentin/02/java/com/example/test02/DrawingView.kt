package com.example.test02

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
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.*

class DrawingView @JvmOverloads constructor(context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes, defStyleAttr), SurfaceHolder.Callback, Runnable {
    lateinit var canvas: Canvas
    lateinit var thread: Thread
    var drawing = true

    val activity = context as FragmentActivity

    lateinit var lesParois: Array<Paroi>

    private val backgroundPaint = Paint()
    private val touchColor = Paint()
    private val random = Random()

    var balle = Balle(500f, 1000f, 150f)

    init {}

    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            backgroundPaint.color = Color.WHITE
            canvas.drawRect(0F, 0F, canvas.width * 1F,
                    canvas.height * 1F, backgroundPaint)
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
                val x = e.rawX.toFloat()
                val y = e.rawY.toFloat() - 220f
                val precision = 2.5f
                // sert à tester l'emplacement du curseur
//                touchColor.color = Color.MAGENTA
//                val taille = 200f
//                val touch = RectF(x - taille, y - taille, x + taille, y + taille)
//                canvas?.drawOval(touch, touchColor)
//                balle.changeDirection(true)
                // en-cas de contact avec la balle
                if (balle.hitbox.intersects(x-precision,y-precision,x+precision,y+precision)) {
                    gereDirectionBalle(true, x, y)
                }
            }
        }
        return true
    }

    fun gereDirectionBalle(change: Boolean, x: Float, y: Float){
        if (x > balle.hitbox.centerX()) {
            balle.changeDirection(change, -45) // vers la gauche
        } else {
            balle.changeDirection(change, 45) // vers la droite
        }
    }

    fun showGameOverDialog(messageId: Int) {
        class GameResult: DialogFragment() {
            override fun onCreateDialog(bundle: Bundle?): Dialog {
                val builder = AlertDialog.Builder(activity)
                builder.setTitle(resources.getString(messageId))
                builder.setMessage(
                        resources.getString(
                                R.string.results_format, 0,0.0,0
                        )
                )
                builder.setPositiveButton(R.string.restart_game) { _, _ -> newGame() }
                builder.setNegativeButton(R.string.reset_game) { _, _ -> resumeGame() }

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
        balle.reset(500f, 1000f)
        resume()
    }
    fun resumeGame() {
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