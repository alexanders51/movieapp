package com.practica.movieapp.ui.searchui.details

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practica.movieapp.R
import com.practica.movieapp.data.ImageHandler

class DetailsFragment : Fragment(R.layout.fragment_details) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = view.findViewById<RecyclerView>(R.id.rvContent)

        val llm = LinearLayoutManager(this.context)
        llm.orientation = LinearLayoutManager.VERTICAL
        llm.reverseLayout = false

        rv.layoutManager = llm

        val model = ViewModelProvider(requireActivity())[DetailsViewModel::class.java]
        model.getCurrentMovie().observe(viewLifecycleOwner) { it?.let {
                val image = view.findViewById<ImageView>(R.id.ivDetailsHeader)
                ImageHandler.downloadW1280ImageWithPath(image.context, it.backdropPath, image)
                rv.adapter = DetailsAdapter(it)
            }
        }
    }
}