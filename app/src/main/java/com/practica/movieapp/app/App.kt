package com.practica.movieapp.app

import android.app.Application
import com.practica.movieapp.data.DataHandler
import com.practica.movieapp.data.ImageHandler
import com.practica.movieapp.database.Database

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Database.instance.initialize(this)
        ImageHandler.initialize()
        DataHandler.initialize()
    }
}