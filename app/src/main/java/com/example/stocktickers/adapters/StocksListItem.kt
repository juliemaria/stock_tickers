package com.example.stocktickers.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.repository.model.Details
import com.example.repository.model.StockTickersSymbol
import com.example.stocktickers.R
import com.example.stocktickers.databinding.StockListItemBinding

class StocksListItem : RecyclerView.Adapter<StocksListItem.StockListItemViewHolder>() {
    private var stocksList: ArrayList<Details>? = null

    fun setStockList(stocks: StockTickersSymbol) {
        this.stocksList =
            arrayListOf(stocks.aapl, stocks.crm, stocks.tsla, stocks.msft, stocks.pega)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockListItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StockListItemBinding.inflate(inflater, parent, false)
        return StockListItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if (stocksList != null)
            return stocksList!!.size
        else
            return 0
    }

    override fun onBindViewHolder(holder: StockListItemViewHolder, position: Int) {
        stocksList?.get(position)?.let {
            holder.bind(
                symbol = it.symbol,
                name = it.name,
                currentPrice = it.price
            )
        }

    }

    class StockListItemViewHolder(private val binding: StockListItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(symbol: String, name: String, currentPrice: String) {
            binding.textViewSymbol.text = symbol
            binding.textViewName.text = name
            binding.textViewCurrentPrice.text =
                binding.textViewCurrentPrice.context.resources.getString(
                    R.string.dollar_symbol
                ).plus(currentPrice)
        }

        override fun onClick(v: View) {

        }

    }

}