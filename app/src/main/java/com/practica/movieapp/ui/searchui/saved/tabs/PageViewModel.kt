package com.practica.movieapp.ui.searchui.saved.tabs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class PageViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        when (it) {
            1 -> "meniul zilei"
            2 -> "POFTA BUNAAAAAAAAAAAAAAAAA"
            else -> "hello"
        }
    }

    fun setIndex(index: Int) {
        _index.value = index
    }
}