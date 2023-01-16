package org.tensorflow.lite.examples.textclassification.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.tensorflow.lite.examples.textclassification.fragments.AnalysisFragment
import org.tensorflow.lite.examples.textclassification.fragments.HomeFragment
import org.tensorflow.lite.examples.textclassification.fragments.QuestionsFragment

// fragment state adapter for viewpager2
class ViewPagerAdapter (fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity)  {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        // switch to the selected item
        return when (position) {
            0 -> HomeFragment()
            1 -> QuestionsFragment()
            2 -> AnalysisFragment()
            else -> HomeFragment()
        }
    }

}