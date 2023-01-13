package org.tensorflow.lite.examples.textclassification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.textclassification.databinding.FragmentHomeBinding
import org.tensorflow.lite.support.label.Category


class HomeFragment : Fragment() {
    private lateinit var classifierHelper: TextClassificationHelper
    private val adapter by lazy {
        ResultsAdapter()
    }

    private val listener = object : TextClassificationHelper.TextResultsListener {
        override fun onResult(results: List<Category>, inferenceTime: Long) {

            adapter.resultsList = results.sortedByDescending {
                it.score
            }

            adapter.notifyDataSetChanged()
        }

        override fun onError(error: String) {
            Toast.makeText(requireActivity(), error, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        classifierHelper = TextClassificationHelper(
            context = container!!.context,
            listener = listener)
        
        // recycler view
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val binding = FragmentHomeBinding.bind(view)
        binding.results.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.results.adapter = adapter
    
        
        val classifyBtn = binding.classifyBtn

        classifyBtn.setOnClickListener {
            if (binding.inputText.text.isNullOrEmpty()) {
                classifierHelper.classify(getString(R.string.default_edit_text))
            }
            else {
                classifierHelper.classify(binding.inputText.text.toString())
            }
        }
        binding.results.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // when classify button is clicked
        // if input text is empty, classify default text
        // else classify input text

        val classifyBtn = view.findViewById<View>(R.id.classify_btn)
        val binding = FragmentHomeBinding.bind(view)
        classifyBtn.setOnClickListener {
            if (binding.inputText.text.isNullOrEmpty()) {
                classifierHelper.classify(getString(R.string.default_edit_text))
            }
            else {
                classifierHelper.classify(binding.inputText.text.toString())
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }
}