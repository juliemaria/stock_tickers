package com.example.repository.model

import android.os.Parcelable
import com.example.repository.api.StockApiConstants
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class StockTickersSymbol(
    @SerializedName(StockApiConstants.PEGA)
    @Expose
    val pega: Details,
    @SerializedName(StockApiConstants.MSFT)
    @Expose
    val msft: Details,
    @SerializedName(StockApiConstants.CRM)
    @Expose
    val crm: Details,
    @SerializedName(StockApiConstants.AAPL)
    @Expose
    val aapl: Details,
    @SerializedName(StockApiConstants.TSLA)
    @Expose
    val tsla: Details

) : Parcelable

@Parcelize
data class Details(
    var symbol : String,
    val name: String,
    val price: String,
    val low: String,
    val high: String
) : Parcelable
