package org.tensorflow.lite.examples.textclassification

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class QuestionsAdapter: RecyclerView.Adapter<QuestionsAdapter.ViewHolder>() {
    // "Sleep", "Nutrition", "Stress", "Alcohol"
    val questions = arrayOf("Sleep", "Nutrition", "Stress", "Alcohol")

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = questions[position]
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
                    Toast.makeText(
                        itemView.context,
                        "You clicked on ${questions[position]}",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(itemView.context, Question_sleep::class.java)
                    intent.putExtra("question", questions[position])
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}