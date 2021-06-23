package com.uesurei.theiconicapp.network.model

import androidx.room.TypeConverter

class Converters {
    //Save Embedded in DB by combining brandName and imageUrl link in a string
    @TypeConverter
    fun fromEmbedded(embedded1: Embedded1): String {
        return embedded1.brand.brand_name + " " + embedded1.images[0].url
    }

    //remaking the Embedded object to fetch brandName and imageUrl
    @TypeConverter
    fun toEmbedded(embedded1: String): Embedded1 {
        val splitString = embedded1.replace("^\\s+", "").split("\\s".toRegex())
        val brand = Brand(splitString[0])
        val fixedString = splitString[splitString.size - 1].replace("http","https")
        val image = Image(fixedString)
        val imageList: List<Image> = listOf(image)
        return Embedded1(brand, imageList)
    }
}