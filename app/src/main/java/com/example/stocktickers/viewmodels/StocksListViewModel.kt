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
    private val stockTickersResponse = MutableLiveData<StockTickersResponse>()
    var listOfStocks = MutableLiveData<ArrayList<Details>>()
    var job: Job? = null
    val errorMessage = MutableLiveData<String>()
    private var selectedPosition = MutableLiveData<Int>()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    var loading = MutableLiveData<Boolean>()
    fun getAllStocks() {
        loading.postValue(true)
        //start the loop
        getAllStocksJob()
    }

    private fun getAllStocksJob(): Job {
        return CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            while (coroutineContext.isActive) {
                val response = applicationRepository.getStockTickersList()
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            if (response.body() != null)
                                setSymbolOfStock(response.body()!!)
                        } else {
                            onError("Error : ${response.message()} ")
                        }
                    } catch (exception: Exception) {
                        onError("Error : ${exception.message} ")
                    }
                }
                delay(10000)
            }
        }
    }

    private fun setSymbolOfStock(body: StockTickersResponse) {
        body.aapl.symbol = StockApiConstants.AAPL
        body.crm.symbol = StockApiConstants.CRM
        body.tsla.symbol = StockApiConstants.TSLA
        body.msft.symbol = StockApiConstants.MSFT
        body.pega.symbol = StockApiConstants.PEGA
        stockTickersResponse.postValue(body)
        setListOfStocks()
    }

    private fun setListOfStocks() {
        stockTickersResponse.value.let { stockTicker ->
            if (stockTicker != null) {
                val stockList = arrayListOf(
                    stockTicker.aapl,
                    stockTicker.crm,
                    stockTicker.tsla,
                    stockTicker.msft,
                    stockTicker.pega
                )
                listOfStocks.postValue(stockList)
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.postValue(message)
        loading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
        //Cancel the loop
        getAllStocksJob().cancel()
    }

    fun setSelectedPosition(position: Int) {
        this.selectedPosition.value = position
    }

    fun getSelectedPosition(): Int {
        return this.selectedPosition.value ?: 0
    }
}