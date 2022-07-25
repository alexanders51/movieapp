package com.practica.movieapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.practica.movieapp.R
import com.practica.movieapp.ui.actors.ActorsScreenActivity
import com.practica.movieapp.ui.genres.GenresScreenActivity
import kotlin.properties.Delegates

class OnboardingScreenActivity : AppCompatActivity() {
    private var btnActors by Delegates.notNull<Int>();
    private var btnGenres by Delegates.notNull<Int>();

    companion object {
        fun open(context: Context) {
            context.startActivity(Intent(context, OnboardingScreenActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        setClickListeners()
    }

    private fun setClickListeners() {
        val btnActors = findViewById<Button>(R.id.btnActors);
        val btnGenres = findViewById<Button>(R.id.btnGenres);

        btnGenres.setOnClickListener {
            GenresScreenActivity.open(this)
        }

        btnActors.setOnClickListener {
            ActorsScreenActivity.open(this)
        }
    }
}