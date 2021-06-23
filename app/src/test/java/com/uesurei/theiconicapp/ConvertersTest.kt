package com.uesurei.theiconicapp

import com.uesurei.theiconicapp.network.model.Brand
import com.uesurei.theiconicapp.network.model.Converters
import com.uesurei.theiconicapp.network.model.Embedded1
import com.uesurei.theiconicapp.network.model.Image
import junit.framework.Assert.assertEquals
import org.junit.Test


class ConvertersTest {
    private val embedded = Embedded1(Brand("nike"),listOf(Image("http://some.website.com")))
    private val embedded1 = Embedded1(Brand("nike"),listOf(Image("https://some.website.com")))
    private val embeddedString = embedded.brand.brand_name + " " + embedded.images[0].url

    @Test
    fun test_fromEmbedded(){
        assertEquals( embeddedString,Converters().fromEmbedded(embedded))
    }

    @Test
    fun test_toEmbedded(){
        assertEquals(embedded1,Converters().toEmbedded(embeddedString))
    }
}