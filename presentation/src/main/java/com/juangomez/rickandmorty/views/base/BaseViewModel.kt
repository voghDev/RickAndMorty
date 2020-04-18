package com.juangomez.rickandmorty.views.base

import androidx.lifecycle.*

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    open class State

    val state: LiveData<State>
        get() = viewState

    protected val viewState = MutableLiveData<State>()

    abstract fun initialState()
}