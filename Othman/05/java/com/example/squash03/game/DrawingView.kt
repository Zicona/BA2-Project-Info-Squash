package com.example.squash03.game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.squash03.bonus.*
import com.example.squash03.dialogFragment.GameOverDialog
import com.example.squash03.dialogFragment.SettingsDialog
import kotlinx.android.synthetic.main.activity_main.view.*
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
    var startLife = 1
    var lives = startLife
    var losingHP = false
    var isFirstTime = true
    private lateinit var currentBall: Balle
    val size = 150F
    var startX = 450F
    var startY = 700F

    // Create game objects
    var balle = Balle(startX, startY, size)

    // Create parameter window
    val settingsDialog = SettingsDialog(drawingView, context as MainActivity)

    // Create walls and obstacles
    lateinit var lesParois: Array<Paroi>
    var lesObstacles = arrayOf(
            Obstacle(0f, 0f, 0f, 0f, this) // Obstacle pour OneWayWall
            , Obstacle(0f, 0f, 0f, 0f, this)
    )

    // Cibles
    var lesCibles = arrayOf(
            Cible(0f,0f,0f,this),
            Cible(0f,0f,0f,this),
            Cible(0f,0f,0f,this)
    )
    // Bonus/Malus
    var bonusSize = SizeModifier(350f,200f,100f,true)
    var malusSize = SizeModifier(150f,500f,100f,false)
    var bonusPoints = DoublePoints(500f, 500f, 100f, true)
    var bonusLifeUp = LifeUp(600f, 300f, 100f, true)
    var oneWayWall = OneWayWall(500f, 300f, 100f, false, this)
    var safeWall = SafeWall(500f, 200f, 100f, true)
    var raquette = Raquette(400f,400f,100f,view=this)
    var osu = Osu(400f,400f,100f,true,this)
    private val scoreToBonus = 5 // Nombre de points à obtenir avant qu'un nouveau bonus n'apparaisse
    var osuCibleTouched = 0
    var firstApparition = true
    var canClickBall = true // for Raquette
    var OsuIsActive = false // for Osu


    // Arrays of objects used
    var lesBalles = arrayListOf<Balle>(balle)
    var lesBonus = arrayListOf<SpecialObject>(
            //bonusSize,
            //malusSize,
            //bonusPoints,
            //bonusLifeUp,
            //oneWayWall,
            //safeWall,
            //raquette,
            osu
    )

    init {
        // Modify Paint objects and format the future textDraws
        backgroundPaint.color = Color.WHITE
        textColor.color = Color.BLACK
        textColor.textSize = 50F
        textColor.textAlign = Paint.Align.LEFT

        for (bonus in lesBonus) {
            when(bonus) {
                is SizeModifier -> {
                    if (bonus.isGentle) bonus.isActive = false else bonus.isActive = false
                }
                is DoublePoints -> {bonus.isActive = true}
                is OneWayWall -> {bonus.isActive = false}
                is SafeWall -> {bonus.isActive = false}
                is LifeUp -> {bonus.isActive = false}
            }
        }
    }

    /**
    var raquetteObstacleOnScreen = false
    var raquetteIsPlaying = false**/
    var raquettetime = 0.0

    fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()

            // Draw background
            canvas.drawRect(0F, 0F, canvas.width * 1F, canvas.height * 1F, backgroundPaint)

            // Draw walls, bonus/malus and then the balls
            for (p in lesParois) if (p.onScreen) p.draw(canvas)
            for (o in lesObstacles) if (o.onScreen) o.draw(canvas)
            for (i in lesBonus) if (i.onScreen and i.isShowing) i.draw(canvas)
            for (c in lesCibles) if (c.onScreen) osu.drawCible(canvas)

            // Gestion de la balle
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
            if (losingHP and isFirstTime) {
                if (lives > 0) lives -= 1
                if (lives != 0) {
                    currentBall.reset((width*1F - size)/2, (height*1F - size)/2)
                } else {
                    isFirstTime = false // Permet d'empecher le code de boucler sur cette partie quand les vies sont à 0
                    triggerGameOverDialog()
                }
            }

            if (firstApparition and (score % scoreToBonus == 0)) {
                triggerBonusApparition()
                firstApparition = false // Empeche l'apparition d'un bonus tout de suite après
                // Set on True when score goes 1 up (in class Paroi)
            }

            // Update speed counter
            /*val vitesse = balle.getVitesseNormed()
            canvas.drawText("%.2f".format(vitesse), 250f, 100F, textColor)*/

            /** DEBUG **/
            canvas.drawText("Time %.2f".format(raquettetime), 30f, 100F, textColor)
            //canvas.drawText("raqu isPlaying $raquetteIsPlaying", 500f, 100F, textColor)

            // Update point counter
            canvas.drawText("$score", (canvas.width*1F)/2, 100F, textColor)
            canvas.drawText("$lives", (canvas.width*1F)/2 + 200F, 100F, textColor)

            holder.unlockCanvasAndPost(canvas)
        }
    }
    override fun run() {
        var previousFrameTime = System.currentTimeMillis()
        while (drawing) {
            val currentTime = System.currentTimeMillis()
            val elapsedTimeMS = (currentTime - previousFrameTime).toDouble()
            for (i in lesBonus) { // Gestion des Timer des bonus/malus
                if (i.isShowing) {
                    i.updateTimerShowing(elapsedTimeMS, this) // Timer quand inactif mais là
                } else if (i.isPlaying) {
                    i.updateTimerPlaying(elapsedTimeMS, this) // Timer quand actif
                }
            }
            draw()
            previousFrameTime = currentTime
        }
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {

            MotionEvent.ACTION_DOWN -> {
                val x = e.rawX.toFloat() - 0F
                val y = e.rawY.toFloat() - 100F
                val precisionClic = 2.5F
                if(canClickBall){

                // sert à tester l'emplacement du curseur
//                testCursorPosition(x, y)
                // en-cas de contact avec la balle
                if (balle.hitbox.intersects(
                                x-precisionClic, y-precisionClic,
                                x+precisionClic, y+precisionClic)) {
                    gereDirectionBalle(x)
                }
                }
                if (osu.isPlaying){
                    for (c in lesCibles){
                        if (c.hitbox.intersect(x-precisionClic, y-precisionClic, x+precisionClic, y+precisionClic)){
                            c.onClick()
                        }
                    }
                }

            }
            MotionEvent.ACTION_MOVE ->{
                if(!canClickBall){
                    val x = e.rawX.toInt()
                    val y = e.rawY.toInt() - 150
                    raquette.translation_x = x.toFloat()
                    raquette.translation_y = y.toFloat()
                    raquette.updatePosition()
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
            "+" -> {balle.speed = balle.initSpeed + score*value}
            "-" -> {balle.speed = balle.initSpeed - score*value}
        }
    }

    fun triggerGameOverDialog() {
        val dialog = GameOverDialog(drawingView, context as MainActivity)
        dialog.isCancelable = false
        dialog.show(activity.supportFragmentManager, "gameOverDialog")
    }
    fun triggerSettingsDialog() {
        settingsDialog.show(activity.supportFragmentManager, "settingsDialog")
    }
    fun triggerBonusApparition() {
        val random = Random()
        var isOk = false
        while (!isOk) {
            val index = (random.nextFloat()*(lesBonus.size-0.5)).toInt() // 0.5 est là pour normaliser
            if (lesBonus[index].isActive) {
                lesBonus[index].launchObject()
                isOk = true
            }
        }
    }
    fun triggerToastFin() { /** DEBUG PURPOSE **/
        Toast.makeText(context,"Fini!", Toast.LENGTH_SHORT).show()
    }

    fun newGame() {
        score = startScore; lives = startLife; isFirstTime = true
        for (b in lesBalles) {b.setShowBalle(false)}
        for (i in lesBonus) {i.resetObject()}
        val w = width.toFloat(); val h = height.toFloat()
        balle.reset(w/2 - size/2, h/2 - size/2)
        Toast.makeText(context,"Nouvelle partie!", Toast.LENGTH_SHORT).show()
        resume()
    }
    fun resumeGame() {
        for (i in lesBonus) if (i.onScreen) i.setPlayBonus(true) // Met les chrono des bonus/malus en route
        Toast.makeText(context,"Fin de la pause!", Toast.LENGTH_SHORT).show()
        resume()
    }
    fun pauseGame() {
        for (i in lesBonus) if (i.onScreen) i.setPlayBonus(false) // Met les chrono des bonus/malus en pause
        Toast.makeText(context,"Partie mise en pause!", Toast.LENGTH_SHORT).show()
        pause()
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
                Paroi(0f, y, e, l, false)           // Ouest
        )
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