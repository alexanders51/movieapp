package com.practica.movieapp.ui.searchui.saved.tabs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.practica.movieapp.R
import com.practica.movieapp.data.ImageHandler
import com.practica.movieapp.data.movies.Movie
import com.practica.movieapp.data.movies.get.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WatchedMoviesAdapter(
    private val moviesList: ArrayList<Movie>
) : RecyclerView.Adapter<WatchedMoviesAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var favorite: Boolean = false
        var watched: Boolean = false

        val itemIvMovie = view.findViewById<ImageView>(R.id.ivMovie)!!
        val itemIvTitle = view.findViewById<TextView>(R.id.tvTitle)!!
        val itemIvOriginalTitle = view.findViewById<TextView>(R.id.tvOriginalTitle)!!
        val itemIvReleaseDate = view.findViewById<TextView>(R.id.tvReleaseDate)!!
        val itemIvOverview = view.findViewById<TextView>(R.id.tvOverview)!!
        val itemBtnDelete = view.findViewById<ImageButton>(R.id.btnDelete)!!
    }

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val moviesRep: MoviesRepository = MoviesRepository.instance

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item_list_watched, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = moviesList[position]

        ImageHandler.downloadH632ImageWithPath(
            holder.itemIvMovie.context,
            movie.posterPath,
            holder.itemIvMovie
        )

        holder.itemIvTitle.text = movie.title
        holder.itemIvOriginalTitle.text = movie.originalTitle
        holder.itemIvOverview.text = movie.overview
        holder.itemIvReleaseDate.text = movie.releaseDate

        holder.favorite = moviesList[position].isFavorite
        holder.watched = moviesList[position].isWatched

        holder.itemBtnDelete.setOnClickListener {
            updateItem(moviesList[position])
            moviesList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, moviesList.size)
        }
    }

    private fun updateItem(movie: Movie) {
        CoroutineScope(ioDispatcher).launch {
            val saved = ArrayList(moviesRep.getAllLocalMovies())
            val idx = saved.indexOf(movie)
            if (idx != -1) saved[idx].isWatched = !saved[idx].isWatched
            if (!saved[idx].isFavorite && !saved[idx].isWatched) {
                moviesRep.deleteLocal(saved[idx])
                saved.removeAt(idx)
            }
            moviesRep.replaceAllLocal(saved.toList())
        }
    }

    override fun getItemCount() = moviesList.size
}
