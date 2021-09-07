package com.example.stocktickers.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stocktickers.R
import com.example.stocktickers.adapters.StocksListItem
import com.example.stocktickers.databinding.FragmentStocksListBinding
import com.example.stocktickers.listeners.StockListItemClickListener
import com.example.stocktickers.viewmodels.StocksListViewModel


class StocksListFragment : Fragment(), StockListItemClickListener {
    private val stockListViewModel: StocksListViewModel by activityViewModels()
    private lateinit var binding: FragmentStocksListBinding
    private lateinit var stockListAdapter: StocksListItem
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStocksListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        fetchListOfStocks()
        subscribeObserver()
    }

    private fun initView() {
        stockListAdapter = StocksListItem(this)
        binding.recyclerViewStocksList.adapter = stockListAdapter
        binding.recyclerViewStocksList.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        stockListViewModel.setShowBackIcon(false)
        stockListViewModel.setTitle(resources.getString(R.string.app_name))
    }

    private fun fetchListOfStocks() {
        binding.progressBar.visibility = View.VISIBLE
        //Call to fetch the list of stock tickers
        stockListViewModel.getAllStocks()
    }

    private fun subscribeObserver() {

        stockListViewModel.listOfStocks.observe(viewLifecycleOwner, Observer {
            stockListAdapter.setStockList(it)
            stockListViewModel.loading.postValue(false)
        })

        stockListViewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            //The actual exception
//            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            //Shows the general message
            Toast.makeText(
                requireActivity(),
                resources.getString(R.string.error_message),
                Toast.LENGTH_SHORT
            ).show()
        })

        stockListViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
    }

    override fun onStockListItemClick(position: Int) {
        stockListViewModel.setSelectedPosition(position)
        findNavController().navigate(R.id.action_stock_list_to_detail)
    }
}