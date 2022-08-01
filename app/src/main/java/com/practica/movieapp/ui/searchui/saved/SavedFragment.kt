package com.practica.movieapp.ui.searchui.saved

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.practica.movieapp.R
import com.practica.movieapp.data.DataHandler
import com.practica.movieapp.data.movies.Movie
import com.practica.movieapp.ui.searchui.saved.tabs.*
import kotlinx.coroutines.*


class SavedFragment : Fragment(R.layout.fragment_saved) {
    private val tabTitles = arrayOf(
        R.string.tab_text_favorite,
        R.string.tab_text_watched
    )

    private var movies: ArrayList<Movie> = ArrayList()

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val mainDispatcher: MainCoroutineDispatcher = Dispatchers.Main

    private var adapterTabPager: AdapterTabPager? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<ViewPager2>(R.id.ftViewPager)
        val tabs: TabLayout = view.findViewById(R.id.ftTabs)

        refreshAdapter(tabs, viewPager)

    }

    override fun onResume() {
        super.onResume()

        val viewPager = view?.findViewById<ViewPager2>(R.id.ftViewPager)
        val tabs: TabLayout? = view?.findViewById(R.id.ftTabs)

        refreshAdapter(tabs!!, viewPager!!)
    }

    private fun refreshAdapter(tabs: TabLayout, viewPager: ViewPager2) {
        CoroutineScope(ioDispatcher).launch {
            DataHandler.updateLocal()
            withContext(mainDispatcher) {
                movies = ArrayList(DataHandler.getLocalMovies())

                adapterTabPager = activity?.let { AdapterTabPager(it) }

                adapterTabPager?.addFragment(FavoriteFragment(movies), getString(tabTitles[0]))
                adapterTabPager?.addFragment(WatchedFragment(movies), getString(tabTitles[1]))

                viewPager.adapter = adapterTabPager!!
                viewPager.currentItem = 0
                TabLayoutMediator(tabs, viewPager) { tab, position ->
                    tab.text = adapterTabPager?.getPageTitle(position)
                }.attach()
            }
        }
    }
}