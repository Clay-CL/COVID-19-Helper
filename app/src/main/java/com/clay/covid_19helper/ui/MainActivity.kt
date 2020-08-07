package com.clay.covid_19helper.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.clay.covid_19helper.R
import com.clay.covid_19helper.repository.CovidRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = CovidRepository()
        val viewModelProviderFactory = MainViewModelProviderFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(MainViewModel::class.java)

        bottomNavView.setupWithNavController(myHostFragment.findNavController())
        bottomNavView.setOnNavigationItemReselectedListener { /* NO-OP */ }

        myHostFragment.findNavController().addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.mainFragment, R.id.heatMapFragment, R.id.worldDataFragment -> {
                    bottomNavView.visibility = View.VISIBLE
                    covidToolbar.visibility = View.VISIBLE
                }
                else -> {
                    bottomNavView.visibility = View.GONE
                    covidToolbar.visibility = View.GONE
                }
            }
        }

    }
}
