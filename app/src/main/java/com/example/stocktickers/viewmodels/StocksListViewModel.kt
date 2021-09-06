package com.example.stocktickers.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.repository.ApplicationRepository
import com.example.repository.api.StockApiConstants
import com.example.repository.model.Details
import com.example.repository.model.StockTickersResponse
import kotlinx.coroutines.*

class StocksListViewModel
constructor(private val applicationRepository: ApplicationRepository) : ViewModel() {
    val listOfStocks = MutableLiveData<StockTickersResponse>()
    var job: Job? = null
    val errorMessage = MutableLiveData<String>()
    private var selectedStockTickerDetails = MutableLiveData<Details>()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    var loading = MutableLiveData<Boolean>()
    fun getAllStocks() {
         //start the loop
        repeatFun()
    }

    private fun repeatFun(): Job {
        return CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            while (coroutineContext.isActive) {
                val response = applicationRepository.getStockTickersList()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        if (response.body() != null)
                            setListOfStocks(response.body()!!)
                        loading.value = false
                    } else {
                        onError("Error : ${response.message()} ")
                    }
                }
                delay(10000)
            }
        }
    }


    private fun setListOfStocks(body: StockTickersResponse) {
        body.aapl.symbol = StockApiConstants.AAPL
        body.crm.symbol = StockApiConstants.CRM
        body.tsla.symbol = StockApiConstants.TSLA
        body.msft.symbol = StockApiConstants.MSFT
        body.pega.symbol = StockApiConstants.PEGA
        listOfStocks.postValue(body)
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
        //Cancel the loop
        repeatFun().cancel()
    }

    fun setSelectedStockDetail(stockTickersDetailsResponse: Details) {
        this.selectedStockTickerDetails.value = stockTickersDetailsResponse
    }

    fun getSelectedStockDetails(): MutableLiveData<Details> {
        return this.selectedStockTickerDetails
    }
}