package com.practica.movieapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.launch
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.practica.movieapp.R
import com.practica.movieapp.data.DataHandler
import com.practica.movieapp.ui.actors.ActorsActivityContract
import com.practica.movieapp.ui.actors.ActorsReturnData
import com.practica.movieapp.ui.genres.GenresActivityContract
import com.practica.movieapp.ui.genres.GenresReturnData

class OnboardingActivity : AppCompatActivity() {
    private var actorsRet: ActorsReturnData? = null
    private var genresRet: GenresReturnData? = null

    private val actorsActivityCustomLauncher = registerForActivityResult(ActorsActivityContract()) {
        if (it != null) actorsRet = it
    }

    private val genresActivityCustomLauncher = registerForActivityResult(GenresActivityContract()) {
        if (it != null) genresRet = it
    }

    companion object {
        fun open(context: Context) {
            context.startActivity(Intent(context, OnboardingActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)

        fetchLocalData()
        setContentView(R.layout.activity_onboarding)
        setClickListeners()
    }

    override fun onResume() {
        super.onResume()
        updateUiElements()
    }

    private fun setClickListeners() {
        val btnActors = findViewById<Button>(R.id.btnActors)
        val btnGenres = findViewById<Button>(R.id.btnGenres)
        val fabNext = findViewById<FloatingActionButton>(R.id.fabOnboardingNext)

        btnGenres.setOnClickListener {
            genresActivityCustomLauncher.launch()
        }

        btnActors.setOnClickListener {
            actorsActivityCustomLauncher.launch()
        }

        fabNext.setOnClickListener {
            MainActivity.open(this)
            finish()
        }
    }

    private fun updateUiElements() {
        val flagGenre = if (genresRet != null) genresRet?.nrSelectedGenres!! > 0 else false
        val flagActor = if (actorsRet != null) actorsRet?.nrSelectedActors!! > 0 else false
        val btnGenres = findViewById<Button>(R.id.btnGenres)
        val btnActors = findViewById<Button>(R.id.btnActors)
        val ivGenreCheck = findViewById<ImageView>(R.id.ivCheckGenres)
        val ivActorCheck = findViewById<ImageView>(R.id.ivCheckActors)

        updateControlDecoration(btnGenres, ivGenreCheck, flagGenre)
        updateControlDecoration(btnActors, ivActorCheck, flagActor)
        updateFloatingButton(flagGenre && flagActor)
    }

    private fun updateControlDecoration(btn: Button, iv: ImageView, flag: Boolean) {
        if (flag) {
            btn.setBackgroundColor(ContextCompat.getColor(this, R.color.magenta_300))
            btn.setTextColor(ContextCompat.getColor(this, R.color.black))
            iv.visibility = View.VISIBLE
        } else {
            btn.setBackgroundColor(ContextCompat.getColor(this, R.color.magenta_500_30a))
            btn.setTextColor(ContextCompat.getColor(this, R.color.white))
            iv.visibility = View.INVISIBLE
        }
    }

    private fun updateFloatingButton(flag: Boolean) {
        val fab = findViewById<FloatingActionButton>(R.id.fabOnboardingNext)
        fab.visibility = when (flag) {
            true -> View.VISIBLE
            else -> View.INVISIBLE
        }
    }

    private fun fetchLocalData() {
        val selActors = DataHandler.getLocalActors().filter { it.isSelected }.map { it.name }
        val selGenres = DataHandler.getLocalGenres().filter { it.isSelected }.map { it.name }

        actorsRet = ActorsReturnData(selActors.size, selActors.toTypedArray())
        genresRet = GenresReturnData(selGenres.size, selGenres.toTypedArray())
    }
}