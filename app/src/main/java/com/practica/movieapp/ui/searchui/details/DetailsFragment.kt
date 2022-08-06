package com.practica.movieapp.ui.searchui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.practica.movieapp.R
import com.practica.movieapp.data.ImageHandler
import com.practica.movieapp.data.movies.Movie
import com.practica.movieapp.data.movies.MovieDetails
import com.practica.movieapp.data.movies.get.MoviesRepository
import com.practica.movieapp.databinding.FragmentDetailsBinding
import com.practica.movieapp.ui.searchui.search.MoviesAdapter
import kotlinx.coroutines.*

class DetailsFragment : Fragment() {

    private lateinit var _binding: FragmentDetailsBinding
    private val binding get() = _binding

    private var favorite: Boolean = false
    private var watched: Boolean = false

    private lateinit var model: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model = ViewModelProvider(requireActivity())[DetailsViewModel::class.java]

        setupGenresRecyclerViewLayout()
        getMovieDetailsToBeShown()
        configureButtons()
    }

    private fun setupGenresRecyclerViewLayout() {
        val horizontalRecyclerView = binding.rvFdGenres

        val linearLayoutManager = LinearLayoutManager(this.context)

        with (linearLayoutManager) {
            orientation = LinearLayoutManager.HORIZONTAL
            reverseLayout = false
        }

        horizontalRecyclerView.layoutManager = linearLayoutManager
    }

    private fun getMovieDetailsToBeShown() {
        model.movieDetails.observe(viewLifecycleOwner) {
            it?.let {
                downloadPosterImage(it)
                setupTextViews(it)
                setupGenresBar(it)
                configureVideoPlayer(it)
            }
        }
    }

    private fun configureButtons() {
        model.favorite.value?.let { favorite = it }
        model.watched.value?.let { watched = it }

        updateFavoriteButton(favorite)
        updateWatchedButton(watched)

        binding.ibFdFavorite.setOnClickListener {
            favorite = !favorite
            updateFavoriteButton(favorite)
        }

        binding.ibFdWatched.setOnClickListener {
            watched = !watched
            updateWatchedButton(watched)
        }
    }

    private fun downloadPosterImage(movieDetails: MovieDetails) {
        val imageView = binding.ivFdImage
        ImageHandler.downloadH632ImageWithPath(imageView.context, movieDetails.posterPath, imageView)
    }

    private fun setupTextViews(movieDetails: MovieDetails) {
        val title = binding.tvFdTitle
        val originalTitle = binding.tvFdOrigTitle
        val releaseDate = binding.tvFdReleaseDate
        val overview = binding.tvFdOverview

        with(movieDetails) {
            title.text = this.title
            originalTitle.text = this.originalTitle
            releaseDate.text = this.releaseDate
            overview.text = this.overview
        }
    }

    private fun setupGenresBar(movieDetails: MovieDetails) {
        with(binding.rvFdGenres) {
            adapter = DetailsGenresBarAdapter(movieDetails.genres)
        }
    }

    private fun configureVideoPlayer(movieDetails: MovieDetails) {
        val ytPlayer = binding.ytpPlayer
        lifecycle.addObserver(ytPlayer)

        var key = ""
        for (i in movieDetails.videos) {
            if (i.site == "YouTube" && i.type == "Trailer") {
                key = i.key
                break
            }
        }
        if (key.isEmpty() && movieDetails.videos.isNotEmpty()) key = movieDetails.videos[0].key

        ytPlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                val videoId = key
                youTubePlayer.loadVideo(videoId, 0.0f)
            }
        })
    }

    private fun updateFavoriteButton(flag: Boolean) {
            binding.ibFdFavorite.setImageResource(
                when (flag) {
                    true -> R.drawable.ic_heart_favorite_fill
                    else -> R.drawable.ic_heart_favorite_border
                }
            )
    }

    private fun updateWatchedButton(flag: Boolean) {
        binding.ibFdWatched.setImageResource(
            when (flag) {
                true -> R.drawable.ic_saved_fill
                else -> R.drawable.ic_saved_border
            }
        )
    }
}