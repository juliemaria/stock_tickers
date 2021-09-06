package com.example.stocktickers.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.repository.ApplicationRepository
import com.example.repository.api.StockApiConstants
import com.example.repository.model.StockTickersSymbol
import kotlinx.coroutines.*

class StocksListViewModel
constructor(private val applicationRepository: ApplicationRepository) : ViewModel() {
    val listOfStocks = MutableLiveData<StockTickersSymbol>()
    var job: Job? = null
    val errorMessage = MutableLiveData<String>()
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()
    fun getAllStocks() {

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = applicationRepository.getStockTickersList()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    if (response.body()!=null)
                    setListOfStocks(response.body()!!)
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun setListOfStocks(body: StockTickersSymbol) {
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
    }
}