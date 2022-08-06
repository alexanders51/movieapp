package com.practica.movieapp.ui.searchui.saved.tabs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practica.movieapp.data.ImageHandler
import com.practica.movieapp.data.movies.Movie
import com.practica.movieapp.data.movies.get.MoviesRepository
import com.practica.movieapp.databinding.RvItemMovieDeleteBinding
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteMoviesAdapter(
    private val items: MutableList<Movie>
) : RecyclerView.Adapter<FavoriteMoviesAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RvItemMovieDeleteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var favorite: Boolean = false
        var watched: Boolean = false
    }

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val moviesRep: MoviesRepository = MoviesRepository.instance

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RvItemMovieDeleteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(items[position]) {
                ImageHandler.downloadH632ImageWithPath(
                    binding.ivImdPoster.context,
                    this.posterPath,
                    binding.ivImdPoster
                )

                binding.tvImdTitle.text = this.title
                binding.tvImdOriginalTitle.text = this.originalTitle
                binding.tvImdReleaseDate.text = this.releaseDate
                binding.tvImdOverview.text = this.overview

                favorite = this.isFavorite
                watched = this.isWatched

                binding.btnImdDelete.setOnClickListener {
                    updateItem(items[position])
                    items.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, items.size)
                }
            }
        }
    }

    private fun updateItem(movie: Movie) {
        CoroutineScope(ioDispatcher).launch {
            val saved = ArrayList(moviesRep.getAllLocalMovies())
            val idx = saved.indexOf(movie)
            if (idx != -1) saved[idx].isFavorite = !saved[idx].isFavorite
            if (!saved[idx].isFavorite && !saved[idx].isWatched) {
                moviesRep.deleteLocal(saved[idx])
                saved.removeAt(idx)
            }
            moviesRep.replaceAllLocal(saved.toList())
        }
    }

    override fun getItemCount() = items.size
}