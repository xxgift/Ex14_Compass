package com.egco428.ex14_compass

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class CompassView(context: Context): View(context) {
    private var paint: Paint = Paint()
    private var position = 0f

    init {
        init()
    }

    private fun init() {
        //draw a compass
        paint = Paint()
        paint.isAntiAlias = true
        paint.strokeWidth = 2.0F
        paint.textSize = 25.0F
        paint.style = Paint.Style.STROKE
        paint.color = Color.DKGRAY
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val xPoint = (getMeasuredWidth() / 2).toFloat()
        val yPoint = (getMeasuredHeight() / 2).toFloat()

        val radius = (Math.max(xPoint, yPoint) * 0.6).toFloat()
        canvas?.drawCircle(xPoint, yPoint, radius, paint)
        canvas?.drawRect(0.0F, 0.0F, getMeasuredWidth().toFloat(), getMeasuredHeight().toFloat(), paint)

        // 3.143 is a good approximation for the circle
        canvas?.drawLine(xPoint,
            yPoint,
            (xPoint + radius * Math.sin((-position).toDouble() / 180 * 3.143)).toFloat(),
            (yPoint - radius * Math.cos((-position).toDouble() / 180 * 3.143)).toFloat(), paint)

        canvas?.drawText(position.toString(), xPoint, yPoint, paint)
    }

    fun updateData(position: Float) {
        this.position = position
        invalidate()
    }

}