package com.uesurei.theiconicapp.network.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.uesurei.theiconicapp.network.api.TheIconicService
import com.uesurei.theiconicapp.network.db.ProductDatabase
import com.uesurei.theiconicapp.network.db.RemoteKeys
import com.uesurei.theiconicapp.network.model.Product
import com.uesurei.theiconicapp.utils.Constants.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class TheIconicRemoteMediator(
    private val service: TheIconicService,
    private val productDatabase: ProductDatabase
) : RemoteMediator<Int, Product>() {
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Product>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val apiResponse = service.getProductsFromRepo(page, state.config.pageSize)
            val products = apiResponse.embedded.products
            val endOfPaginationReached = products.isEmpty()
            productDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    productDatabase.remoteKeysDao().clearRemoteKeys()
                    productDatabase.productDao().clearProducts()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = products.map {
                    RemoteKeys(product_Id = it.sku, prevKey = prevKey, nextKey = nextKey)
                }
                productDatabase.remoteKeysDao().insertAll(keys)
                productDatabase.productDao().insertAll(products)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Product>): RemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { product ->
                productDatabase.remoteKeysDao().remoteKeysProductId(product.sku)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Product>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { product ->
                productDatabase.remoteKeysDao().remoteKeysProductId(product.sku)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Product>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.sku?.let { productId ->
                productDatabase.remoteKeysDao().remoteKeysProductId(productId)
            }
        }
    }
}