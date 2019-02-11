package com.customchart.mposadar.customchartdemo

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ChartView.OnChartDraw {

    companion object {
        var chartView: ChartView? = null
        var numberOfParts = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // val data = intArrayOf(5, 5, 5, 5, 5, 5)
        val data = intArrayOf(5, 3, 1, 6, 2, 5)
        numberOfParts = data.size
        val color = intArrayOf(
            Color.RED,
            Color.BLUE,
            Color.CYAN,
            Color.GREEN,
            Color.MAGENTA,
            Color.GREEN)

        chartView = ChartView(this, data, color, this)
        container.addView(chartView)



    }

    override fun onChartDraw() {
        chartView?.let { chartView ->
            var scaledValues: FloatArray? = chartView.scaledValues
            var coordinates = chartView.coordinates

            for (i in 0 until numberOfParts) {
                scaledValues?.let {
                    if ((scaledValues[i] / 360) > 0.08) {
                        val imageView = ImageView(container.context)
                        imageView.setImageResource(R.drawable.ic_launcher_background)

                        val layoutParams: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(34, 34)
                        //layoutParams.leftMargin = (coordinates[i].xPosition - chartView.secondaryViewRadius).toInt()
                        //layoutParams.topMargin = (coordinates[i].yPosition - chartView.secondaryViewRadius).toInt()

                        imageView.x = (coordinates[i].xPosition - chartView.secondaryViewRadius).toFloat()
                        imageView.y = (coordinates[i].yPosition - chartView.secondaryViewRadius).toFloat()
                        imageView.layoutParams = layoutParams
                        container.addView(imageView)
                    }
                }
            }
        }
    }
}
