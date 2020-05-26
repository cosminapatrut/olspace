package com.orange.olsnasa.screens.satellites

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.orange.domain.model.Items
import com.orange.domain.model.SatelliteAbove
import com.orange.domain.repository.NasaRepository
import com.orange.domain.repository.Repository
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SatellitesViewModel(
    private val satellitesRepository: Repository,
    private val nasaRepository: NasaRepository,
    private val executionScheduler: Scheduler = Schedulers.io(),
    private val postExecutionScheduler: Scheduler = AndroidSchedulers.mainThread()
): ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val data = MutableLiveData<List<SatelliteAbove>>()
    private val disposables = CompositeDisposable()

    fun getSatellites(lat: Float, lng: Float, refreshInterval: Long = 10000L) {
        disposables.add(
            satellitesRepository.getSatellitesAbove(lat, lng, 0, 10, 0)
                .subscribeOn(executionScheduler)
                .observeOn(postExecutionScheduler)
                .repeatWhen { completed -> completed.delay(refreshInterval, TimeUnit.MILLISECONDS) }
                .doOnSubscribe { isLoading.postValue(true) }
                .doOnNext { isLoading.postValue(false) }
                .doFinally { isLoading.postValue(false) }
                .subscribe({
                    val satellites = it.above
                    data.postValue(satellites)
                    satellites.forEach { satelliteAbove ->
                        val strippedQuery = satelliteAbove.satname.split(" ".toRegex()).first()
                        nasaRepository.searchImage(strippedQuery)
                            .subscribeOn(executionScheduler)
                            .observeOn(postExecutionScheduler)
                            .subscribe({
                                if (!it.collection.items.isNullOrEmpty()) {
                                    val hrefs =
                                        it.collection.items.filter { items: Items -> !items.links.isNullOrEmpty() }
                                            .map { items ->
                                                items.links.filter { links ->
                                                    links.href.isNotBlank()
                                                }
                                            }
                                    satelliteAbove.satIcon = hrefs.first().first().href
                                    data.postValue(satellites)
                                }
                            }, {
                                Timber.e(it)
                            })
                    }
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
                        val hrefs =
                            it.collection.items.filter { items: Items -> !items.links.isNullOrEmpty() }
                                .map { items ->
                                    items.links.filter { links ->
                                        links.href.isNotBlank()
                                    }
                                }
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
