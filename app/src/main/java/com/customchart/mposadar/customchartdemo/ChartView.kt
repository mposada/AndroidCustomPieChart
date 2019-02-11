package com.customchart.mposadar.customchartdemo

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.view.View

data class ChartCoordinates(val xPosition: Double, val yPosition: Double)

class ChartView(context: Context?, val data: IntArray, val color: IntArray, val onChartDraw: OnChartDraw) :
    View(context) {

    interface OnChartDraw {
        fun onChartDraw()
    }

    var scaledValues: FloatArray? = null
    var coordinates: ArrayList<ChartCoordinates> = ArrayList()
    var start = 270F
    val secondaryViewRadius = 16F
    var cx: Int = 0
    var cy:Int = 0

    init {
        scaledValues = scale()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        initCoordinates()

        canvas?.let {
            canvas.drawColor(Color.WHITE)

            val newRect = canvas.clipBounds;
            newRect.inset(-30, -30)  // make the rect larger
            canvas.clipRect (newRect, Region.Op.REPLACE)

            val paint = Paint()
            paint.isAntiAlias = true
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 10F
            paint.style = Paint.Style.FILL

            var rectF = RectF(0F, 0F, width.toFloat(), width.toFloat())

            for (i in 0 until data.size) {
                paint.color = color[i]
                paint.style = Paint.Style.FILL
                scaledValues?.let { scaledValues ->
                    canvas.drawArc(rectF, start, scaledValues[i], true, paint)

                    start += scaledValues[i]

                    /*
                    paint.color = Color.BLACK
                    if ((scaledValues[i] / 360) > 0.08) {

                        canvas.drawCircle(
                            coordinates[i].xPosition.toFloat(),
                            coordinates[i].yPosition.toFloat(),
                            34F,
                            paint
                        )
                    }
                    */
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

            onChartDraw.onChartDraw()
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
    private fun getTotal(): Float {
        var total = 0F
        data?.forEach { value ->
            total += value
        }
        return total
    }

    private fun initCoordinates() {
        val primaryViewRadius = width / 2
        var startAngle = 3 * Math.PI / 2
        var endAngle = startAngle

        for (i in 0 until data.size) {
            scaledValues?.let { scaledValues ->

                start += scaledValues[i]

                startAngle = endAngle
                endAngle = startAngle + 2 * Math.PI * (scaledValues[i] / 360)
                val startInDegrees = startAngle * 180 / Math.PI
                val endAngleInDegrees = endAngle * 180 / Math.PI

                val middleAngle = ((endAngleInDegrees - startInDegrees) / 2) + startInDegrees
                val sinValue = Math.sin(middleAngle * Math.PI / 180)
                val cosValue = Math.cos(middleAngle * Math.PI / 180)
                val xPosition = (primaryViewRadius * cosValue) + primaryViewRadius
                val yPosition = (primaryViewRadius * sinValue) + primaryViewRadius

                coordinates.add(ChartCoordinates(xPosition = xPosition, yPosition = yPosition))
            }
        }

    }
}