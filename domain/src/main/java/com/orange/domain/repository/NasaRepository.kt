package com.orange.domain.repository

import com.orange.domain.model.NasaImagesResponse
import io.reactivex.Single

interface NasaRepository {
    fun searchImage(query: String): Single<NasaImagesResponse>
}
