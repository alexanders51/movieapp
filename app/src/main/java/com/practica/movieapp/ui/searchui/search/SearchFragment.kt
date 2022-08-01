package com.practica.movieapp.ui.searchui.search

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practica.movieapp.R
import com.practica.movieapp.data.DataHandler
import com.practica.movieapp.data.movies.Movie
import kotlinx.coroutines.*
import java.net.URLEncoder

class SearchFragment : Fragment(R.layout.fragment_search) {
    private var movies : List<Movie> = emptyList()
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val mainDispatcher: MainCoroutineDispatcher = Dispatchers.Main

    private lateinit var moviesAdapter: MoviesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateMovies(view)
        setupListeners(view)
    }

    private fun setupRecyclerView(view: View) {
        val llm = LinearLayoutManager(view.context)
        val rv = view.findViewById<RecyclerView>(R.id.rvMovieItems)

        moviesAdapter = MoviesAdapter(movies)

        llm.orientation = LinearLayoutManager.VERTICAL
        llm.reverseLayout = false

        rv.layoutManager = llm
        rv.adapter = moviesAdapter
    }

    private fun setupListeners(view: View) {
        val svMovies = view.findViewById<SearchView>(R.id.svMovies)
        svMovies.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(newText: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {
                when (newText != null) {
                    true -> {
                        val encoded = URLEncoder.encode(newText, "utf-8")
                        if (encoded.isNotEmpty()) updateMoviesWithQuery(view, encoded) else updateMovies(view)
                    }
                    else -> updateMovies(view)
                }
                return true
            }
        })
    }

    private fun updateMovies(view: View) {
        CoroutineScope(ioDispatcher).launch {
            getRemoteMovies()
            withContext(mainDispatcher) {
                setupRecyclerView(view)
            }
        }
    }

    private fun updateMoviesWithQuery(view: View, query: String) {
        CoroutineScope(ioDispatcher).launch {
            getRemoteMoviesWithQuery(query)
            withContext(mainDispatcher) {
                setupRecyclerView(view)
            }
        }
    }

    private fun getRemoteMovies() {
        DataHandler.updateMovies()
        DataHandler.updateLocal()

        movies = DataHandler.getPreloadedMovies()
        updateBooleanFieldsOnMovieList()
    }

    private fun getRemoteMoviesWithQuery(query: String) {
        DataHandler.updateLocal()

        movies = DataHandler.queryMovies(query)
        updateBooleanFieldsOnMovieList()
    }

    private fun updateBooleanFieldsOnMovieList() {
        val localMovies = DataHandler.getLocalMovies()
        movies.forEach {
            if (localMovies.contains(it)) {
                val found = localMovies.indexOf(it)
                it.isFavorite = localMovies[found].isFavorite
                it.isWatched = localMovies[found].isWatched
            }
        }
    }
}