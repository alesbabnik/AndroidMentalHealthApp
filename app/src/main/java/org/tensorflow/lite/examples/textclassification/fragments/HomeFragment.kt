package org.tensorflow.lite.examples.textclassification.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import org.tensorflow.lite.examples.textclassification.R
import org.tensorflow.lite.examples.textclassification.activities.MainActivity
import org.tensorflow.lite.examples.textclassification.adapters.VideoAdapter
import org.tensorflow.lite.examples.textclassification.adapters.ViewPagerAdapter


class HomeFragment : Fragment() {
    companion object {
        fun newInstance() = HomeFragment()
    }
    private lateinit var videoPager: ViewPager2
    private lateinit var videoLayout: TabLayout

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val main = activity as MainActivity
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        videoLayout = view.findViewById<TabLayout>(R.id.videoLayout)
        videoPager = view.findViewById<ViewPager2>(R.id.videoPager)

        videoPager.adapter = VideoAdapter(requireActivity())
        TabLayoutMediator(videoLayout, videoPager){ tab,index ->
            tab.text = when(index) {
                0 -> {"Intro"}
                1 -> {"Sleep"}
                2 -> {"Diet"}
                3 -> {"Stress"}
                4 -> {"Alcohol"}
                else ->{""}
            }
        }.attach()


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }
}