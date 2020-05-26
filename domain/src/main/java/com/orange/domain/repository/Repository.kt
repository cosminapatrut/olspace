package com.orange.domain.repository

import com.orange.domain.model.SatelliteAboveResponse
import com.orange.domain.model.SatellitePassesResponse
import io.reactivex.Single

interface Repository {
    fun getSatellitesAbove(
        lat: Float,
        long: Float,
        altitude: Int,
        searchRadius: Int,
        categoryId: Int): Single<SatelliteAboveResponse>

    fun getSatellitePasses(
        id: Int,
        lat: Float,
        long: Float,
        altitude: Int,
        days: Int,
        minVisibility: Int
    ): Single<SatellitePassesResponse>

}
