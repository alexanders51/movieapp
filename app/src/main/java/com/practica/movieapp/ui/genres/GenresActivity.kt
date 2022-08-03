package com.practica.movieapp.ui.genres

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.practica.movieapp.R
import com.practica.movieapp.data.DataHandler
import com.practica.movieapp.data.genres.Genre
import com.practica.movieapp.data.genres.get.GenreRepository
import kotlinx.coroutines.*

class GenresActivity : AppCompatActivity() {
    private var genres: List<Genre> = emptyList()
    private var genreRepository = GenreRepository.instance
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val mainDispatcher: MainCoroutineDispatcher = Dispatchers.Main

    companion object {
        const val GENRES_RETURN_DATA: String = "genresReturnData"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genres)
        setOnClickListeners()
        getGenres()
    }

    private fun getGenres() {
        CoroutineScope(ioDispatcher).launch {
            genres = DataHandler.getPreloadedGenres()
            withContext(mainDispatcher) {
                preselectItems()
            }
        }
    }

    private fun filterSelected() = genres.filter { genre -> genre.isSelected }

    private fun setupRecyclerView() {
        val rv = findViewById<RecyclerView>(R.id.rvGenresList)

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        llm.reverseLayout = false

        rv.layoutManager = llm
        rv.adapter = GenresAdapter(genres)
    }

    private fun setOnClickListeners() {
        val fabSubmit = findViewById<FloatingActionButton>(R.id.fabSubmit)
        fabSubmit.setOnClickListener {
            updateDatabase()
        }
    }

    private fun updateDatabase() {
        CoroutineScope(ioDispatcher).launch {
            genreRepository.replaceAllLocal(filterSelected())
            DataHandler.updateMovies()
            withContext(mainDispatcher) {
                val selected = genres.filter { it.isSelected }.map { it.name }
                val returnData = GenresReturnData(selected.size, selected.toTypedArray())
                val intent = Intent()
                intent.putExtra(GENRES_RETURN_DATA, returnData)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun preselectItems() {
        CoroutineScope(ioDispatcher).launch {
            val saved = genreRepository.getAllLocalGenres()
            withContext(mainDispatcher) {
                genres.forEach { genre -> genre.isSelected = saved.contains(genre) }
                setupRecyclerView()
            }
        }
    }
}
