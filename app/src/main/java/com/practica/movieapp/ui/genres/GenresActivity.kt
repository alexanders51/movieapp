package com.practica.movieapp.ui.genres

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.practica.movieapp.R
import com.practica.movieapp.data.DataHandler
import com.practica.movieapp.data.genres.Genre
import com.practica.movieapp.data.genres.get.GenreRepository
import com.practica.movieapp.databinding.ActivityGenresBinding
import kotlinx.coroutines.*

class GenresActivity : AppCompatActivity() {

    private var genres: List<Genre> = emptyList()
    private var genreRepository = GenreRepository.instance

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val mainDispatcher: MainCoroutineDispatcher = Dispatchers.Main

    private lateinit var _binding: ActivityGenresBinding
    private val binding get() = _binding

    companion object {
        const val GENRES_RETURN_DATA: String = "genresReturnData"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)

        _binding = ActivityGenresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerViewLayout()
        getGenres()
        setOnClickListeners()
    }

    private fun getGenres() {
        CoroutineScope(ioDispatcher).launch {
            genres = DataHandler.getPreloadedGenres()
            withContext(mainDispatcher) {
                preselectItems()
            }
        }
    }

    private fun preselectItems() {
        CoroutineScope(ioDispatcher).launch {
            val saved = genreRepository.getAllLocalGenres()
            withContext(mainDispatcher) {
                genres.forEach { genre -> genre.isSelected = saved.contains(genre) }
                setupRecyclerViewAdapter()
            }
        }
    }

    private fun filterSelected() = genres.filter { genre -> genre.isSelected }

    private fun setupRecyclerViewLayout() {
        val recyclerView = binding.rvAgGenres

        val linearLayoutManager = LinearLayoutManager(this)

        with(linearLayoutManager) {
            orientation = LinearLayoutManager.VERTICAL
            reverseLayout = false
        }

        with(recyclerView) {
            layoutManager = linearLayoutManager
        }
    }

    private fun setupRecyclerViewAdapter() {
        with(binding.rvAgGenres) {
            adapter = GenresAdapter(genres)
        }
    }

    private fun setOnClickListeners() {
        val fabSubmit = findViewById<FloatingActionButton>(R.id.fab_ag_submit)
        fabSubmit.setOnClickListener {
            updateDatabase()
        }
    }

    private fun updateDatabase() {
        CoroutineScope(ioDispatcher).launch {
            genreRepository.replaceAllLocal(filterSelected())
            DataHandler.updateMovies()
            withContext(mainDispatcher) {
                setResult(Activity.RESULT_OK, putReturnData())
                finish()
            }
        }
    }

    private fun putReturnData(): Intent {
        val selected = genres.filter { it.isSelected }.map { it.name }
        val returnData = GenresReturnData(selected.size, selected.toTypedArray())
        val intent = Intent()
        intent.putExtra(GENRES_RETURN_DATA, returnData)
        return intent
    }
}
