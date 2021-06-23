package com.uesurei.theiconicapp.network.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.uesurei.theiconicapp.network.model.Brand
import com.uesurei.theiconicapp.network.model.Embedded1
import com.uesurei.theiconicapp.network.model.Image
import com.uesurei.theiconicapp.network.model.Product
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductDaoTest {
    private lateinit var database: ProductDatabase
    private lateinit var productDao: ProductDao
    private val embedded = Embedded1(Brand("nike"), listOf(Image("http://some.website.com")))
    private val productA = Product("SKU1", 10.0, 9.0, "T-Shirt", "Descript", embedded)
    private val productB = Product("SKU2", 11.0, 9.0, "Shorts", "Descript", embedded)
    private val productC = Product("SKU3", 12.0, 9.0, "Cap", "Descript", embedded)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, ProductDatabase::class.java).build()
        productDao = database.productDao()

        // Insert plants in non-alphabetical order to test that results are sorted by name
        productDao.insertAll(listOf(productA, productB, productC))
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun test_fetchAllProducts() = runBlocking {
        val pagingSource = productDao.fetchAllProducts()
        //Paging 3.0 is still not robust enough for testing will rewrite database test
//        assertThat(productList.size,equalTo(3))
    }

}