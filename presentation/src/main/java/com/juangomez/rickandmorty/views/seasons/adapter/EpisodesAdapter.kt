package com.juangomez.rickandmorty.views.seasons.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.juangomez.domain.models.Episode
import com.juangomez.rickandmorty.databinding.EpisodeRowBinding
import com.juangomez.rickandmorty.views.seasons.adapter.viewholder.EpisodesViewHolder

class EpisodesAdapter(private val episodes: List<Episode>) :
    RecyclerView.Adapter<EpisodesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesViewHolder {
        val binding = EpisodeRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) =
        holder.bind(episodes[position])

    override fun getItemCount() = episodes.size
}