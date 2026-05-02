package com.example.aadi_app

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class SceneDrawingView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var sceneId = "park"

    fun setScene(id: String) {
        sceneId = id
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBackground(canvas)

        when (sceneId) {
            "school" -> drawSchool(canvas)
            "mall" -> drawMall(canvas)
            "road" -> drawRoad(canvas)
            "cricket" -> drawCricket(canvas)
            "table_tennis" -> drawTableTennis(canvas)
            "courtesy_thank_you" -> drawCourtesy(canvas, "Thank you", "?")
            "courtesy_sorry" -> drawCourtesy(canvas, "Oops!", "?")
            "courtesy_please" -> drawCourtesy(canvas, "I need water", "?")
            "courtesy_help" -> drawCourtesy(canvas, "Need help", "?")
            "courtesy_greeting" -> drawCourtesy(canvas, "Teacher", "?")
            "courtesy_namaste" -> drawCourtesy(canvas, "Hello", "?")
            else -> drawPark(canvas)
        }
    }

    private fun drawBackground(canvas: Canvas) {
        paint.style = Paint.Style.FILL
        paint.color = Color.rgb(198, 236, 255)
        canvas.drawRect(0f, 0f, width.toFloat(), height * 0.68f, paint)
        paint.color = Color.rgb(174, 222, 142)
        canvas.drawRect(0f, height * 0.68f, width.toFloat(), height.toFloat(), paint)
        paint.color = Color.rgb(255, 217, 102)
        canvas.drawCircle(width * 0.86f, height * 0.16f, width * 0.08f, paint)
    }

    private fun drawPark(canvas: Canvas) {
        drawTree(canvas, width * 0.13f, height * 0.46f)
        drawSwing(canvas, width * 0.42f, height * 0.47f)
        drawSlide(canvas, width * 0.72f, height * 0.52f)
        drawPerson(canvas, width * 0.28f, height * 0.72f, Color.rgb(255, 138, 128))
        drawPerson(canvas, width * 0.52f, height * 0.72f, Color.rgb(100, 181, 246))
    }

    private fun drawSchool(canvas: Canvas) {
        drawBuilding(canvas, width * 0.48f, height * 0.44f, Color.rgb(255, 213, 128), "ABC")
        drawBus(canvas, width * 0.2f, height * 0.68f)
        drawPerson(canvas, width * 0.74f, height * 0.7f, Color.rgb(129, 199, 132))
        drawBoard(canvas, width * 0.68f, height * 0.45f)
    }

    private fun drawMall(canvas: Canvas) {
        drawBuilding(canvas, width * 0.5f, height * 0.42f, Color.rgb(220, 200, 255), "MALL")
        drawShopWindow(canvas, width * 0.34f, height * 0.48f)
        drawShopWindow(canvas, width * 0.66f, height * 0.48f)
        drawPerson(canvas, width * 0.28f, height * 0.72f, Color.rgb(255, 171, 145))
        drawBag(canvas, width * 0.36f, height * 0.78f)
        drawPerson(canvas, width * 0.62f, height * 0.72f, Color.rgb(77, 182, 172))
    }

    private fun drawRoad(canvas: Canvas) {
        paint.color = Color.rgb(90, 90, 90)
        canvas.drawRect(0f, height * 0.62f, width.toFloat(), height.toFloat(), paint)
        paint.color = Color.WHITE
        repeat(4) {
            canvas.drawRect(
                width * (0.1f + it * 0.24f),
                height * 0.78f,
                width * (0.2f + it * 0.24f),
                height * 0.82f,
                paint
            )
        }
        drawCar(canvas, width * 0.28f, height * 0.7f, Color.rgb(100, 181, 246))
        drawCar(canvas, width * 0.66f, height * 0.84f, Color.rgb(255, 138, 128))
        drawTrafficLight(canvas, width * 0.82f, height * 0.46f)
        drawPerson(canvas, width * 0.5f, height * 0.56f, Color.rgb(255, 213, 79))
    }

    private fun drawCricket(canvas: Canvas) {
        drawPitch(canvas)
        drawStumps(canvas, width * 0.72f, height * 0.62f)
        drawPerson(canvas, width * 0.38f, height * 0.62f, Color.rgb(100, 181, 246))
        drawBat(canvas, width * 0.47f, height * 0.6f)
        paint.color = Color.rgb(244, 67, 54)
        canvas.drawCircle(width * 0.62f, height * 0.54f, width * 0.025f, paint)
    }

    private fun drawTableTennis(canvas: Canvas) {
        paint.color = Color.rgb(76, 175, 80)
        canvas.drawRoundRect(
            RectF(width * 0.18f, height * 0.48f, width * 0.82f, height * 0.72f),
            18f,
            18f,
            paint
        )
        paint.color = Color.WHITE
        canvas.drawRect(width * 0.49f, height * 0.48f, width * 0.51f, height * 0.72f, paint)
        paint.color = Color.rgb(255, 255, 255)
        canvas.drawCircle(width * 0.56f, height * 0.58f, width * 0.018f, paint)
        drawPerson(canvas, width * 0.18f, height * 0.62f, Color.rgb(255, 138, 128))
        drawPerson(canvas, width * 0.84f, height * 0.62f, Color.rgb(100, 181, 246))
        drawRacket(canvas, width * 0.3f, height * 0.6f)
        drawRacket(canvas, width * 0.7f, height * 0.6f)
    }

    private fun drawCourtesy(canvas: Canvas, leftBubble: String, rightBubble: String) {
        drawPerson(canvas, width * 0.28f, height * 0.72f, Color.rgb(100, 181, 246))
        drawPerson(canvas, width * 0.72f, height * 0.72f, Color.rgb(255, 171, 145))
        drawSpeechBubble(canvas, width * 0.28f, height * 0.28f, leftBubble)
        drawSpeechBubble(canvas, width * 0.72f, height * 0.28f, rightBubble)
    }

    private fun drawTree(canvas: Canvas, x: Float, y: Float) {
        paint.color = Color.rgb(120, 72, 40)
        canvas.drawRect(x - 12f, y, x + 12f, y + height * 0.22f, paint)
        paint.color = Color.rgb(76, 175, 80)
        canvas.drawCircle(x, y, width * 0.08f, paint)
    }

    private fun drawSwing(canvas: Canvas, x: Float, y: Float) {
        paint.color = Color.rgb(80, 80, 80)
        paint.strokeWidth = 8f
        canvas.drawLine(x - 70f, y + 120f, x, y - 60f, paint)
        canvas.drawLine(x + 70f, y + 120f, x, y - 60f, paint)
        canvas.drawLine(x, y - 60f, x, y + 60f, paint)
        canvas.drawLine(x - 36f, y + 62f, x + 36f, y + 62f, paint)
        paint.strokeWidth = 1f
    }

    private fun drawSlide(canvas: Canvas, x: Float, y: Float) {
        paint.color = Color.rgb(255, 167, 38)
        canvas.drawRect(x - 42f, y - 80f, x + 42f, y + 40f, paint)
        paint.color = Color.rgb(66, 165, 245)
        paint.strokeWidth = 14f
        canvas.drawLine(x + 40f, y - 20f, x + 130f, y + 100f, paint)
        paint.strokeWidth = 1f
    }

    private fun drawBuilding(canvas: Canvas, x: Float, y: Float, color: Int, label: String) {
        paint.color = color
        canvas.drawRoundRect(
            RectF(x - width * 0.24f, y - height * 0.18f, x + width * 0.24f, y + height * 0.22f),
            20f,
            20f,
            paint
        )
        paint.color = Color.rgb(93, 64, 55)
        canvas.drawRect(x - 34f, y + height * 0.08f, x + 34f, y + height * 0.22f, paint)
        paint.color = Color.rgb(36, 52, 71)
        paint.textSize = 42f
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText(label, x, y - height * 0.05f, paint)
    }

    private fun drawBus(canvas: Canvas, x: Float, y: Float) {
        paint.color = Color.rgb(255, 202, 40)
        canvas.drawRoundRect(RectF(x - 90f, y - 40f, x + 90f, y + 40f), 18f, 18f, paint)
        paint.color = Color.rgb(80, 80, 80)
        canvas.drawCircle(x - 50f, y + 42f, 18f, paint)
        canvas.drawCircle(x + 50f, y + 42f, 18f, paint)
    }

    private fun drawBoard(canvas: Canvas, x: Float, y: Float) {
        paint.color = Color.rgb(67, 160, 71)
        canvas.drawRect(x - 70f, y - 38f, x + 70f, y + 38f, paint)
        paint.color = Color.WHITE
        paint.textSize = 30f
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText("A B C", x, y + 10f, paint)
    }

    private fun drawShopWindow(canvas: Canvas, x: Float, y: Float) {
        paint.color = Color.rgb(179, 229, 252)
        canvas.drawRoundRect(RectF(x - 58f, y - 42f, x + 58f, y + 42f), 12f, 12f, paint)
    }

    private fun drawBag(canvas: Canvas, x: Float, y: Float) {
        paint.color = Color.rgb(255, 193, 7)
        canvas.drawRoundRect(RectF(x - 22f, y - 24f, x + 22f, y + 28f), 8f, 8f, paint)
    }

    private fun drawCar(canvas: Canvas, x: Float, y: Float, color: Int) {
        paint.color = color
        canvas.drawRoundRect(RectF(x - 80f, y - 35f, x + 80f, y + 35f), 18f, 18f, paint)
        paint.color = Color.rgb(40, 40, 40)
        canvas.drawCircle(x - 45f, y + 38f, 17f, paint)
        canvas.drawCircle(x + 45f, y + 38f, 17f, paint)
    }

    private fun drawTrafficLight(canvas: Canvas, x: Float, y: Float) {
        paint.color = Color.rgb(60, 60, 60)
        canvas.drawRoundRect(RectF(x - 28f, y - 78f, x + 28f, y + 78f), 12f, 12f, paint)
        paint.color = Color.RED
        canvas.drawCircle(x, y - 45f, 15f, paint)
        paint.color = Color.YELLOW
        canvas.drawCircle(x, y, 15f, paint)
        paint.color = Color.GREEN
        canvas.drawCircle(x, y + 45f, 15f, paint)
    }

    private fun drawPitch(canvas: Canvas) {
        paint.color = Color.rgb(210, 180, 120)
        canvas.drawRoundRect(
            RectF(width * 0.28f, height * 0.48f, width * 0.78f, height * 0.76f),
            24f,
            24f,
            paint
        )
    }

    private fun drawStumps(canvas: Canvas, x: Float, y: Float) {
        paint.color = Color.rgb(121, 85, 72)
        paint.strokeWidth = 8f
        canvas.drawLine(x - 16f, y - 60f, x - 16f, y + 30f, paint)
        canvas.drawLine(x, y - 60f, x, y + 30f, paint)
        canvas.drawLine(x + 16f, y - 60f, x + 16f, y + 30f, paint)
        paint.strokeWidth = 1f
    }

    private fun drawBat(canvas: Canvas, x: Float, y: Float) {
        paint.color = Color.rgb(121, 85, 72)
        paint.strokeWidth = 16f
        canvas.drawLine(x, y - 20f, x + 40f, y + 80f, paint)
        paint.strokeWidth = 1f
    }

    private fun drawRacket(canvas: Canvas, x: Float, y: Float) {
        paint.color = Color.rgb(244, 67, 54)
        canvas.drawCircle(x, y, 24f, paint)
        paint.color = Color.rgb(80, 80, 80)
        paint.strokeWidth = 8f
        canvas.drawLine(x, y + 20f, x + 26f, y + 54f, paint)
        paint.strokeWidth = 1f
    }

    private fun drawSpeechBubble(canvas: Canvas, x: Float, y: Float, text: String) {
        paint.style = Paint.Style.FILL
        paint.color = Color.WHITE
        val bubble = RectF(x - 120f, y - 54f, x + 120f, y + 54f)
        canvas.drawRoundRect(bubble, 28f, 28f, paint)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4f
        paint.color = Color.rgb(36, 52, 71)
        canvas.drawRoundRect(bubble, 28f, 28f, paint)

        paint.style = Paint.Style.FILL
        paint.strokeWidth = 1f
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = if (text.length > 10) 24f else 34f
        paint.color = Color.rgb(36, 52, 71)
        canvas.drawText(text, x, y + 10f, paint)
    }

    private fun drawPerson(canvas: Canvas, x: Float, y: Float, shirtColor: Int) {
        paint.color = Color.rgb(255, 204, 170)
        canvas.drawCircle(x, y - 72f, 24f, paint)
        paint.color = shirtColor
        canvas.drawRoundRect(RectF(x - 28f, y - 48f, x + 28f, y + 28f), 14f, 14f, paint)
        paint.color = Color.rgb(60, 60, 60)
        paint.strokeWidth = 8f
        canvas.drawLine(x - 20f, y + 24f, x - 42f, y + 75f, paint)
        canvas.drawLine(x + 20f, y + 24f, x + 42f, y + 75f, paint)
        canvas.drawLine(x - 28f, y - 20f, x - 58f, y + 20f, paint)
        canvas.drawLine(x + 28f, y - 20f, x + 58f, y + 20f, paint)
        paint.strokeWidth = 1f
    }
}
