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

class GenresAdapter(private val genresList: List<Genre>) : RecyclerView.Adapter<GenresAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemParent: ConstraintLayout = view.findViewById<ConstraintLayout>(R.id.itemParent)
        val genreCheck: CheckBox = view.findViewById<CheckBox>(R.id.cbCheck)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_item_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val genre = genresList[position]
        holder.genreCheck.text = genre.name
        holder.genreCheck.isChecked = genre.isSelected

        setItemSelection(holder, genre, position)

        holder.genreCheck.setOnClickListener {
            genre.isSelected = !genre.isSelected
            setItemSelection(holder, genre, position)
        }
    }

    private fun setItemSelection(holder: ViewHolder, genre: Genre, position: Int) {
        val bgColor = when (genre.isSelected) {
            true -> ContextCompat.getColor(holder.itemParent.context, R.color.magenta_500_30a)
            else -> when (position % 2 != 0) {
                true -> ContextCompat.getColor(holder.itemParent.context, R.color.white_10a)
                else -> ContextCompat.getColor(holder.itemParent.context, R.color.black)
            }
        }

        val fgColor = when (genre.isSelected) {
            true -> ContextCompat.getColor(holder.itemParent.context, R.color.magenta_300)
            else -> ContextCompat.getColor(holder.itemParent.context, R.color.white)
        }

        holder.itemParent.setBackgroundColor(bgColor)
        holder.genreCheck.setTextColor(fgColor)
    }

    override fun getItemCount() = genresList.size
}