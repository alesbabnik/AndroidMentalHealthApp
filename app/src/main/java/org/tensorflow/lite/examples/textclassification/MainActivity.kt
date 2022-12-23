
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

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.tensorflow.lite.examples.textclassification.databinding.ActivityMainBinding
import org.tensorflow.lite.support.label.Category

import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {

    private var _activityMainBinding: ActivityMainBinding? = null
    private val activityMainBinding get() = _activityMainBinding!!
    private lateinit var classifierHelper: TextClassificationHelper
    private val adapter by lazy {
        ResultsAdapter()
    }

    private val listener = object : TextClassificationHelper.TextResultsListener {
        override fun onResult(results: List<Category>, inferenceTime: Long) {
            runOnUiThread {

                adapter.resultsList = results.sortedByDescending {
                    it.score
                }

                adapter.notifyDataSetChanged()
            }
        }

        override fun onError(error: String) {
            Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // Create the classification helper that will do the heavy lifting
        classifierHelper = TextClassificationHelper(
            context = this@MainActivity,
            listener = listener)


        replaceFragment(HomeFragment())


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
}