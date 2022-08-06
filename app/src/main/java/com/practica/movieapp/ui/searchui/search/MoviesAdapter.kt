package com.practica.movieapp.ui.searchui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practica.movieapp.R
import com.practica.movieapp.data.ImageHandler
import com.practica.movieapp.data.movies.Movie
import com.practica.movieapp.data.movies.get.MoviesRepository
import com.practica.movieapp.databinding.RvItemMovieBinding
import com.practica.movieapp.ui.searchui.details.DetailsViewModel
import kotlinx.coroutines.*

class MoviesAdapter(
    private val items: List<Movie>,
    private val viewModel: DetailsViewModel,
    private val callback: () -> Unit
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RvItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        var favorite: Boolean = false
        var watched: Boolean = false
    }

    private val mainDispatcher: MainCoroutineDispatcher = Dispatchers.Main
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val moviesRep: MoviesRepository = MoviesRepository.instance

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(items[position]) {
                ImageHandler.downloadH632ImageWithPath(
                    binding.ivImPoster.context,
                    this.posterPath,
                    binding.ivImPoster
                )

                binding.tvImTitle.text = this.title
                binding.tvImOriginalTitle.text = this.originalTitle
                binding.tvImReleaseDate.text = this.releaseDate
                binding.tvImOverview.text = this.overview

                favorite = this.isFavorite
                watched = this.isWatched
            }

            updateFavoriteButton(this)
            updateWatchedButton(this)

            with(items[position]) {
                val id = this.id
                viewModel.movieObject.postValue(this)
                viewModel.favorite.postValue(favorite)
                viewModel.watched.postValue(watched)

                binding.clImParent.setOnClickListener {
                    CoroutineScope(ioDispatcher).launch {
                        viewModel.movieDetails.postValue(moviesRep.getRemoteMovieDetailsById(id))
                        withContext(mainDispatcher) {
                            callback.invoke()
                        }
                    }
                }

                binding.btnImFavorite.setOnClickListener {
                    favorite = !favorite
                    this.isFavorite = favorite
                    updateFavoriteButton(holder)
                    updateDatabase(this)
                }

                binding.btnImWatched.setOnClickListener {
                    watched = !watched
                    this.isWatched = watched
                    updateWatchedButton(holder)
                    updateDatabase(this)
                }
            }
        }
    }

    private fun updateFavoriteButton(holder: ViewHolder) {
        with(holder) {
            binding.btnImFavorite.setImageResource(
                when (holder.favorite) {
                    true -> R.drawable.ic_heart_favorite_fill
                    else -> R.drawable.ic_heart_favorite_border
                }
            )
        }
    }

    private fun updateWatchedButton(holder: ViewHolder) {
        with(holder) {
            binding.btnImWatched.setImageResource(
                when (holder.watched) {
                    true -> R.drawable.ic_saved_fill
                    else -> R.drawable.ic_saved_border
                }
            )
        }
    }

    private fun filterWithFlags() = items.filter { it.isFavorite || it.isWatched }

    private fun updateDatabase(item: Movie) {
        CoroutineScope(ioDispatcher).launch {
            val saved = ArrayList<Movie>(moviesRep.getAllLocalMovies())
            val filtered = ArrayList<Movie>(filterWithFlags())

            saved.remove(item)

            moviesRep.replaceAllLocal(saved.union(filtered).toList())
        }
    }

    override fun getItemCount(): Int = items.size
}