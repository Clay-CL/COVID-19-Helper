package com.clay.covid_19helper.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

import com.clay.covid_19helper.R
import com.clay.covid_19helper.models.IndiaData
import com.clay.covid_19helper.ui.MainActivity
import com.clay.covid_19helper.ui.MainViewModel
import com.clay.covid_19helper.util.Resource
import kotlinx.android.synthetic.main.fragment_splash.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val DIALOG_TAG = "DIALOG_TAG"

class SplashFragment : Fragment(R.layout.fragment_splash) {

    lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = (activity as MainActivity).viewModel

        if (savedInstanceState != null) {
            //Timber.d("List of fragments size ${parentFragmentManager.fragments.size}")
            val errorDialog = parentFragmentManager.findFragmentByTag(DIALOG_TAG) as ErrorDialog?
            errorDialog?.dismiss()
            parentFragmentManager.fragments.clear()
            errorDialog?.apply {
                title = "Something Went Wrong"
                viewModel.nationalCovidData.value?.message?.let { msg ->
                    this.message = msg
                }
                setYesListener {
                    this.dismiss()
                    this.dialog?.cancel()
                    parentFragmentManager.fragments.clear()
                    viewModel.getNationalCovidData()
                }
                setNoListener {
                    this.dismiss()
                    (activity as MainActivity).onBackPressed()
                }
            }
        }

        // using nationalData since that is the first thing to be displayed
        // the others load asynchronously
        viewModel.nationalCovidData.observe(viewLifecycleOwner, Observer { resourceData ->
            when (resourceData) {
                is Resource.Success -> {
                    navigateToMainFragment(savedInstanceState)
                }
                is Resource.Error -> {
                    // create a dialog box with an error
                    errorDialog(resourceData)
                }
                is Resource.Loading -> {

                }
            }
        })

    }

    private fun errorDialog(resourceData: Resource.Error<IndiaData>) {
        ErrorDialog().apply {
            title = "Something Went Wrong"
            resourceData.message?.let { msg ->
                this.message = msg
            }
            setYesListener {
                this.dismiss()
                parentFragmentManager.fragments.clear()
                viewModel.getNationalCovidData()

            }
            setNoListener {
                this.dismiss()
                (activity as MainActivity).onBackPressed()
            }
        }.show(parentFragmentManager, DIALOG_TAG)
    }

    private fun navigateToMainFragment(savedInstanceState: Bundle?) {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.splashFragment, true).build()
        findNavController().navigate(
            R.id.action_splashFragment_to_mainFragment,
            savedInstanceState,
            navOptions
        )
    }

}
