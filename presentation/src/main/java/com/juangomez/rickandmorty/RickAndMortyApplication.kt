package com.juangomez.rickandmorty

import android.app.Application
import com.juangomez.rickandmorty.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RickAndMortyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        setupDI()
    }

    private fun setupDI() {
        startKoin {
            androidContext(this@RickAndMortyApplication)
            modules(
                listOf(
                    cacheModule,
                    remoteModule,
                    dataModule,
                    domainModule,
                    presentationModule
                )
            )
        }
    }
}