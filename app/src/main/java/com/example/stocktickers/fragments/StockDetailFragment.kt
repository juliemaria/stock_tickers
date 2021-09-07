package com.example.stocktickers.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.repository.model.Details
import com.example.stocktickers.R
import com.example.stocktickers.databinding.FragmentStockDetailBinding
import com.example.stocktickers.viewmodels.StocksListViewModel

class StockDetailFragment : Fragment() {
    lateinit var binding: FragmentStockDetailBinding
    private val stockListViewModel: StocksListViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStockDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObserver()
    }

    private fun subscribeObserver() {
        stockListViewModel.listOfStocks.observe(viewLifecycleOwner, Observer {
            setValuesToViews(it[stockListViewModel.getSelectedPosition()])
        })
    }

    private fun setValuesToViews(stockDetail: Details) {
        binding.textViewSymbolValue.text = stockDetail.symbol
        binding.textViewNameValue.text = stockDetail.name
        binding.textViewCurrentPriceValue.text =
            resources.getString(R.string.dollar_symbol).plus(stockDetail.price)
        binding.textViewLowestPriceValue.text =
            resources.getString(R.string.dollar_symbol).plus(stockDetail.low)
        binding.textViewHighestPriceValue.text =
            resources.getString(R.string.dollar_symbol).plus(stockDetail.high)
        stockListViewModel.setShowBackIcon(true)
        stockListViewModel.setTitle(stockDetail.symbol)
    }
}