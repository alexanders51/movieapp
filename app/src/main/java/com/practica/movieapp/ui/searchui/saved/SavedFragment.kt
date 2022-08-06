package com.practica.movieapp.ui.searchui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.practica.movieapp.R
import com.practica.movieapp.databinding.FragmentSavedBinding
import com.practica.movieapp.ui.searchui.saved.tabs.SavedViewPagerAdapter
import com.practica.movieapp.ui.searchui.saved.tabs.FavoriteFragment
import com.practica.movieapp.ui.searchui.saved.tabs.WatchedFragment


class SavedFragment : Fragment() {
    private val tabTitles = arrayOf(
        R.string.tab_text_favorite,
        R.string.tab_text_watched
    )

    private lateinit var _binding: FragmentSavedBinding
    private val binding get() = _binding

    private lateinit var tabs: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var savedViewPagerAdapter: SavedViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with (binding) {
            tabs = this.ftFsaTabs
            viewPager2 = this.vp2FsaViewPager

            refreshAdapter(tabs, viewPager2)
        }
    }

    override fun onResume() {
        super.onResume()
        refreshAdapter(tabs, viewPager2)
    }

    private fun refreshAdapter(tabs: TabLayout, viewPager2: ViewPager2) {
        savedViewPagerAdapter = SavedViewPagerAdapter(requireActivity())

        with(savedViewPagerAdapter) {
            with(tabTitles) {
                addFragment(FavoriteFragment(), getString(this[0]))
                addFragment(WatchedFragment(), getString(this[1]))
            }

            viewPager2.adapter = savedViewPagerAdapter
            viewPager2.currentItem = 0

            TabLayoutMediator(tabs, viewPager2) { tab, position ->
                tab.text = this.getPageTitle(position)
            }.attach()
        }
    }
}