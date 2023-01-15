package org.tensorflow.lite.examples.textclassification.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import org.tensorflow.lite.examples.textclassification.AnalysisAdapter
import org.tensorflow.lite.examples.textclassification.R

class GraphFragment : Fragment() {
    lateinit var barChart: BarChart

    companion object {
        fun newInstance() = GraphFragment()
    }

    private lateinit var viewModel: GraphViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // barchart
        val view = inflater.inflate(R.layout.fragment_graph, container, false)
        barChart = view.findViewById<BarChart>(R.id.barchart)
        barChart.setDrawBarShadow(false)
        barChart.setDrawValueAboveBar(true)
        barChart.description.isEnabled = false
        barChart.setMaxVisibleValueCount(4)
        barChart.setPinchZoom(false)
        barChart.setDrawGridBackground(false)
        barChart.axisLeft.axisMinimum = 0f
        barChart.axisLeft.axisMaximum = 1f

        fillData()

        return view
    }

    fun fillData() {
        val data = AnalysisAdapter().getAnalysis()
        val entries = ArrayList<BarEntry>()
        for (i in 0 until data.size) {
            if (data[i].negative != -0.0f)
                entries.add(BarEntry(i.toFloat(), data[i].positive!!.toFloat(), data[i].negative!!.toFloat()))
        }
        // fill the data
        val barDataSet = BarDataSet(entries, "Questions")
        barDataSet.setColors(*ColorTemplate.MATERIAL_COLORS)
        barDataSet.valueTextSize = 16f
        barDataSet.valueTextColor = R.color.black
        val barData = BarData(barDataSet)
        barChart.data = barData
        barChart.animateY(800)
        barChart.invalidate()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GraphViewModel::class.java)
        // TODO: Use the ViewModel
    }

}