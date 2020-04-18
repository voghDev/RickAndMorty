package com.juangomez.rickandmorty.views.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    abstract val layoutId: Int
    abstract val viewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        setupObservers()
        prepare(intent)
        viewModel.initialState()
    }

    open fun prepare(intent: Intent?) {}

    abstract fun setupObservers()

    abstract fun manageState(state: BaseViewModel.State)
}