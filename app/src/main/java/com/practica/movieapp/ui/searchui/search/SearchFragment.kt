package com.practica.movieapp.ui.searchui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practica.movieapp.R
import com.practica.movieapp.data.DataHandler
import com.practica.movieapp.data.movies.Movie
import com.practica.movieapp.ui.searchui.details.DetailsViewModel
import kotlinx.coroutines.*
import java.net.URLEncoder

class SearchFragment : Fragment() {
    private var movies: List<Movie> = emptyList()

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val mainDispatcher: MainCoroutineDispatcher = Dispatchers.Main

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var navController: NavController

    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[DetailsViewModel::class.java]

        navController = findNavController()

        updateMovies(view)
        setupListeners(view)
    }

    private fun setupRecyclerView(view: View) {
        val llm = LinearLayoutManager(view.context)
        val rv = view.findViewById<RecyclerView>(R.id.rvMovieItems)

        moviesAdapter = MoviesAdapter(movies, {
            navigateToSpecifiedDestination(R.id.navDetails)
        }, viewModel)

        llm.orientation = LinearLayoutManager.VERTICAL
        llm.reverseLayout = false

        rv.layoutManager = llm
        rv.adapter = moviesAdapter
    }

    private fun setupListeners(view: View) {
        val svMovies = view.findViewById<SearchView>(R.id.svMovies)
        svMovies.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {
                when (newText != null) {
                    true -> {
                        val encoded = URLEncoder.encode(newText, "utf-8")
                        if (encoded.isNotEmpty()) updateMoviesWithQuery(
                            view,
                            encoded
                        ) else updateMovies(view)
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
        preselectItems()
    }

    private fun getRemoteMoviesWithQuery(query: String) {
        DataHandler.updateLocal()
        movies = DataHandler.queryMovies(query)
        preselectItems()
    }

    private fun preselectItems() {
        val saved = DataHandler.getLocalMovies()
        movies.forEach {
            val idx = saved.indexOf(it)
            it.isFavorite = (idx != -1) && saved[idx].isFavorite
            it.isWatched = (idx != -1) && saved[idx].isWatched
        }
    }

    private fun navigateToSpecifiedDestination(@IdRes destination: Int) {
        navController.navigate(destination)
    }
}