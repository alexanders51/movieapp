package com.practica.movieapp.data.configuration

import com.google.gson.annotations.SerializedName

data class ConfigurationResponse(
    @SerializedName("images")       var images: ConfigurationImagesResponse,
    @SerializedName("change_keys")  var changeKeys: List<String>
)
