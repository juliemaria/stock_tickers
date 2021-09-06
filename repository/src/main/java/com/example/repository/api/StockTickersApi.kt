package com.example.repository.api

import com.example.repository.model.StockTickersResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface StockTickersApi {
    @GET("interview/favorite-stocks")
    suspend fun getStockTickers(): Response<StockTickersResponse>

    companion object {
        private var stockTickersApi: StockTickersApi? = null
        fun getInstance() : StockTickersApi {
            if (stockTickersApi == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(StockApiConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                stockTickersApi = retrofit.create(StockTickersApi::class.java)
            }
            return stockTickersApi!!
        }

    }
}