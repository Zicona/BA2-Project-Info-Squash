package com.example.squash

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

class DrawingView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes,defStyleAttr), SurfaceHolder.Callback, Runnable {

    lateinit var canvas: Canvas
    val backgroundPaint = Paint()
    lateinit var thread: Thread
    var drawing: Boolean = true

    lateinit var LesBalles: Array<Balle>
    lateinit var lesParois: Array<Obstacle> // Array car on a un nombre limité de parois

    init {
        backgroundPaint.color = Color.WHITE
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val canvasH = (h - 230).toFloat()
        val canvasW = (w - 25).toFloat()

        lesParois = arrayOf(
            // les arguments des parois dépendants de la hauteur(canvasH) et de la largeur(canvasW) de l'écran
            Obstacle(5f, 5f, 25f, canvasH),
            Obstacle(5f, 5f, canvasW, 25f),
            Obstacle(5f, canvasH, canvasW, canvasH + 25),
            Obstacle(canvasW, 5f, canvasW + 25, canvasH + 25)
        )
        LesBalles = arrayOf(
            Balle(25f, 51f, 25f)
        )
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

    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawRect(0F, 0F, canvas.getWidth()*1F,
                canvas.getHeight()*1F, backgroundPaint)


            // dessine les balles
            for (b in LesBalles) {
                if (!b.dead) { // on ne dessine les balles que si elles n'ont pas été tuées
                    b.draw(canvas)
                }
            }

            // dessine les parois
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

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        TODO("Not yet implemented")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        thread.join()
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        thread = Thread(this)
        thread.start()
    }

}