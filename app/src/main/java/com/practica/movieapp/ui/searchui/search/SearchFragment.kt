package com.practica.movieapp.ui.searchui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practica.movieapp.R
import com.practica.movieapp.data.RemoteDataRetriever
import com.practica.movieapp.data.movies.Movie
import kotlinx.coroutines.*

class SearchFragment : Fragment(R.layout.fragment_search) {
    private var movies : List<Movie> = emptyList()
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val mainDispatcher: MainCoroutineDispatcher = Dispatchers.Main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(ioDispatcher).launch {
            RemoteDataRetriever.updateMovies()
            movies = RemoteDataRetriever.getPreloadedMovies()
            withContext(mainDispatcher) {
                setupRecyclerView(view)
            }
        }
    }

    private fun setupRecyclerView(view: View) {
        val llm = LinearLayoutManager(view.context)
        val rv = view.findViewById<RecyclerView>(R.id.rvMovieItems)

        llm.orientation = LinearLayoutManager.VERTICAL
        llm.reverseLayout = false

        rv.layoutManager = llm
        rv.adapter = MoviesAdapter(movies)
    }
}