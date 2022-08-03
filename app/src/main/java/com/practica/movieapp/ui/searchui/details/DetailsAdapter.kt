package com.practica.movieapp.ui.searchui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.practica.movieapp.R
import com.practica.movieapp.data.movies.Movie

class DetailsAdapter(
    private val movie: Movie,
) : RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rdTitle = view.findViewById<TextView>(R.id.tvRdTitle)!!
        val rdOrigTitle = view.findViewById<TextView>(R.id.tvRdOrigTitleContent)!!
        val rdReleaseDate = view.findViewById<TextView>(R.id.tvRdReleaseDateContent)!!
        val rdOverview = view.findViewById<TextView>(R.id.tvRdOverviewContent)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_details, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.rdTitle.text = movie.title
        holder.rdOrigTitle.text = movie.originalTitle
        holder.rdReleaseDate.text = movie.releaseDate
        holder.rdOverview.text = movie.overview
    }

    override fun getItemCount() = 1
}