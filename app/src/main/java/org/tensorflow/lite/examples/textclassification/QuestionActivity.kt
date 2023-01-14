package org.tensorflow.lite.examples.textclassification

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import org.tensorflow.lite.examples.textclassification.databinding.ActivityQuestionBinding

class QuestionActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityQuestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get the question from the intent
        val question = intent.getStringExtra("question")
        binding.textViewQuestionTitle.text = question
        println(question)
        val submitButton = binding.buttonSubmit
        submitButton.setOnClickListener {
            // get the answer from the edit text
            val answer = binding.editTextAnswer.text.toString()
            // add the question and answer to the hashmap
            val main = MainActivity()
            // intent answer to main activity
            val intent = Intent(this, MainActivity::class.java)
            intent.getStringExtra("answer")
            // put the question and answer in the hashmap
            main.currentAnswer = answer
            main.currentQuestion = question
            main.addQuestion(question, answer)
            println("current answer: ${main.currentAnswer}")
            println("current question: ${main.currentQuestion}")
            // go back to the previous activity
            AnalysisAdapter().submitAnswer(main.currentAnswer.toString(), main.currentQuestion.toString())

            finish()
        }
    }

    // override the back button
    override fun onBackPressed() {
        // go back to the previous activity
        finish()
    }

    // print the hashmap when the activity is finished
    override fun onDestroy() {
        super.onDestroy()
        val main = MainActivity()
    }
}

