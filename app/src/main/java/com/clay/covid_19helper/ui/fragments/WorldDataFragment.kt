package com.clay.covid_19helper.ui.fragments

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer

import com.clay.covid_19helper.R
import com.clay.covid_19helper.models.CountryCovidData
import com.clay.covid_19helper.models.CountryCovidDataItem
import com.clay.covid_19helper.ui.MainActivity
import com.clay.covid_19helper.ui.MainViewModel
import com.clay.covid_19helper.util.ImageUtils
import com.clay.covid_19helper.util.Metric
import com.clay.covid_19helper.util.Resource
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_world_data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

typealias Circles = List<CircleOptions>

class WorldDataFragment : Fragment(R.layout.fragment_world_data), OnMapReadyCallback,
    GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraIdleListener {

    lateinit var viewModel: MainViewModel

    var myAsyncTask: MapAsyncTask? = null

    private var map: GoogleMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchText.mText?.let { searchText.animateText(it) }

        viewModel = (activity as MainActivity).viewModel
        subscribeToObservers()

        mapView?.onCreate(savedInstanceState)
        //mapView?.onResume()
        mapView?.getMapAsync(this)
        map?.setOnCameraMoveListener(this)
//        mapView.getMapAsync {
//            map = it
//        }


        setUpEventListeners()


    }

    private fun setUpEventListeners() {
        icActionSettings.setOnClickListener {
            if (metric_container.visibility == View.VISIBLE) {
                metric_container.visibility = View.GONE
            } else {
                metric_container.visibility = View.VISIBLE
            }
        }
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
        viewModel.worldData.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        // TODO: get the non null LatLng Co-ordinates here
                        Timber.d("size = ${it.size}")
                        //initCircles(it)
                    }
                }
                is Resource.Error -> {
                }
                is Resource.Loading -> {
                }
            }
        })

        viewModel.metricData.observe(viewLifecycleOwner, Observer {
            it?.let {
                updateRadioGroupMetrics(it)
                //initCircles(viewModel.worldData.value?.data!!)
            }
        })

    }

    private fun initCircles(countryCovidData: CountryCovidData) {

        Timber.d("${countryCovidData.size}")

        @ColorInt var colorMetric = 0
        viewModel.metricData.value?.let {
            colorMetric = when (it) {
                Metric.NEGATIVE -> ContextCompat.getColor(
                    requireContext(),
                    R.color.colorNegativeIncrease
                )
                Metric.POSITIVE -> ContextCompat.getColor(
                    requireContext(),
                    R.color.colorPositiveIncrease
                )
                Metric.DEATH -> ContextCompat.getColor(
                    requireContext(),
                    R.color.colorDeathIncrease
                )
            }
        }

        map?.let { myMap ->
            for (region in countryCovidData) {
                if (region.lat != null && region.long != null) {
                    myMap.addCircle(
                        CircleOptions().apply {
                            center(LatLng(region.lat, region.long))
                            fillColor(colorMetric)
                            strokeWidth(0f)
                            radius(
                                when (viewModel.metricData.value!!) {
                                    Metric.NEGATIVE -> region.recovered
                                    Metric.POSITIVE -> region.confirmed
                                    Metric.DEATH -> region.deaths
                                }.toDouble() * 0.24f
                            )
                        }
                    )
                }
            }
        }
    }

