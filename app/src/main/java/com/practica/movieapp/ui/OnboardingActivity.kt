package com.practica.movieapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.practica.movieapp.R
import com.practica.movieapp.data.RemoteDataRetriever
import com.practica.movieapp.ui.actors.ActorsActivity
import com.practica.movieapp.ui.genres.GenresActivity
import kotlinx.coroutines.*

class OnboardingActivity : AppCompatActivity() {
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val mainDispatcher: MainCoroutineDispatcher = Dispatchers.Main

    companion object {
        fun open(context: Context) {
            context.startActivity(Intent(context, OnboardingActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        setClickListeners()
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(ioDispatcher).launch {
            val flag = RemoteDataRetriever.userPreferencesExist()
            if (flag) RemoteDataRetriever.updateMovies()
            withContext(mainDispatcher) {
                if (flag) {
                    MainActivity.open(this@OnboardingActivity)
                    finish()
                }
            }
        }
    }

    private fun setClickListeners() {
        val btnActors = findViewById<Button>(R.id.btnActors)
        val btnGenres = findViewById<Button>(R.id.btnGenres)

        btnGenres.setOnClickListener {
            GenresActivity.open(this)
        }

        btnActors.setOnClickListener {
            ActorsActivity.open(this)
        }
    }
}