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
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_world_data.*

class WorldDataFragment : Fragment(R.layout.fragment_world_data) {

    lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        subscribeToObservers()

    }

    private fun subscribeToObservers() {
        viewModel.countryListData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {

                    // TODO: make a recycler view for the country list
                    // TODO: sort the non-null LatLng
                    // TODO: Bubble Chart
                    // TODO: Metric Buttons

//                    it.data?.let {
//                        tvTstWorld.text = it.countries.size.toString()
//                    }
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }
        })
        viewModel.worldData.observe(viewLifecycleOwner, Observer {
            when(it) {
                is Resource.Success -> {
                    it.data?.let {
                        tvTstWorld.text = it.size.toString()
                    }
                }
                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        })
    }

}
