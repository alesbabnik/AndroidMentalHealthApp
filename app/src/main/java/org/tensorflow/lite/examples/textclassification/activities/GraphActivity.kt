package org.tensorflow.lite.examples.textclassification.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import org.tensorflow.lite.examples.textclassification.R
import org.tensorflow.lite.examples.textclassification.adapters.AnalysisAdapter

class GraphActivity : AppCompatActivity() {
    lateinit var barChart: BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        // barchart
        barChart = findViewById<BarChart>(R.id.barchart)
        barChart.setDrawBarShadow(false)
        barChart.setDrawValueAboveBar(true)
        barChart.description.isEnabled = false
        barChart.setMaxVisibleValueCount(4)
        barChart.setPinchZoom(false)
        barChart.setDrawGridBackground(false)
        barChart.axisLeft.axisMinimum = 0f
        barChart.axisLeft.axisMaximum = 1f

        fillData()
    }

    fun fillData() {
        val data = AnalysisAdapter().getAnalysis()
        val entries = ArrayList<BarEntry>()
        for (i in 0 until data.size) {
            if (data[i].negative != -0.0f)
                entries.add(BarEntry(i.toFloat(), data[i].positive!!.toFloat(), data[i].negative!!.toFloat()))
        }
        // fill the data
        val barDataSet = BarDataSet(entries, "Positivity")
        barDataSet.setColors(*ColorTemplate.MATERIAL_COLORS)
        barDataSet.valueTextSize = 16f
        barDataSet.valueTextColor = R.color.black
        val barData = BarData(barDataSet)
        barChart.data = barData
        barChart.animateY(800)
        barChart.invalidate()
    }
}