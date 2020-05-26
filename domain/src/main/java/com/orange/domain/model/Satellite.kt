package com.orange.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class SatelliteAboveResponse(
    val info: SatelliteAboveInfo,
    val above: List<SatelliteAbove>
)

@Parcelize
data class SatelliteAbove (
    val satid: Int,
    val satname: String,
    val intDesignator: String,
    val launchDate: String,
    val satlat: Float,
    val satlng: Float,
    val satalt: Float,
    var satIcon: String?
) : Parcelable

data class SatelliteAboveInfo(
    val category: String,
    val transactionsCount: Int,
    val satCount: Int
)

@Parcelize
data class  SatellitePassesResponse(
    val info: SatellitePassesInfo,
    val passes: List<SatellitePass>
) : Parcelable

@Parcelize
data class SatellitePass(
    val startAz: Float,
    val startAzCompass: String,
    val startEl: Float,
    val startUTC: Long,
    val maxAz: Float,
    val maxAzCompass: String,
    val maxEl: Float,
    val maxUTC: Long,
    val endAz: Float,
    val endAzCompass: String,
    val endEl: Float,
    val endUTC: Long,
    val mag: Float,
    val duration: Long
) : Parcelable

@Parcelize
data class SatellitePassesInfo(
    val satId: Int,
    val satName: String,
    val transactionsCount: Int,
    val passesCount: Int
) : Parcelable
