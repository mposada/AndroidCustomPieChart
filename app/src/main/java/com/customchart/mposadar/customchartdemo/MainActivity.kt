package com.customchart.mposadar.customchartdemo

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView = ImageView(container.context)
        imageView.setImageResource(R.drawable.ic_launcher_background)
        // val data = intArrayOf(5, 5, 5, 5, 5, 5)
        val data = intArrayOf(5, 3, 1, 6, 2, 5)
        val color = intArrayOf(
            Color.RED,
            Color.BLUE,
            Color.CYAN,
            Color.GREEN,
            Color.MAGENTA,
            Color.GREEN)

        val chartView = ChartView(this, data, data.size, color)

        container.addView(chartView)

//        val layoutParams: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(24, 24)
//        layoutParams.leftMargin = chartView.coordinates[0].x.toInt()
//        layoutParams.topMargin = chartView.coordinates[0].y.toInt()
//        imageView.layoutParams = layoutParams
//        container.addView(imageView)
    }
}
