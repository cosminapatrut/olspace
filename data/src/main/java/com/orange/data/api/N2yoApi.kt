package com.orange.data.api

import com.orange.domain.model.SatelliteAboveResponse
import com.orange.domain.model.SatellitePassesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface N2yoApi {
    @GET("/rest/v1/satellite/above/{observer_lat}/{observer_lng}/{observer_alt}/{search_radius}/{category_id}")
    fun getSatelliteAbove(
        @Path("observer_lat") lat: Float,
        @Path("observer_lng") long: Float,
        @Path("observer_alt") altitude: Int,
        @Path("search_radius") searchRadius: Int,
        @Path("category_id") categoryId: Int,
        @Query("apiKey") apiKey: String = API_KEY
    ): Single<SatelliteAboveResponse>

    @GET("/rest/v1/satellite/visualpasses/{id}/{observer_lat}/{observer_lng}/{observer_alt}/{days}/{min_visibility}")
    fun getSatellitePasses(
        @Path("id") id: Int,
        @Path("observer_lat") lat: Float,
        @Path("observer_lng") long: Float,
        @Path("observer_alt") altitude: Int,
        @Path("days") searchRadius: Int,
        @Path("min_visibility") categoryId: Int,
        @Query("apiKey") apiKey: String = API_KEY
    ): Single<SatellitePassesResponse>

    companion object {
        val BASE_URL = "https://www.n2yo.com/"
        val API_KEY = "XNVUFW-2MU23B-U9VFQQ-47LS"
    }
}
