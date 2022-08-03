package com.practica.movieapp.ui.searchui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practica.movieapp.data.movies.Movie

class DetailsViewModel : ViewModel() {
    private val currentMovie = MutableLiveData<Movie?>()

    fun getCurrentMovie() = currentMovie

    fun setCurrentMovie(movie: Movie?) {
        currentMovie.value = movie
    }
}