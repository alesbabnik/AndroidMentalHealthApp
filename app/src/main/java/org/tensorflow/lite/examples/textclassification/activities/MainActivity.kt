package org.tensorflow.lite.examples.textclassification.activities

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.tensorflow.lite.examples.textclassification.R
import org.tensorflow.lite.examples.textclassification.adapters.AnalysisAdapter
import org.tensorflow.lite.examples.textclassification.adapters.ViewPagerAdapter
import org.tensorflow.lite.examples.textclassification.databinding.ActivityMainBinding
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.text.nlclassifier.BertNLClassifier
import java.util.concurrent.ScheduledThreadPoolExecutor


var questionsAnswers = mutableMapOf<String, String>();
var currentQuestion: String? = null
var currentAnswer: String? = null
class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private var icons = intArrayOf(
        R.drawable.ic_baseline_home_24,
        R.drawable.ic_baseline_question_answer_24,
        R.drawable.ic_baseline_poll_24
    )

    private lateinit var bertClassifier: BertNLClassifier
    private var _activityMainBinding: ActivityMainBinding? = null
    private val activityMainBinding get() = _activityMainBinding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        initmobilebert()

        tabLayout = findViewById(R.id.tabs)
        viewPager = findViewById(R.id.pager)

        viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager){ tab,index ->
            tab.text = when(index) {
                0 -> {"Home"}
                1 -> {"Questions"}
                2 -> {"Analysis"}
                else ->{""}
            }
            tab.setIcon(icons[index])
        }.attach()
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
    }
    fun initmobilebert() {
        val baseOptionsBuilder = BaseOptions.builder()
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
        const val MOBILEBERT = "mobilebert.tflite"
    }
}