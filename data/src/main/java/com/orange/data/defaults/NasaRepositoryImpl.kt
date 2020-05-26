package com.orange.data.defaults

import com.orange.data.api.NasaApi
import com.orange.domain.model.NasaImagesResponse
import com.orange.domain.repository.NasaRepository
import io.reactivex.Single

class NasaRepositoryImpl(private val api: NasaApi) : NasaRepository{
    override fun searchImage(query: String): Single<NasaImagesResponse> = api.searchImage(query)
}
