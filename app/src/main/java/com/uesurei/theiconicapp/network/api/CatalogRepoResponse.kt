package com.uesurei.theiconicapp.network.api

import com.google.gson.annotations.SerializedName
import com.uesurei.theiconicapp.network.model.Product

data class CatalogRepoResponse(
    @SerializedName("_embedded")
    val embedded: Embedded
)

data class Embedded(
    @SerializedName("product")
    val products: List<Product> = emptyList()
)