//    private fun initCircles(countryCovidStack: Stack<CountryCovidDataItem>, map: GoogleMap?) {
//        initCircles(countryCovidStack.toList(), map)
//    }

    private fun initCircles(countryCovidData: ArrayList<CountryCovidDataItem>, map: GoogleMap?) {

        val latLngBounds = map?.projection?.visibleRegion?.latLngBounds

        Timber.d("${countryCovidData.size}")

        @ColorInt var colorMetric = 0
        viewModel.metricData.value?.let {
            colorMetric = when (it) {
                Metric.NEGATIVE -> ContextCompat.getColor(
                    requireContext(),
                    R.color.colorNegativeIncrease
                )
                Metric.POSITIVE -> ContextCompat.getColor(
                    requireContext(),
                    R.color.colorPositiveIncrease
                )
                Metric.DEATH -> ContextCompat.getColor(
                    requireContext(),
                    R.color.colorDeathIncrease
                )
            }
        }

        if (myAsyncTask != null) {
            if (myAsyncTask?.status == AsyncTask.Status.RUNNING) {
                myAsyncTask?.cancel(true)
            }
        }

        myAsyncTask = MapAsyncTask(
            map!!,
            latLngBounds!!,
            colorMetric,
            countryCovidData,
            viewModel.metricData.value!!,
            requireContext(),
            activity as MainActivity,
            viewModel
        )


        myAsyncTask?.execute()


    }


    private fun updateRadioGroupMetrics(metric: Metric) {
        when (metric) {
            Metric.POSITIVE -> radioButtonPositiveWorld
            Metric.NEGATIVE -> radioButtonNegativeWorld
            Metric.DEATH -> radioButtonDeathsWorld
        }.isChecked = true
    }

    //Lifecycle methods

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    //the map seems to be destroyed in onStop()

    /*override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }*/

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    override fun onMapReady(map: GoogleMap?) {

        map?.setOnCameraIdleListener(this)

        this.map = map

        Timber.d("Map is ready")
        //initCircles(viewModel.worldData.value?.data!!, map)
    }

    override fun onCameraMove() {}


    override fun onCameraIdle() {
        Timber.d("Map Pan is idle. Init the circles")
        initCircles(
            viewModel.worldDataStack.value?.toList() as ArrayList<CountryCovidDataItem>,
            map
        )
    }

    class MapAsyncTask internal constructor(
        val map: GoogleMap,
        val latLngBounds: LatLngBounds,
        @ColorInt val colorInt: Int,
        val countryCovidData: ArrayList<CountryCovidDataItem>,
        val metric: Metric,
        val context: Context,
        val activity: MainActivity,
        val viewModel: MainViewModel
    ) : AsyncTask<String, Void, Circles>() {
        

        override fun doInBackground(vararg params: String?): Circles {

            val circleOptionsList = mutableListOf<CircleOptions>()

            for (region in countryCovidData) {
                if (region.lat != null && region.long != null) {
                    if (latLngBounds.contains(LatLng(region.lat, region.long))) {
                        val circleOptions = CircleOptions().apply {
                            center(LatLng(region.lat, region.long))
                            fillColor(colorInt)
                            strokeWidth(0f)
                            radius(
                                when (metric) {
                                    Metric.NEGATIVE -> region.recovered
                                    Metric.POSITIVE -> region.confirmed
                                    Metric.DEATH -> region.deaths
                                }.toDouble() * 0.44f
                            )
                        }
                        circleOptionsList.add(
                            circleOptions
                        )

                        activity.runOnUiThread {
                            Timber.d("On Main Thread time : ${System.currentTimeMillis()}")
                            map.addCircle(circleOptions)
                            viewModel.worldDataStack.value?.remove(region)
                        }




//                        val num_metric_value = when (metric) {
//                            Metric.NEGATIVE -> region.recovered
//                            Metric.POSITIVE -> region.confirmed
//                            Metric.DEATH -> region.deaths
//                        }* 0.24f
//
//                        circleOptionsList.add(
//                            GroundOverlayOptions().apply {
//                                position(LatLng(region.lat, region.long), num_metric_value, num_metric_value)
//                                image(ImageUtils.bitmapDescriptorFromVector(context, R.drawable.ground_overlay_circle)!!)
//                                transparency(0.4f)
//                            }
//                        )

                    }
                }
            }
            return circleOptionsList.toList()
        }

        override fun onPostExecute(result: Circles?) {
            result?.let { circles ->
                circles.forEach {
                    CoroutineScope(Dispatchers.Main).launch {
                        //delay(150L)
                        //map.addCircle(it)
                    }
                }
            }
        }

    }


}
