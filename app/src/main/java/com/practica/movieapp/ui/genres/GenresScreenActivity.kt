package com.practica.movieapp.ui.genres

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.practica.movieapp.R
import com.practica.movieapp.data.RemoteDataRetriever
import com.practica.movieapp.data.genres.Genre
import com.practica.movieapp.data.genres.get.GenreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GenresScreenActivity : AppCompatActivity() {
    private var genres: List<Genre> = emptyList()
    private var genreRepository = GenreRepository.instance

    companion object {
        fun open(context: Context) {
            context.startActivity(Intent(context, GenresScreenActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genres)
        setOnClickListeners()
        getGenres()
    }

    private fun getGenres() {
        GlobalScope.launch(Dispatchers.IO) {
            genres = RemoteDataRetriever.getPreloadedGenres()
            withContext(Dispatchers.Main){
                preselectItems()
            }
        }
    }

    private fun filterSelected() = genres.filter { genre -> genre.isSelected }

    private fun setupRecyclerView() {
        val rvItems = findViewById<RecyclerView>(R.id.rvGItems)
        rvItems.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvItems.adapter = GenresAdapter(genres)
    }

    private fun setOnClickListeners() {
        val fabSubmit: FloatingActionButton = findViewById<FloatingActionButton>(R.id.fabSubmit)
        fabSubmit.setOnClickListener {
            updateDatabase()
        }
    }

    private fun updateDatabase() {
        GlobalScope.launch(Dispatchers.IO) {
            genreRepository.replaceAllLocal(filterSelected())
            withContext(Dispatchers.Main) {
                finish()
            }
        }
    }

    private fun preselectItems() {
        GlobalScope.launch(Dispatchers.IO) {
            var saved = genreRepository.getAllLocalGenres()
            withContext(Dispatchers.Main) {
                genres.forEach { genre -> genre.isSelected = saved.contains(genre) }
                setupRecyclerView()
            }
        }
    }
}
