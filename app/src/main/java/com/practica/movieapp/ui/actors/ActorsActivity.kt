package com.practica.movieapp.ui.actors

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.practica.movieapp.R
import com.practica.movieapp.data.DataHandler
import com.practica.movieapp.data.actors.Actor
import com.practica.movieapp.data.actors.get.ActorRepository
import com.practica.movieapp.databinding.ActivityActorsBinding
import kotlinx.coroutines.*

class ActorsActivity : AppCompatActivity() {

    private var actors: List<Actor> = emptyList()
    private var actorRepository = ActorRepository.instance

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val mainDispatcher: MainCoroutineDispatcher = Dispatchers.Main

    private lateinit var _binding: ActivityActorsBinding
    private val binding get() = _binding

    companion object {
        const val ACTORS_RETURN_DATA: String = "actorsReturnData"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)

        _binding = ActivityActorsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerViewLayout()
        getActors()
        setOnClickListeners()
    }

    private fun getActors() {
        CoroutineScope(ioDispatcher).launch {
            actors = DataHandler.getPreloadedActors()
            withContext(mainDispatcher) {
                preselectItems()
            }
        }
    }

    private fun preselectItems() {
        CoroutineScope(ioDispatcher).launch {
            val saved = actorRepository.getAllLocalActors()
            withContext(mainDispatcher) {
                actors.forEach { actor -> actor.isSelected = saved.contains(actor) }
                setupRecyclerViewAdapter()
            }
        }
    }

    private fun filterSelected() = actors.filter { actor -> actor.isSelected }

    private fun setupRecyclerViewLayout() {
        val recyclerView = binding.rvAaActors

        val gridLayoutManager = GridLayoutManager(this, 3)

        with(gridLayoutManager) {
            orientation = LinearLayoutManager.VERTICAL
            reverseLayout = false
        }

        with(recyclerView) {
            layoutManager = gridLayoutManager
            setHasFixedSize(true)
        }
    }

    private fun setupRecyclerViewAdapter() {
        with(binding.rvAaActors) {
            adapter = ActorsAdapter(actors)
        }
    }

    private fun setOnClickListeners() {
        val fabSubmit = findViewById<FloatingActionButton>(R.id.fab_aa_submit)
        fabSubmit.setOnClickListener {
            updateDatabase()
        }
    }

    private fun updateDatabase() {
        CoroutineScope(ioDispatcher).launch {
            actorRepository.replaceAllLocal(filterSelected())
            DataHandler.updateMovies()
            withContext(mainDispatcher) {
                setResult(Activity.RESULT_OK, putReturnData())
                finish()
            }
        }
    }

    private fun putReturnData(): Intent {
        val selected = actors.filter { it.isSelected }.map { it.name }
        val returnData = ActorsReturnData(selected.size, selected.toTypedArray())
        val intent = Intent()
        intent.putExtra(ACTORS_RETURN_DATA, returnData)
        return intent
    }
}