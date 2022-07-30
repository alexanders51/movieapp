package com.practica.movieapp.ui.searchui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.practica.movieapp.R
import com.practica.movieapp.data.ImageDownloadManager
import com.practica.movieapp.data.movies.Movie

class MoviesAdapter(private val moviesList: List<Movie>) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemParent: ConstraintLayout = view.findViewById<ConstraintLayout>(R.id.itemParent)
        val itemIvMovie: ImageView = view.findViewById<ImageView>(R.id.ivMovie)
        val itemIvTitle: TextView = view.findViewById<TextView>(R.id.tvTitle)
        val itemIvOriginalTitle: TextView = view.findViewById<TextView>(R.id.tvOriginalTitle)
        val itemIvReleaseDate: TextView = view.findViewById<TextView>(R.id.tvReleaseDate)
        val itemIvOverview: TextView = view.findViewById<TextView>(R.id.tvOverview)
        val itemBtnFavorite: ImageButton = view.findViewById<ImageButton>(R.id.btnSelFavorite)
        val itemBtnWatched: ImageButton = view.findViewById<ImageButton>(R.id.btnSelWatched)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = moviesList[position]

        ImageDownloadManager.downloadH632ImageWithPath(holder.itemIvMovie.context, movie.posterPath, holder.itemIvMovie)

        holder.itemIvTitle.text = movie.title + if (movie.adult) " - NSFW" else ""
        holder.itemIvOriginalTitle.text = movie.originalTitle
        holder.itemIvOverview.text = movie.overview
        holder.itemIvReleaseDate.text = movie.releaseDate

        holder.itemBtnFavorite.setOnClickListener {

        }
    }

    override fun getItemCount(): Int = moviesList.size
}