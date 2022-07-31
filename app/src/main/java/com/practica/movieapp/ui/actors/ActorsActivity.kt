package com.practica.movieapp.ui.actors

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
import com.practica.movieapp.data.actors.Actor
import com.practica.movieapp.data.actors.get.ActorRepository
import kotlinx.coroutines.*

class ActorsActivity : AppCompatActivity() {
    private var actors : List<Actor> = emptyList()
    private var actorRepository = ActorRepository.instance
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val mainDispatcher: MainCoroutineDispatcher = Dispatchers.Main

    companion object {
        const val GENRES_RETURN_DATA: String = "actorsReturnData"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actors)
        setOnClickListeners()
        getActors()
    }

    private fun getActors() {
        CoroutineScope(ioDispatcher).launch {
            actors = DataHandler.getPreloadedActors()
            withContext(mainDispatcher) {
                preselectItems()
            }
        }
    }

    private fun filterSelected() = actors.filter { actor -> actor.isSelected }

    private fun setupRecyclerView() {
        val rv = findViewById<RecyclerView>(R.id.rvActorsList)

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        llm.reverseLayout = false

        rv.layoutManager = llm
        rv.adapter = ActorsAdapter(actors)
    }

    private fun setOnClickListeners() {
        val fabSubmit = findViewById<FloatingActionButton>(R.id.fabSubmit)
        fabSubmit.setOnClickListener {
            updateDatabase()
        }
    }

    private fun updateDatabase() {
        CoroutineScope(ioDispatcher).launch {
            actorRepository.replaceAllLocal(filterSelected())
            DataHandler.updateMovies()
            withContext(mainDispatcher) {
                val selected = actors.filter { it.isSelected }.map { it.name }
                val returnData = ActorsReturnData(selected.size, selected.toTypedArray())
                val intent = Intent()
                intent.putExtra(GENRES_RETURN_DATA, returnData)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun preselectItems() {
        CoroutineScope(ioDispatcher).launch {
            val saved = actorRepository.getAllLocalActors()
            withContext(mainDispatcher) {
                actors.forEach { actor -> actor.isSelected = saved.contains(actor) }
                setupRecyclerView()
            }
        }
    }
}