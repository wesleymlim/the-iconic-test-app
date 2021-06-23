package com.uesurei.theiconicapp.network.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.uesurei.theiconicapp.network.api.TheIconicService
import com.uesurei.theiconicapp.network.db.ProductDatabase
import com.uesurei.theiconicapp.network.model.Product
import com.uesurei.theiconicapp.utils.Constants.NETWORK_PAGE_SIZE
import kotlinx.coroutines.flow.Flow

class TheIconicRepository(
    private val service: TheIconicService,
    private val database: ProductDatabase
) {
    fun getProductRepoResults(): Flow<PagingData<Product>> {
        Log.d("TheIconicRepository", "Fetch products")
        val pagingSourceFactory = { database.productDao().fetchAllProducts() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = TheIconicRemoteMediator(service, database),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}