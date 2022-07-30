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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates

class OnboardingActivity : AppCompatActivity() {
    private var btnActors by Delegates.notNull<Int>();
    private var btnGenres by Delegates.notNull<Int>();

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
        GlobalScope.launch(Dispatchers.IO) {
            val flag = RemoteDataRetriever.userPreferencesExist()
            if (flag) RemoteDataRetriever.updateMovies()
            withContext(Dispatchers.Main) {
                if (flag) {
                    MainActivity.open(this@OnboardingActivity)
                    finish()
                }
            }
        }
    }

    private fun setClickListeners() {
        val btnActors = findViewById<Button>(R.id.btnActors);
        val btnGenres = findViewById<Button>(R.id.btnGenres);

        btnGenres.setOnClickListener {
            GenresActivity.open(this)
        }

        btnActors.setOnClickListener {
            ActorsActivity.open(this)
        }
    }
}