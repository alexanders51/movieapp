package com.practica.movieapp.ui.actors

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practica.movieapp.R
import com.practica.movieapp.data.actors.Actor
import com.practica.movieapp.data.actors.get.ActorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActorsScreenActivity : AppCompatActivity() {
    private var actors : List<Actor> = emptyList()
    private var actorRepository = ActorRepository.instance

    companion object {
        fun open(context: Context) {
            context.startActivity(Intent(context, ActorsScreenActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actors)
        getActors()
        setupRecyclerView()
    }

    private fun getActors() {
        GlobalScope.launch {
            actors = actorRepository.getRemoteActorsFromPage(1 /* TODO: hardcoded */)
            withContext(Dispatchers.Main) {
                setupRecyclerView()
            }
        }
    }

    private fun setupRecyclerView() {
        val rvItems = findViewById<RecyclerView>(R.id.rvAItems)
        rvItems.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvItems.adapter = ActorsAdapter(actors)
    }
}