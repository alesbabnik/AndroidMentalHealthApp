package org.tensorflow.lite.examples.textclassification

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.ui.AppBarConfiguration
import org.tensorflow.lite.examples.textclassification.databinding.ActivityQuestionBinding
import org.tensorflow.lite.examples.textclassification.fragments.QuestionsFragment

class QuestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get the question from the intent
        val question = intent.getStringExtra("question")
        var desc = ""
        if (question == "Sleep")
            desc = "How well would you describe your sleep quality last night?"
        else if (question == "Nutrition")
            desc = "How well would you describe your nutrition intake?"
        else if (question == "Stress")
            desc = "How well would you describe your stress during the day?"
        else if (question == "Alcohol")
            desc = "How well would you describe your alcohol consumption in general?"
        binding.textViewQuestionTitle.text = desc
        val submitButton = binding.buttonSubmit
        submitButton.setOnClickListener {
            // get the answer from the edit text
            val answer = binding.editTextAnswer.text.toString()
            // add the question and answer to the hashmap
            val main = MainActivity()
            // put the question and answer in the hashmap
            currentAnswer = answer
            currentQuestion = question
            main.addQuestion(question, answer)

            // go back to the previous activity
            finish()
        }
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        val main = MainActivity()
    }
}

