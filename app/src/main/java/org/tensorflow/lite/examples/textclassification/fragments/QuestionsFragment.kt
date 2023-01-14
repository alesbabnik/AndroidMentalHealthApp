package org.tensorflow.lite.examples.textclassification.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.textclassification.*
import org.tensorflow.lite.examples.textclassification.databinding.FragmentQuestionsBinding

class QuestionsFragment : Fragment() {

    companion object {
        fun newInstance() = QuestionsFragment()
    }

    private lateinit var viewModel: QuestionsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_questions, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.questions)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        val adapter = QuestionsAdapter()
        recyclerView.adapter = adapter

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QuestionsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentQuestionsBinding.bind(view)
        binding.questions.layoutManager = LinearLayoutManager(context)
        binding.questions.setHasFixedSize(true)
        val adapter = QuestionsAdapter()
        binding.questions.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        submit()
    }

    fun submit() {
        val main = activity as MainActivity
        if (currentAnswer != null || currentQuestion != null)
            main.submitAnswer()
    }

}