package com.practica.movieapp.ui.actors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.practica.movieapp.R
import com.practica.movieapp.data.ImageHandler
import com.practica.movieapp.data.actors.Actor
import com.practica.movieapp.databinding.RvItemActorBinding

class ActorsAdapter(private val items: List<Actor>) :
    RecyclerView.Adapter<ActorsAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RvItemActorBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvItemActorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(items[position]) {
                ImageHandler.downloadH632ImageWithPath(
                    binding.ivIaImage.context,
                    this.profilePath,
                    binding.ivIaImage
                )

                binding.tvIaName.text = this.name

                setItemSelection(holder, this)

                binding.clIaParent.setOnClickListener {
                    this.isSelected = !this.isSelected
                    setItemSelection(holder, this)
                }
            }
        }
    }

    private fun setItemSelection(holder: ViewHolder, actor: Actor) {
        with(holder) {
            val bgColor = when(actor.isSelected) {
                true -> ContextCompat.getColor(binding.cvIaCard.context, R.color.magenta_500_card)
                else -> ContextCompat.getColor(binding.cvIaCard.context, R.color.dark)
            }

            val fgColor = when(actor.isSelected) {
                true -> ContextCompat.getColor(binding.cvIaCard.context, R.color.magenta_200)
                else -> ContextCompat.getColor(binding.cvIaCard.context, R.color.white)
            }

            binding.cvIaCard.setCardBackgroundColor(bgColor)
            binding.tvIaName.setTextColor(fgColor)
        }
    }

    override fun getItemCount(): Int = items.size
}