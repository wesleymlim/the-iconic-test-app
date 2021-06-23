package com.uesurei.theiconicapp.network.db

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.uesurei.theiconicapp.network.model.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<Product>)

    @Query("SELECT * FROM products")
    fun fetchAllProducts(): PagingSource<Int, Product>

    @Query("DELETE FROM products")
    suspend fun clearProducts()
}