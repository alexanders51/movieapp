package com.practica.movieapp.ui.searchui.saved.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.practica.movieapp.data.movies.Movie
import com.practica.movieapp.data.movies.get.MoviesRepository
import com.practica.movieapp.databinding.FragmentWatchedBinding
import kotlinx.coroutines.*

class WatchedFragment : Fragment() {

    private val movieRep: MoviesRepository = MoviesRepository.instance
    private var movies: MutableList<Movie> = mutableListOf()
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val mainDispatcher: MainCoroutineDispatcher = Dispatchers.Main

    private lateinit var _binding: FragmentWatchedBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWatchedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeListOfMovies()
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this.context)
        val recyclerView = binding.rvWfMovies

        with (linearLayoutManager) {
            orientation = LinearLayoutManager.VERTICAL
            reverseLayout = false
        }

        with(recyclerView) {
            layoutManager = linearLayoutManager
            adapter = WatchedMoviesAdapter(movies)
        }
    }

    private fun initializeListOfMovies() {
        CoroutineScope(ioDispatcher).launch {
            movies = movieRep.getWatched().toMutableList()
            withContext(mainDispatcher) {
                setupRecyclerView()
            }
        }
    }

}