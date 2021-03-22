package com.juangomez.rickandmorty.views.seasons

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.juangomez.domain.models.Season
import com.juangomez.rickandmorty.views.base.BaseActivity
import com.juangomez.rickandmorty.views.base.BaseViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import com.juangomez.rickandmorty.R
import com.juangomez.rickandmorty.common.visible
import com.juangomez.rickandmorty.databinding.SeasonsActivityBinding
import com.juangomez.rickandmorty.views.seasons.adapter.EpisodesAdapter

class SeasonsActivity : BaseActivity() {

    override val viewModel: SeasonsViewModel by viewModel()
    override val binding: SeasonsActivityBinding by lazy {
        SeasonsActivityBinding.inflate(layoutInflater)
    }

    private lateinit var episodesAdapter: EpisodesAdapter

    override fun setupObservers() {
        viewModel.state.observe(this, { manageState(it) })
    }

    override fun manageState(state: BaseViewModel.State) {
        when (state) {
            is SeasonsViewModel.State.Loading -> {
                showLoading()
                viewModel.getSeasons()
            }
            is SeasonsViewModel.State.Error -> {
                hideLoading()
                showDefaultError(state.failure) { viewModel.retryUseCase(state.useCase) }
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

    private fun showLoading() = binding.progressBar.visible()

    private fun hideLoading() = binding.progressBar.hide()

    private fun showSeasonsContentLayout() = binding.seasonsContentLayout.visible()

    private fun setupSeasonsSelector(seasons: List<Season>) {
        binding.seasonNumberTextSelector.apply {
            adapter = ArrayAdapter(
                this@SeasonsActivity,
                R.layout.season_row,
                seasons.map { getString(R.string.season_number).format(it.number) })

            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.onSeasonSelected(seasons[position])
                }
            }
        }
    }

    private fun setupEpisodesList(season: Season) {
        episodesAdapter = EpisodesAdapter(season.episodes)
        binding.episodesList.apply {
            layoutManager = LinearLayoutManager(this@SeasonsActivity, RecyclerView.VERTICAL, false)
            adapter = episodesAdapter
            isNestedScrollingEnabled = false
        }
        binding.seasonInfoView.setup(season.number.toString())
    }
}