package com.example.stocktickers.listeners

import com.example.repository.model.Details

interface StockListItemClickListener {
    fun onStockListItemClick(stockTickerDetails: Details)
}