package com.practica.movieapp.data

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.practica.movieapp.R
import com.practica.movieapp.data.configuration.ConfigurationImagesResponse
import com.practica.movieapp.data.configuration.ConfigurationRetriever
import com.practica.movieapp.network.ApiClient
import kotlinx.coroutines.*

object ImageHandler {
    private lateinit var configurationImagesResponse: ConfigurationImagesResponse
    private var configurationRetriever: ConfigurationRetriever = ConfigurationRetriever(ApiClient.instance.retrofit!!)
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    private lateinit var imageBaseUrl: String
    private lateinit var imageProfileSizes: List<String>

    fun initialize() {
        CoroutineScope(ioDispatcher).launch {
            retrieveConfiguration()
        }
    }

    fun downloadH632ImageWithPath(context: Context, path: String?, iv: ImageView) = downloadImageWithPath(context, path, iv, imageProfileSizes[2])

    private fun retrieveConfiguration() {
        configurationImagesResponse = configurationRetriever.getConfig().images
        imageBaseUrl = configurationImagesResponse.secureBaseUrl
        imageProfileSizes = configurationImagesResponse.profileSizes
    }

    private fun downloadImageWithPath(context: Context, path: String?, iv: ImageView, size: String) {
        if (path == null) {
            iv.setImageResource(R.drawable.ic_no_image)
            iv.setColorFilter(ContextCompat.getColor(context, R.color.violet_300))
            iv.setBackgroundColor(ContextCompat.getColor(context, R.color.violet_200_30a))
        }
        else {
            GlideApp.with(context).load(imageBaseUrl + size + path).into(iv)
        }
    }

}