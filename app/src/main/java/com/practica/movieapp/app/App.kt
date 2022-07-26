package com.practica.movieapp.app

import android.app.Application
import com.practica.movieapp.data.RemoteDataRetriever
import com.practica.movieapp.database.Database
import com.practica.movieapp.ui.actors.ActorsImageRetriever

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Database.instance.initialize(this)
        ActorsImageRetriever.initialize()
        RemoteDataRetriever.initialize()
    }
}