package com.practica.movieapp.ui.genres

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practica.movieapp.R
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
        getGenres()
    }

    private fun getGenres() {
        GlobalScope.launch(Dispatchers.IO) {
            genres = genreRepository.getAllRemoteGenres()
            withContext(Dispatchers.Main){
                setupRecyclerView()
            }
        }
    }

    private fun setupRecyclerView() {
        val rvItems = findViewById<RecyclerView>(R.id.rvGItems)
        rvItems.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvItems.adapter = GenresAdapter(genres)
    }
}
