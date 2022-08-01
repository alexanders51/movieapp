package com.practica.movieapp.ui.searchui.saved.tabs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practica.movieapp.R
import com.practica.movieapp.data.DataHandler
import com.practica.movieapp.data.movies.Movie
import kotlinx.coroutines.*

class FavoriteFragment(
    private var movies: ArrayList<Movie>
) : Fragment(R.layout.fragment_favorite) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(view)
    }

    private fun setupRecyclerView(view: View) {
        val llm = LinearLayoutManager(view.context)
        val rv = view.findViewById<RecyclerView>(R.id.rvFavoriteMoviesList)

        llm.orientation = LinearLayoutManager.VERTICAL
        llm.reverseLayout = false

        rv.layoutManager = llm
        rv.adapter = FavoriteMoviesAdapter(ArrayList(getFavorite()))
    }

    private fun getFavorite() = movies.filter { it.isFavorite }
}