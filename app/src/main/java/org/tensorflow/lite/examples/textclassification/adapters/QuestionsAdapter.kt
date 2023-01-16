package org.tensorflow.lite.examples.textclassification.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.textclassification.QuestionModel
import org.tensorflow.lite.examples.textclassification.R
import org.tensorflow.lite.examples.textclassification.activities.MainActivity
import org.tensorflow.lite.examples.textclassification.activities.QuestionActivity
import org.tensorflow.lite.examples.textclassification.activities.currentQuestion
import org.tensorflow.lite.examples.textclassification.activities.questionsAnswers

class QuestionsAdapter: RecyclerView.Adapter<QuestionsAdapter.ViewHolder>() {
    // "Sleep", "Nutrition", "Stress", "Alcohol"
    val questions = listOf(QuestionModel("Sleep", ""), QuestionModel("Nutrition", ""), QuestionModel("Stress", ""), QuestionModel("Alcohol", ""))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = questions[position].question
        holder.question.text = question
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_question, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val question: TextView

        init {
            question = itemView.findViewById(R.id.textViewQuestion)
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // main questionsAnswer hashmap
                    val main = MainActivity()
                    val questionsAnswers = questionsAnswers
                    val intent = Intent(itemView.context, QuestionActivity::class.java)
                    intent.putExtra("question", questions[position].question)
                    intent.putExtra("currentAnswer", questionsAnswers[questions[position].question])
                    currentQuestion = questions[position].question
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}