package com.practica.movieapp.ui.searchui.saved

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.practica.movieapp.R
import com.practica.movieapp.ui.searchui.saved.tabs.SectionsPagerAdapter

class SavedFragment : Fragment(R.layout.fragment_saved) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionsPagerAdapter = SectionsPagerAdapter(view.context, childFragmentManager)
        val viewPager: ViewPager = view.findViewById(R.id.ftViewPager)
        val tabs: TabLayout = view.findViewById(R.id.ftTabs)

        viewPager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(viewPager)
    }
}