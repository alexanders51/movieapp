package com.practica.movieapp.ui.searchui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practica.movieapp.data.movies.Movie
import com.practica.movieapp.data.movies.MovieDetails
import com.practica.movieapp.data.movies.get.MoviesRepository

class DetailsViewModel : ViewModel() {
    val movieDetails = MutableLiveData<MovieDetails>()
    val movieObject = MutableLiveData<Movie>()
    val favorite = MutableLiveData<Boolean>()
    val watched = MutableLiveData<Boolean>()
}