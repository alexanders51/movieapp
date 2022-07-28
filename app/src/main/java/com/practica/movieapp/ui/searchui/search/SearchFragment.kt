package com.practica.movieapp.ui.searchui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practica.movieapp.R
import com.practica.movieapp.data.RemoteDataRetriever
import com.practica.movieapp.data.movies.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var movies : List<Movie> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch (Dispatchers.IO) {
            RemoteDataRetriever.updateMovies()
            movies = RemoteDataRetriever.getPreloadedMovies()
            withContext(Dispatchers.Main) {
                setupRecyclerView(view)
            }
        }
    }

    private fun setupRecyclerView(view: View) {
        val rvItems = view.findViewById<RecyclerView>(R.id.rvMovieItems)
        rvItems.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        rvItems.adapter = SearchMoviesAdapter(movies)
    }
}