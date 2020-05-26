package com.orange.domain.model

data class NasaImagesResponse(
    val collection : Collection
)

data class Collection(
    val href : String,
    val items : List<Items> = emptyList(),
    val version : String
)

data class Items(
    val href : String,
    val links : List<Links> = emptyList(),
    val data: List<DataDescription> = emptyList()
)

data class Links(
    val render : String,
    val href : String,
    val rel : String
)

data class DataDescription(
    val description : String,
    val title : String
)

