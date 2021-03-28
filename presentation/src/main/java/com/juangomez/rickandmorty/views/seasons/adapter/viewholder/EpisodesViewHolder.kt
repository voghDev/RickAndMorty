package com.juangomez.rickandmorty.views.seasons.adapter.viewholder

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.juangomez.domain.models.Episode
import com.juangomez.rickandmorty.databinding.EpisodeRowBinding
import kotlinx.android.extensions.LayoutContainer

class EpisodesViewHolder(
    override val containerView: View,
    private val binding: EpisodeRowBinding
) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    @SuppressLint("SetTextI18n")
    fun bind(episode: Episode) {
        binding.episodeNameText.text = episode.name
        binding.episodeNumberText.text = "#${episode.number}"
    }
}