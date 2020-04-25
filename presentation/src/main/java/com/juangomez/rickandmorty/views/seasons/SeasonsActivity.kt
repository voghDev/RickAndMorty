package com.juangomez.rickandmorty.views.seasons

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.juangomez.domain.models.Season
import com.juangomez.rickandmorty.views.base.BaseActivity
import com.juangomez.rickandmorty.views.base.BaseViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import com.juangomez.rickandmorty.R
import com.juangomez.rickandmorty.common.visible
import com.juangomez.rickandmorty.views.seasons.adapter.EpisodesAdapter
import kotlinx.android.synthetic.main.seasons_activity.*

class SeasonsActivity : BaseActivity() {

    override val viewModel: SeasonsViewModel by viewModel()
    override val layoutId: Int = R.layout.seasons_activity

    private lateinit var episodesAdapter: EpisodesAdapter

    override fun setupObservers() {
        viewModel.state.observe(this, Observer { manageState(it) })
    }

    override fun manageState(state: BaseViewModel.State) {
        when (state) {
            is SeasonsViewModel.State.Loading -> {
                showLoading()
                viewModel.getSeasons()
            }
            is SeasonsViewModel.State.Error -> {
                hideLoading()
            }
            is SeasonsViewModel.State.SeasonsLoaded -> {
                hideLoading()
                showSeasonsContentLayout()
                setupSeasonsSelector(state.seasons)
            }
            is SeasonsViewModel.State.SeasonSelected -> {
                setupEpisodesList(state.season)
            }
        }
    }

    private fun showLoading() = progress_bar.visible()

    private fun hideLoading() = progress_bar.hide()

    private fun showSeasonsContentLayout() = seasons_content_layout.visible()

    private fun setupSeasonsSelector(seasons: List<Season>) {
        season_number_text_selector.adapter =
            ArrayAdapter(
                this,
                R.layout.season_row,
                seasons.map { getString(R.string.season_number).format(it.number) })

        season_number_text_selector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.onSeasonSelected(seasons[position])
            }
        }
    }

    private fun setupEpisodesList(season: Season) {
        episodesAdapter = EpisodesAdapter(season.episodes)
        episodes_list.apply {
            layoutManager = LinearLayoutManager(this@SeasonsActivity, RecyclerView.VERTICAL, false)
            adapter = episodesAdapter
            isNestedScrollingEnabled = false
        }
    }

}