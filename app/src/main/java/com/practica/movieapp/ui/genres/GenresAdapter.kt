package com.practica.movieapp.ui.genres

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.practica.movieapp.R
import com.practica.movieapp.data.genres.Genre
import com.practica.movieapp.databinding.RvItemGenreCheckBinding

class GenresAdapter(private val items: List<Genre>) :
    RecyclerView.Adapter<GenresAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RvItemGenreCheckBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvItemGenreCheckBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(items[position]) {
                binding.cbIgCheck.text = this.name
                binding.cbIgCheck.isChecked = this.isSelected

                setItemSelection(holder, this, position)

                binding.cbIgCheck.setOnClickListener {
                    this.isSelected = !this.isSelected
                    setItemSelection(holder, this, position)
                }
            }
        }
    }

    private fun setItemSelection(holder: ViewHolder, genre: Genre, position: Int) {
        with(holder) {
            val bgColor = when(genre.isSelected) {
                true -> ContextCompat.getColor(binding.clIgParent.context, R.color.magenta_500_30a)
                else -> when (position % 2 != 0) {
                    true -> ContextCompat.getColor(binding.clIgParent.context, R.color.white_10a)
                    else -> ContextCompat.getColor(binding.clIgParent.context, R.color.black)
                }
            }

            val fgColor = when(genre.isSelected) {
                true -> ContextCompat.getColor(binding.clIgParent.context, R.color.magenta_300)
                else -> ContextCompat.getColor(binding.clIgParent.context, R.color.white)
            }

            binding.cbIgCheck.setBackgroundColor(bgColor)
            binding.cbIgCheck.setTextColor(fgColor)
        }
    }

    override fun getItemCount() = items.size
}