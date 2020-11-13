package com.juangomez.rickandmorty.views.base

import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    open class State

    val state: StateFlow<State>
        get() = viewState

    protected val viewState = MutableStateFlow(initialState)

    abstract val initialState: State

    abstract fun init()
}