package com.juangomez.rickandmorty.views.seasons.adapter.viewholder

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.juangomez.domain.models.Episode
import com.juangomez.rickandmorty.databinding.EpisodeRowBinding
import kotlinx.android.extensions.LayoutContainer

class EpisodesViewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    private val binding = EpisodeRowBinding.bind(containerView)

    @SuppressLint("SetTextI18n")
    fun bind(episode: Episode) = with(binding) {
        episodeNameText.text = episode.name
        episodeNumberText.text = "#${episode.number}"
    }
}