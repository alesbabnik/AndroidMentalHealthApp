package org.tensorflow.lite.examples.textclassification.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.textclassification.*
import org.tensorflow.lite.examples.textclassification.activities.GraphActivity
import org.tensorflow.lite.examples.textclassification.adapters.AnalysisAdapter

class AnalysisFragment : Fragment() {
    companion object {
        fun newInstance() = AnalysisFragment()
    }

    private lateinit var viewModel: AnalysisViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // recycler view
        val view = inflater.inflate(R.layout.fragment_analysis, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.analysisRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        var adapter = AnalysisAdapter()
        recyclerView.adapter = adapter

        // if buttonGraph is clicked, go to GraphFragment
        val buttonGraph = view.findViewById<View>(R.id.buttonGraph)
        buttonGraph.setOnClickListener {
            // open graph activity
            val intent = Intent(requireContext(), GraphActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AnalysisViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()
        // reload the data
        Log.d("AnalysisFragment", "resuming")
        val view = view

        val recyclerView = view?.findViewById<RecyclerView>(R.id.analysisRecyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        var adapter = AnalysisAdapter()
        recyclerView?.adapter = adapter
    }
}