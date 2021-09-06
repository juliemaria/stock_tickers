package com.example.stocktickers.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stocktickers.adapters.StocksListItem
import com.example.stocktickers.databinding.FragmentStocksListBinding
import com.example.stocktickers.viewmodels.StocksListViewModel


class StocksListFragment : Fragment() {
    private val stockListViewModel: StocksListViewModel by activityViewModels()
    lateinit var binding: FragmentStocksListBinding
    private val adapter = StocksListItem()
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
        fetchListOfStocks()
        subscribeObserver()
        initView()
    }

    private fun initView() {
        binding.recyclerViewStocksList.adapter = adapter
        binding.recyclerViewStocksList.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
    }

    private fun fetchListOfStocks() {
        stockListViewModel.getAllStocks()
    }

    private fun subscribeObserver() {

        stockListViewModel.listOfStocks.observe(viewLifecycleOwner, Observer {
            adapter.setStockList(it)
        })

        stockListViewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        })

        stockListViewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
    }
}