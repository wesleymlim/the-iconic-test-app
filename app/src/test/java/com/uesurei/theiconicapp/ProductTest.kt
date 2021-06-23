package com.uesurei.theiconicapp

import com.uesurei.theiconicapp.network.model.Brand
import com.uesurei.theiconicapp.network.model.Embedded1
import com.uesurei.theiconicapp.network.model.Image
import com.uesurei.theiconicapp.network.model.Product
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProductTest {
    private lateinit var product: Product
    private lateinit var embedded : Embedded1

    @Before
    fun setUp() {
        embedded = Embedded1(Brand("nike"), listOf(Image("http://some.website.com")))
        product = Product("SKU", 10.0, 9.0, "T-Shirt", "Descript", embedded)
    }

    @Test
    fun test_defaultValue(){
        assertEquals(product.embedded?.brand, embedded.brand)
        assertEquals(product.embedded?.images!![0].url, embedded.images[0].url)
    }

    @Test
    fun test_toObjectType(){
        assertEquals("SKU", product.sku)
        assertEquals(10.0, product.price)
        assertEquals(9.0, product.final_price)
        assertEquals("T-Shirt", product.name)
        assertEquals("nike", product.embedded?.brand?.brand_name)
        assertEquals("http://some.website.com", product.embedded?.images!![0].url)
    }
}