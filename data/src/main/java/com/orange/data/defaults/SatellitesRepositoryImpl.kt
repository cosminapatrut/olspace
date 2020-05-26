package com.orange.data.defaults

import com.orange.data.api.N2yoApi
import com.orange.domain.model.SatelliteAboveResponse
import com.orange.domain.model.SatellitePassesResponse
import com.orange.domain.repository.Repository
import io.reactivex.Single

class SatellitesRepositoryImpl(val n2yoApi: N2yoApi) : Repository {
    override fun getSatellitesAbove(
        lat: Float,
        long: Float,
        altitude: Int,
        searchRadius: Int,
        categoryId: Int): Single<SatelliteAboveResponse> {
        return n2yoApi.getSatelliteAbove(lat, long, altitude, searchRadius, categoryId)
    }

    override fun getSatellitePasses(
        id: Int,
        lat: Float,
        long: Float,
        altitude: Int,
        days: Int,
        minVisibility: Int
    ): Single<SatellitePassesResponse> {
        return n2yoApi.getSatellitePasses(id, lat, long, altitude, days, minVisibility)
    }
}
