package com.juangomez.rickandmorty

import android.app.Application

class RickAndMortyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        setupDI()
    }

    private fun setupDI() {

    }
}