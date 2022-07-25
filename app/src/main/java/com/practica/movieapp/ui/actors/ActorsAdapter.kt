package com.practica.movieapp.ui.actors

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.practica.movieapp.R
import com.practica.movieapp.data.actors.Actor

class ActorsAdapter(private val actorsList: List<Actor>) : RecyclerView.Adapter<ActorsAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemParent: ConstraintLayout = view.findViewById<ConstraintLayout>(R.id.itemParent)
        val itemName: TextView = view.findViewById<TextView>(R.id.tvName)
        val itemImage: ImageView = view.findViewById<ImageView>(R.id.ivImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_item_list_with_photo, parent, false)

        return ActorsAdapter.ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val actor = actorsList[position]
        holder.itemName.text = actor.name

        ActorsImageRetriever.downloadImageWithPath(holder.itemImage.context, actor.profilePath!!, holder.itemImage)

        setItemSelection(holder, actor, position)

        holder.itemParent.setOnClickListener {
            actor.isSelected = !actor.isSelected
            setItemSelection(holder, actor, position)
        }
    }

    private fun setItemSelection(holder: ViewHolder, actor: Actor, position: Int) {
        val bgColor = when (actor.isSelected) {
            true -> ContextCompat.getColor(holder.itemParent.context, R.color.magenta_500_30a)
            else -> when (position % 2 != 0) {
                true -> ContextCompat.getColor(holder.itemParent.context, R.color.white_10a)
                else -> ContextCompat.getColor(holder.itemParent.context, R.color.black)
            }
        }

        val fgColor = when (actor.isSelected) {
            true -> ContextCompat.getColor(holder.itemParent.context, R.color.magenta_300)
            else -> ContextCompat.getColor(holder.itemParent.context, R.color.white)
        }

        holder.itemParent.setBackgroundColor(bgColor)
        holder.itemName.setTextColor(fgColor)
    }

    override fun getItemCount(): Int = actorsList.size
}