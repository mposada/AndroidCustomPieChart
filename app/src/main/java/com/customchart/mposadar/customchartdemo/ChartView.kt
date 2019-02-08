package com.customchart.mposadar.customchartdemo

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.View

class ChartView(context: Context?, val data: IntArray, val numberOfparts: Int, val color: IntArray) :
    View(context) {

    companion object {
        var start = 270F
    }

    var cx: Int = 0
    var cy:Int = 0

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            canvas.drawColor(Color.WHITE)

            val newRect = canvas.clipBounds;
            newRect.inset(-30, -30)  // make the rect larger
            canvas.clipRect (newRect, Region.Op.REPLACE)

            val paint = Paint()
            paint.isAntiAlias = true
            paint.color = Color.RED
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 10F
            paint.style = Paint.Style.FILL

            var scaledValues: FloatArray? = scale()

            var rectF = RectF(0F, 0F, width.toFloat(), width.toFloat())

            paint.color = Color.BLACK

            val primaryViewRadious = width / 2
            val secondaryViewRadious = 24F
            var startAngle = 3 * Math.PI / 2
            var endAngle = startAngle

            for (i in 0 until numberOfparts) {
                paint.color = color[i]
                paint.style = Paint.Style.FILL
                scaledValues?.let {
                    canvas.drawArc(rectF, start, scaledValues[i], true, paint)

                    start += scaledValues[i]

                    paint.color = Color.BLACK
                    startAngle = endAngle
                    endAngle = startAngle + 2 * Math.PI * (scaledValues[i] / 360)
                    val startInDegrees = startAngle * 180 / Math.PI
                    val endAngleInDegrees = endAngle * 180 / Math.PI

                    val middleAngle = ((endAngleInDegrees - startInDegrees) / 2) + startInDegrees
                    val sinValue = Math.sin(middleAngle * Math.PI / 180)
                    val cosValue = Math.cos(middleAngle * Math.PI / 180)
                    val xPosition = (primaryViewRadious * cosValue) + primaryViewRadious
                    val yPosition = (primaryViewRadious * sinValue) + primaryViewRadious

                    if ((scaledValues[i] / 360) > 0.08) {

                        canvas.drawCircle(xPosition.toFloat(), yPosition.toFloat(), 24F, paint)
                    }
                }

                //break
            }


            val cenPaint = Paint()
            val radius = width / 2 - 130
            cenPaint.style = Paint.Style.FILL
            cenPaint.color = Color.WHITE
            cy = width / 2
            cx = cy

            //this is middle black circle to hide some portions of arc
            canvas.drawCircle(cx.toFloat(), cy.toFloat(), radius.toFloat(), cenPaint)
        }
    }

    //this scale method used to calculate how much portion will be covered by specific data
    fun scale(): FloatArray? {
        var scaledValues: FloatArray? = null
        data?.let { data ->
            scaledValues = FloatArray(data.size)
            val total = getTotal() //Total all values supplied to the chart
            for (i in 0 until data.size) {
                scaledValues!![i] = data[i] / total * 360 //Scale each value
            }
        }

        return scaledValues
    }

    //need the sum of the data to calculate scale
    fun getTotal(): Float {
        var total = 0F
        data?.forEach { value ->
            total += value
        }
        return total
    }


}