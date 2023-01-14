package org.tensorflow.lite.examples.textclassification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

val defaultdata = listOf(ResultsModel("Sleep", "Sleep", 0.0f), ResultsModel("Nutrition", "Nutrition", 0.0f), ResultsModel("Stress", "Stress", 0.0f), ResultsModel("Alcohol", "Alcohol", 0.0f))
var resultsList: List<ResultsModel> = defaultdata

class AnalysisAdapter: RecyclerView.Adapter<AnalysisAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: AnalysisAdapter.ViewHolder, position: Int) {
        val question = resultsList[position].question
        val answer = resultsList[position].answer
        val result = resultsList[position].result
        holder.question.text = question
        holder.answer.text = answer
        holder.result.text = result.toString()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // if result is 0.0f skip
        // if result is 1.0f show

        if (viewType == 0) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_analysis_empty, parent, false)
            return ViewHolder(view)
        }
        else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_analysis, parent, false)
            return ViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (resultsList[position].result == 0.0f) {
            return 0
        }
        else {
            return 1
        }
    }

    override fun getItemCount(): Int {
        return resultsList.size;
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val question: TextView
        val answer: TextView
        val result: TextView
        init {
            question = itemView.findViewById(R.id.cardTextViewQuestion)
            answer = itemView.findViewById(R.id.cardTextViewAnswer)
            result = itemView.findViewById(R.id.cardTextViewResult)
        }
    }

    fun submitAnswer(Answer: String, Key : String) {
        // get key
        val key = Key
        // get answer
        val answer = Answer
        // get result
        val result = 1.0f
        // remove old key
        resultsList = resultsList.filter { it.question != key }
        // add to list
        resultsList = resultsList + ResultsModel(key, answer, result)
        println(key)
        println(answer)
        // print all results
        for (result in resultsList) {
            println(result.question)
            println(result.answer)
            println(result.result)
        }
        notifyDataSetChanged()
    }
}