package com.example.repository

import com.example.repository.api.StockTickersApi
import com.example.repository.model.StockTickersResponse
import retrofit2.Response

/***
 * The Repository  to cater the requirements for StockViewModel
 */
class ApplicationRepository constructor(private val stockTickersApi: StockTickersApi){

    suspend fun getStockTickersList(): Response<StockTickersResponse>
                         = stockTickersApi.getStockTickers()
}