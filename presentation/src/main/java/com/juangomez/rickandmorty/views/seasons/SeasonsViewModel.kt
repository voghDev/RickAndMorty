package com.juangomez.rickandmorty.views.seasons

import com.juangomez.common.Failure
import com.juangomez.domain.usecases.GetSeasonsUseCase
import com.juangomez.rickandmorty.views.base.BaseViewModel
import androidx.lifecycle.viewModelScope
import com.juangomez.domain.models.Season
import com.juangomez.domain.usecases.BaseUseCase

class SeasonsViewModel(private val getSeasonsUseCase: GetSeasonsUseCase) : BaseViewModel() {

    sealed class State : BaseViewModel.State() {
        object Loading : State()
        data class SeasonsLoaded(val seasons: List<Season>) : State()
        data class SeasonSelected(val season: Season) : State()
        data class Error(val failure: Failure) : State()
    }

    private lateinit var seasons: List<Season>

    override fun initialState() {
        viewState.value = State.Loading
    }

    fun getSeasons() {
        getSeasonsUseCase.invoke(viewModelScope, BaseUseCase.None()) {
            it.either(::handleGetSeasonError, ::handleGetSeasonsSuccess)
        }
    }

    fun onSeasonSelected(season: Season) {
        viewState.value = State.SeasonSelected(season)
    }

    private fun handleGetSeasonError(failure: Failure) {
        viewState.value = State.Error(failure)
    }

    private fun handleGetSeasonsSuccess(seasons: List<Season>) {
        this.seasons = seasons
        viewState.value = State.SeasonsLoaded(seasons)
    }
}