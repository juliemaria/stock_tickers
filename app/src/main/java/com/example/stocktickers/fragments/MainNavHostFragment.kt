package com.example.stocktickers.fragments

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.stocktickers.R

class MainNavHostFragment : NavHostFragment(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController.setGraph(R.navigation.navigation_graph)
    }
}