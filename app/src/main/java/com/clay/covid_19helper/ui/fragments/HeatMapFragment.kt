package com.clay.covid_19helper.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.clay.covid_19helper.R
import com.clay.covid_19helper.ui.MainActivity
import com.clay.covid_19helper.ui.MainViewModel
import com.clay.covid_19helper.util.Resource
import kotlinx.android.synthetic.main.fragment_heat_map.*
import kotlinx.android.synthetic.main.fragment_world_data.*

class HeatMapFragment : Fragment(R.layout.fragment_heat_map) {

    lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        subscribeToObservers()

    }

    private fun subscribeToObservers() {
        viewModel.indiaData.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success -> {
                    it.data?.let {
                        tvtTestheat.text = it.size.toString()
                    }
                }
                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        })
    }

}
