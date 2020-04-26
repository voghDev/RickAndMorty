package com.juangomez.rickandmorty.views.seasons.adapter.viewholder

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.juangomez.domain.models.Episode
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.episode_row.view.*

class EpisodesViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    @SuppressLint("SetTextI18n")
    fun bind(episode: Episode) {
        containerView.episode_name_text.text = episode.name
        containerView.episode_number_text.text = "#${episode.number}"
    }
}