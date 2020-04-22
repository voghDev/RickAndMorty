package com.juangomez.rickandmorty.views.seasons

import com.juangomez.rickandmorty.views.base.BaseActivity
import com.juangomez.rickandmorty.views.base.BaseViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import com.juangomez.rickandmorty.R

class SeasonsActivity: BaseActivity() {

    override val viewModel: SeasonsViewModel by viewModel()
    override val layoutId: Int = R.layout.seasons_activity

    override fun setupObservers() {
        TODO("Not yet implemented")
    }

    override fun manageState(state: BaseViewModel.State) {
        TODO("Not yet implemented")
    }

}