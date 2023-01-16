package org.tensorflow.lite.examples.textclassification.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.tensorflow.lite.examples.textclassification.fragments.AnalysisFragment
import org.tensorflow.lite.examples.textclassification.fragments.HomeFragment
import org.tensorflow.lite.examples.textclassification.fragments.QuestionsFragment
import org.tensorflow.lite.examples.textclassification.fragments.video.*

class VideoAdapter (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity)  {
    override fun getItemCount() = 5

    override fun createFragment(position: Int): Fragment {
        // switch to the selected item
        return when (position) {
            0 -> Video1Fragment()
            1 -> Video2Fragment()
            2 -> Video3Fragment()
            3 -> Video4Fragment()
            4 -> Video5Fragment()
            else -> Video1Fragment()
        }
    }

}