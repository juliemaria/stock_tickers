package com.example.stocktickers.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.repository.ApplicationRepository
import com.example.repository.api.StockTickersApi
import com.example.stocktickers.R
import com.example.stocktickers.viewmodels.StocksListViewModel
import com.example.stocktickers.viewmodels.StocksViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var stockListViewModel: StocksListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setViewModel()
    }

    private fun setViewModel() {
        val retrofitService = StockTickersApi.getInstance()
        val applicationRepository = ApplicationRepository(retrofitService)
        stockListViewModel =
            ViewModelProvider(this, StocksViewModelFactory(applicationRepository)).get(
                StocksListViewModel::class.java
            )
        subscribeObservers()
    }

    private fun subscribeObservers(){
        stockListViewModel.title.observe(this, Observer {
            supportActionBar?.title = it ?: resources.getString(R.string.app_name)
        })

        stockListViewModel.showBackIcon.observe(this, Observer { show ->
            if (show){
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
            else{
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}