package com.uesurei.theiconicapp.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Entity(tableName = "products")
@TypeConverters(Converters::class)
data class Product(
    @PrimaryKey
    @field:SerializedName("sku")
    val sku: String,
    @field:SerializedName("price")
    val price: Double,
    @field:SerializedName("final_price")
    val final_price: Double?,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("short_description")
    val description: String?,
    @field:SerializedName("_embedded")
    val embedded: Embedded1?
)

data class Embedded1(
    @field:SerializedName("brand")
    val brand: Brand,
    val images: List<Image>
)

data class Brand(
    @field:SerializedName("name")
    val brand_name: String
)

data class Image(
    @field:SerializedName("url")
    val url: String
)
