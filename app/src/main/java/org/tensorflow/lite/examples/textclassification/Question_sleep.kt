package org.tensorflow.lite.examples.textclassification

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import org.tensorflow.lite.examples.textclassification.databinding.ActivityQuestionSleepBinding

class Question_sleep : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityQuestionSleepBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuestionSleepBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get the question from the intent
        val question = intent.getStringExtra("question")
        binding.textViewQuestionTitle.text = question
    }
}