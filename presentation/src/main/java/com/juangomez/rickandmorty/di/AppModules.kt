package com.juangomez.rickandmorty.di

import com.google.gson.Gson
import com.juangomez.cache.CharacterCacheDataSourceImpl
import com.juangomez.cache.EpisodeCacheDataSourceImpl
import com.juangomez.data.providers.cache.CharacterCacheDataSource
import com.juangomez.data.providers.cache.EpisodeCacheDataSource
import com.juangomez.data.providers.remote.CharacterRemoteDataSource
import com.juangomez.data.providers.remote.EpisodeRemoteDataSource
import com.juangomez.data.repositories.CharacterRepositoryImpl
import com.juangomez.data.repositories.EpisodeRepositoryImpl
import com.juangomez.domain.repositories.CharacterRepository
import com.juangomez.domain.repositories.EpisodeRepository
import com.juangomez.domain.usecases.GetCharactersUseCase
import com.juangomez.domain.usecases.GetEpisodeUseCase
import com.juangomez.domain.usecases.GetSeasonsUseCase
import com.juangomez.remote.services.APIService
import com.juangomez.remote.services.characters.CharacterAPIService
import com.juangomez.remote.services.characters.CharacterRemoteDataSourceImpl
import com.juangomez.remote.services.episodes.EpisodeAPIService
import com.juangomez.remote.services.episodes.EpisodeRemoteDataSourceImpl
import com.juangomez.rickandmorty.BuildConfig.BASE_URL
import com.juangomez.rickandmorty.BuildConfig.DEBUG
import com.juangomez.rickandmorty.views.seasons.SeasonsViewModel
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.converter.gson.GsonConverterFactory

private const val GSON_CONVERTER_FACTORY = "gson_converter_factory"
private const val HTTP_LOGGING = "http_logging"
private const val INTERCEPTORS = "interceptors"
private const val EPISODE_API_SERVICE = "episode_api_service"
private const val CHARACTER_API_SERVICE = "character_api_service"

val remoteModule = module {
    single(named(GSON_CONVERTER_FACTORY)) { GsonConverterFactory.create(Gson()) }

    single(named(HTTP_LOGGING)) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (DEBUG) httpLoggingInterceptor.level =
            HttpLoggingInterceptor.Level.BODY else httpLoggingInterceptor.level =
            HttpLoggingInterceptor.Level.NONE
        httpLoggingInterceptor
    }

    single(named(INTERCEPTORS)) { arrayOf(get(named(HTTP_LOGGING)) as Interceptor) }

    single(named(EPISODE_API_SERVICE)) {
        APIService(
            EpisodeAPIService::class.java,
            BASE_URL,
            get(named(GSON_CONVERTER_FACTORY)),
            get(named(INTERCEPTORS))
        )
    }

    single(named(CHARACTER_API_SERVICE)) {
        APIService(
            CharacterAPIService::class.java,
            BASE_URL,
            get(named(GSON_CONVERTER_FACTORY)),
            get(named(INTERCEPTORS))
        )
    }

    single { EpisodeRemoteDataSourceImpl(get(named(EPISODE_API_SERVICE))) as EpisodeRemoteDataSource }

    single { CharacterRemoteDataSourceImpl(get(named(CHARACTER_API_SERVICE))) as CharacterRemoteDataSource }
}

val cacheModule = module {
    single { EpisodeCacheDataSourceImpl() as EpisodeCacheDataSource }

    single { CharacterCacheDataSourceImpl() as CharacterCacheDataSource }
}

val dataModule = module {
    single { EpisodeRepositoryImpl(get(), get()) as EpisodeRepository }

    single { CharacterRepositoryImpl(get(), get()) as CharacterRepository }
}

val domainModule = module {
    factory { GetSeasonsUseCase(get()) }

    factory { GetEpisodeUseCase(get()) }

    factory { GetCharactersUseCase(get()) }
}

val presentationModule = module {
    viewModel {
        SeasonsViewModel(get())
    }
}