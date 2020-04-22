package com.juangomez.rickandmorty.views.seasons

import com.juangomez.domain.usecases.GetSeasonsUseCase
import com.juangomez.rickandmorty.views.base.BaseViewModel

class SeasonsViewModel(private val getSeasonsUseCase: GetSeasonsUseCase): BaseViewModel() {

    sealed class State : BaseViewModel.State() {
        object Default : State()
    }

    override fun initialState() {
        viewState.value = State.Default
    }
}