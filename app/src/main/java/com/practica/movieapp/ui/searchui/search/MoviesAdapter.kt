package com.practica.movieapp.ui.searchui.search

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
import kotlinx.coroutines.*

class MoviesAdapter(
    private val moviesList: List<Movie>,
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var favorite: Boolean = false
        var watched: Boolean = false

        val itemIvMovie = view.findViewById<ImageView>(R.id.ivMovie)!!
        val itemIvTitle = view.findViewById<TextView>(R.id.tvTitle)!!
        val itemIvOriginalTitle = view.findViewById<TextView>(R.id.tvOriginalTitle)!!
        val itemIvReleaseDate = view.findViewById<TextView>(R.id.tvReleaseDate)!!
        val itemIvOverview = view.findViewById<TextView>(R.id.tvOverview)!!
        val itemBtnFavorite = view.findViewById<ImageButton>(R.id.btnSelFavorite)!!
        val itemBtnWatched = view.findViewById<ImageButton>(R.id.btnSelWatched)!!
    }

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val moviesRep: MoviesRepository = MoviesRepository.instance

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = moviesList[position]

        ImageHandler.downloadH632ImageWithPath(holder.itemIvMovie.context, movie.posterPath, holder.itemIvMovie)

        holder.itemIvTitle.text = movie.title
        holder.itemIvOriginalTitle.text = movie.originalTitle
        holder.itemIvOverview.text = movie.overview
        holder.itemIvReleaseDate.text = movie.releaseDate

        holder.favorite = moviesList[position].isFavorite
        holder.watched = moviesList[position].isWatched

        updateFavoriteButton(holder)
        updateWatchedButton(holder)

        holder.itemBtnFavorite.setOnClickListener {
            holder.favorite = !holder.favorite
            moviesList[position].isFavorite = holder.favorite
            updateFavoriteButton(holder)
            updateDatabase(moviesList[position])
        }

        holder.itemBtnWatched.setOnClickListener {
            holder.watched = !holder.watched
            moviesList[position].isWatched = holder.watched
            updateWatchedButton(holder)
            updateDatabase(moviesList[position])
        }
    }

    private fun updateFavoriteButton(holder: ViewHolder) {
        holder.itemBtnFavorite.setImageResource(when(holder.favorite) {
            true -> R.drawable.ic_corason_fill
            else -> R.drawable.ic_corason_border
        })
    }

    private fun updateWatchedButton(holder: ViewHolder) {
        holder.itemBtnWatched.setImageResource(when(holder.watched) {
            true -> R.drawable.ic_saved_fill
            else -> R.drawable.ic_saved_border
        })
    }

    private fun filterWithFlags() = moviesList.filter { it.isFavorite || it.isWatched }

    private fun updateDatabase(item: Movie) {
        CoroutineScope(ioDispatcher).launch {
            val saved = ArrayList<Movie>(moviesRep.getAllLocalMovies())
            val filtered = ArrayList<Movie>(filterWithFlags())

            saved.remove(item)

            moviesRep.replaceAllLocal(saved.union(filtered).toList())
        }
    }

    override fun getItemCount(): Int = moviesList.size
}