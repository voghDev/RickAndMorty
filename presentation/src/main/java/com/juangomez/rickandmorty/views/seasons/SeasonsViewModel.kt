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
        data class Error(val failure: Failure, val useCase: UseCase) : State()
    }

    enum class UseCase {
        GET_SEASONS
    }

    private lateinit var seasons: List<Season>

    override fun initialState() {
        viewState.value = State.Loading

        getSeasons()
    }

    private fun getSeasons() {
        getSeasonsUseCase.invoke(viewModelScope, BaseUseCase.None()) {
            it.fold(::handleGetSeasonError, ::handleGetSeasonsSuccess)
        }
    }

    fun retryUseCase(useCase: UseCase) {
        viewState.value = State.Loading
        when (useCase) {
            UseCase.GET_SEASONS -> getSeasons()
        }
    }

    fun onSeasonSelected(season: Season) {
        viewState.value = State.SeasonSelected(season)
    }

    private fun handleGetSeasonError(failure: Failure) {
        viewState.value = State.Error(failure, UseCase.GET_SEASONS)
    }

    private fun handleGetSeasonsSuccess(seasons: List<Season>) {
        this.seasons = seasons
        viewState.value = State.SeasonsLoaded(seasons)
    }
}