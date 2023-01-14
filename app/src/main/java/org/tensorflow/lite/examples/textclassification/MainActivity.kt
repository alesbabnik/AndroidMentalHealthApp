
/*
 * Copyright 2022 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.tensorflow.lite.examples.textclassification

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.tensorflow.lite.examples.textclassification.databinding.ActivityMainBinding
import org.tensorflow.lite.examples.textclassification.fragments.AnalysisFragment
import org.tensorflow.lite.examples.textclassification.fragments.HomeFragment
import org.tensorflow.lite.examples.textclassification.fragments.QuestionsFragment
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.text.nlclassifier.BertNLClassifier
import java.util.concurrent.ScheduledThreadPoolExecutor

var questionsAnswers = mutableMapOf<String, String>();
var results = mutableMapOf<String, Float>();
var currentQuestion: String? = null
var currentAnswer: String? = null
class MainActivity : AppCompatActivity() {
    private lateinit var bertClassifier: BertNLClassifier
    private var _activityMainBinding: ActivityMainBinding? = null
    private val activityMainBinding get() = _activityMainBinding!!
    // public set of questionmodel objects


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // create adapter for questionsFragment recycler view
        replaceFragment(HomeFragment())


        initmobilebert()

        // setOnItemSelectedListener is called when the user clicks on an item in the bottom sheet
        _activityMainBinding!!.bottomNavigationView.setOnItemSelectedListener {
            // switch to the selected item
            when (it.itemId) {
                R.id.questions -> {
                    replaceFragment(QuestionsFragment())
                    true
                }
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.analysis -> {
                    replaceFragment(AnalysisFragment())
                    true
                }
                else -> false
            }
        }
        for ((key, value) in questionsAnswers) {
            println("Question: $key")
            println("Answer: $value")
        }
        println("Questions and answers: $questionsAnswers")
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            // Workaround for Android Q memory leak issue in IRequestFinishCallback$Stub.
            // (https://issuetracker.google.com/issues/139738913)
            finishAfterTransition()
        } else {
            super.onBackPressed()
        }
    }

    fun addQuestion(question: String?, answer: String?) {
        questionsAnswers.put(question!!, answer!!)
        for ((key, value) in questionsAnswers) {
            println("Question: $key")
            println("Answer: $value")
        }
    }

    fun submitAnswer() {
        Log.d("Main", "Running submitAnswer")
        // get key
        val key = currentQuestion.toString()
        // get answer
        val answer = currentAnswer.toString()
        Log.d("Main", key)
        Log.d("Main", answer)
        // get result
        val executor = ScheduledThreadPoolExecutor(1)
        executor.execute {
            val result = bertClassifier.classify(answer)
            Log.d("negative", result[0].score.toString())
            Log.d("positive", result[1].score.toString())
            AnalysisAdapter().updateResult(answer, key, result[0].score, result[1].score)
        }
        Toast.makeText(
            this,
            "Submission sent!",
            Toast.LENGTH_SHORT
        ).show()
    }
    fun initmobilebert() {
        val baseOptionsBuilder = BaseOptions.builder()
        when (0) {
            DELEGATE_CPU -> {
                // Default
            }
            DELEGATE_NNAPI -> {
                baseOptionsBuilder.useNnapi()
            }
        }
        val baseOptions = baseOptionsBuilder.build()

        val options = BertNLClassifier.BertNLClassifierOptions
            .builder()
            .setBaseOptions(baseOptions)
            .build()

        bertClassifier = BertNLClassifier.createFromFileAndOptions(
            this,
            MOBILEBERT,
            options)
    }
    companion object {
        const val DELEGATE_CPU = 0
        const val DELEGATE_NNAPI = 1
        const val MOBILEBERT = "mobilebert.tflite"
    }
}