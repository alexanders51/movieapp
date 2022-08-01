package com.practica.movieapp.ui.searchui.saved

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.practica.movieapp.R
import com.practica.movieapp.ui.searchui.saved.tabs.AdapterTabPager
import com.practica.movieapp.ui.searchui.saved.tabs.FavoriteFragment
import com.practica.movieapp.ui.searchui.saved.tabs.WatchedFragment


class SavedFragment : Fragment(R.layout.fragment_saved) {
    private val tabTitles = arrayOf(
        R.string.tab_text_favorite,
        R.string.tab_text_watched
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = activity?.let { AdapterTabPager(it) }
        val viewPager = view.findViewById<ViewPager2>(R.id.ftViewPager)
        val tabs: TabLayout = view.findViewById(R.id.ftTabs)

        adapter?.addFragment(FavoriteFragment(), getString(tabTitles[0]))
        adapter?.addFragment(WatchedFragment(), getString(tabTitles[1]))

        viewPager.adapter = adapter!!
        viewPager.currentItem = 0
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = adapter.getPageTitle(position)
        }.attach()
    }
}