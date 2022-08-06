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
import com.practica.movieapp.R
import com.practica.movieapp.data.DataHandler
import com.practica.movieapp.data.movies.Movie
import com.practica.movieapp.databinding.FragmentSearchBinding
import com.practica.movieapp.ui.searchui.details.DetailsViewModel
import kotlinx.coroutines.*
import java.net.URLEncoder

class SearchFragment : Fragment() {

    private var movies: List<Movie> = emptyList()

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val mainDispatcher: MainCoroutineDispatcher = Dispatchers.Main

    private lateinit var _binding: FragmentSearchBinding
    private val binding get() = _binding

    private lateinit var viewModel: DetailsViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[DetailsViewModel::class.java]
        navController = findNavController()

        setupRecyclerViewLayout()
        updateMovies()
        setupListeners()
    }

    private fun updateMovies() {
        CoroutineScope(ioDispatcher).launch {
            getRemoteMovies()
            withContext(mainDispatcher) {
                setupRecyclerViewAdapter()
            }
        }
    }

    private fun updateMoviesWithQuery(query: String) {
        CoroutineScope(ioDispatcher).launch {
            getRemoteMoviesWithQuery(query)
            withContext(mainDispatcher) {
                setupRecyclerViewAdapter()
            }
        }
    }


    private fun setupRecyclerViewLayout() {
        val recyclerView = binding.rvFsMovies

        val linearLayoutManager = LinearLayoutManager(this.context)

        with(linearLayoutManager) {
            orientation = LinearLayoutManager.VERTICAL
            reverseLayout = false
        }

        with(recyclerView) {
            layoutManager = linearLayoutManager
        }
    }

    private fun setupRecyclerViewAdapter() {
        with(binding.rvFsMovies) {
            adapter = MoviesAdapter(movies, viewModel) {
                navigateToSpecifiedDestination(R.id.navDetails)
            }
        }
    }

    private fun setupListeners() {
        val searchView = binding.searchviewFs
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String?) = false

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    val encoded = URLEncoder.encode(it, "utf-8")
                    with(encoded) {
                        if (this.isNotEmpty()) updateMoviesWithQuery(this)
                        else updateMovies()
                    }
                }
                return true
            }
        })
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