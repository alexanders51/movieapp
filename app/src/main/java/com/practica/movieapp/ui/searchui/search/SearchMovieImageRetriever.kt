package com.practica.movieapp.ui.searchui.search

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.practica.movieapp.data.configuration.ConfigurationImagesResponse
import com.practica.movieapp.data.configuration.ConfigurationRetriever
import com.practica.movieapp.network.ApiClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@GlideModule
object SearchMovieImageRetriever : AppGlideModule() {
    private var configurationRetriever: ConfigurationRetriever = ConfigurationRetriever(ApiClient.instance.retrofit!!)
    private lateinit var configurationImagesResponse: ConfigurationImagesResponse
    lateinit var imageBaseUrl: String
    lateinit var imageProfileSizes: List<String>

    fun initialize() {
        GlobalScope.launch {
            configurationImagesResponse = configurationRetriever.getConfig().images
            imageBaseUrl = configurationImagesResponse.secureBaseUrl
            imageProfileSizes = configurationImagesResponse.profileSizes
        }
    }

    fun downloadImageWithPath(context: Context?, path: String, iv: ImageView?) {
        Glide.with(context!!).load(imageBaseUrl + imageProfileSizes[2] + path).into(iv!!)
    }
}