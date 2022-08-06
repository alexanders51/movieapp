package com.practica.movieapp.ui.searchui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practica.movieapp.data.genres.Genre
import com.practica.movieapp.databinding.RvDetailsGenreBinding

class DetailsGenresBarAdapter(
    private val items: List<Genre>
) : RecyclerView.Adapter<DetailsGenresBarAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RvDetailsGenreBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvDetailsGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(items[position]) {
                binding.tvItemName.text = this.name
            }
        }
    }

    override fun getItemCount() = items.size
}