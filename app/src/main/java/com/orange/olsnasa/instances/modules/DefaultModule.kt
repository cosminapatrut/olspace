package com.orange.olsnasa.instances.modules

import com.orange.data.api.N2yoApi
import com.orange.data.api.NasaApi
import com.orange.data.defaults.NasaRepositoryImpl
import com.orange.data.defaults.SatellitesRepositoryImpl
import com.orange.domain.repository.NasaRepository
import com.orange.domain.repository.Repository
import com.orange.olsnasa.screens.satellitedetail.SatelliteDetailViewModel
import com.orange.olsnasa.screens.satellites.SatellitesViewModel
import com.orange.olsnasa.screens.scores.ScoresViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val defaultModule: Module = module {
    single { createOkHttpClient() }
    single { createWebService<N2yoApi>(get(), N2yoApi.BASE_URL) }
    single { createWebService<NasaApi>(get(), NasaApi.BASE_URL) }

    single { SatellitesRepositoryImpl(get()) as Repository }
    single { NasaRepositoryImpl(get()) as NasaRepository }

    viewModel { SatellitesViewModel(get(), get()) }
    viewModel { SatelliteDetailViewModel(get(), get()) }
    viewModel { ScoresViewModel() }

}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    return retrofit.create(T::class.java)
}
