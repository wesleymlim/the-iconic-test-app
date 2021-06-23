package com.uesurei.theiconicapp.network.api

import com.uesurei.theiconicapp.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface TheIconicService {

    @Headers("Accept: */*")
    @GET("products")
    suspend fun getProductsFromRepo(
        @Query("page") page: Int,
        @Query("page_size") page_size: Int
    ): CatalogRepoResponse


    companion object {
        fun create(): TheIconicService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TheIconicService::class.java)
        }
    }
}