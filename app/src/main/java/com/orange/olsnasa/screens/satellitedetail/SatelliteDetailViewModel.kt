package com.orange.olsnasa.screens.satellitedetail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.orange.domain.model.*
import com.orange.domain.repository.NasaRepository
import com.orange.domain.repository.Repository
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class SatelliteDetailViewModel(
    private val satellitesRepository: Repository,
    private val nasaRepository: NasaRepository,
    private val executionScheduler: Scheduler = Schedulers.io(),
    private val postExecutionScheduler: Scheduler = AndroidSchedulers.mainThread()
): ViewModel() {

    val data = MutableLiveData<Items>()
    val passesData = MutableLiveData<SatellitePassesResponse>()
    private val disposables = CompositeDisposable()

    fun getSatellitePasses(satelliteAbove: SatelliteAbove, period: Int) {
        disposables.add(
            satellitesRepository.getSatellitePasses(
                satelliteAbove.satid,
                satelliteAbove.satlat,
                satelliteAbove.satlng,
                satelliteAbove.satalt.toInt(),
                period,
                5
            )
                .subscribeOn(executionScheduler)
                .observeOn(postExecutionScheduler)
                .subscribe({
                    passesData.postValue(it)
                }, {
                    Timber.e(it)
                })
        )
    }

    fun searchSatelliteImage(query: String) {
        val strippedQuery = query.split(" ".toRegex()).first()
        disposables.add(
            nasaRepository.searchImage(strippedQuery)
                .subscribeOn(executionScheduler)
                .observeOn(postExecutionScheduler)
                .subscribe({
                    if (!it.collection.items.isNullOrEmpty()) {
                        data.postValue(it.collection.items.first())
                    }
                }, {
                    Timber.e(it)
                })
        )
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}
