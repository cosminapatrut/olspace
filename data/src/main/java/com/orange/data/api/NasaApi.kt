package com.orange.data.api

import com.orange.domain.model.NasaImagesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {
    @GET("/search")
    fun searchImage(@Query("q") query: String): Single<NasaImagesResponse>

    companion object {
        val BASE_URL = "https://images-api.nasa.gov/"
    }
}
