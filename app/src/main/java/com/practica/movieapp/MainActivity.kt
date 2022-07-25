package com.practica.movieapp

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.practica.movieapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}