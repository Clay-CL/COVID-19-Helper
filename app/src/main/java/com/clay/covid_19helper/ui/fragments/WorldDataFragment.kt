package com.clay.covid_19helper.ui.fragments

import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.clay.covid_19helper.R
import com.clay.covid_19helper.adapters.CountryListAdapter
import com.clay.covid_19helper.models.WorldCountryCovidData
import com.clay.covid_19helper.ui.MainActivity
import com.clay.covid_19helper.ui.MainViewModel
import com.clay.covid_19helper.util.AnimationUtils.collapseRecyclerView
import com.clay.covid_19helper.util.AnimationUtils.expandMap
import com.clay.covid_19helper.util.AnimationUtils.expandToolbar
import com.clay.covid_19helper.util.AnimationUtils.resetToolbar
import com.clay.covid_19helper.util.AnimationUtils.shrinkMap
import com.clay.covid_19helper.util.Constants
import com.clay.covid_19helper.util.Constants.MAP_CAMERA_ZOOM
import com.clay.covid_19helper.util.Constants.countries_bb
import com.clay.covid_19helper.util.Metric
import com.clay.covid_19helper.util.MiscUtils.getZoomLevel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import kotlinx.android.synthetic.main.fragment_world_data.*
import timber.log.Timber
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


class WorldDataFragment : Fragment(R.layout.fragment_world_data), OnMapReadyCallback,
    GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraIdleListener {

    lateinit var viewModel: MainViewModel

    lateinit var countryListAdapter: CountryListAdapter

    var myAsyncTask: MapAsyncTask? = null

    private var map: GoogleMap? = null
    private var orgHeight = 0
    private var orgWidth = 0
    private var orgTop = 0
    private var toggle = false

    private var isMapReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        // re-instantiating stack data
        viewModel.addToWorldStatck(viewModel.worldData.value?.data!!)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchText.mText?.let { searchText.animateText(it) }

        subscribeToObservers()

        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)
        map?.setOnCameraMoveListener(this)

        setUpEventListeners()
        setUpRecyclerView()

    }

    private fun setUpRecyclerView() {
        countryListAdapter =
            CountryListAdapter(requireContext(), this, viewModel.worldData.value?.data!!).apply {
                collapseFun = {
                    onItemSelected(it)
                }
            }
        countryList.apply {
            adapter = countryListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun onItemSelected(country: WorldCountryCovidData) {
        searchText.setText(country.location)
        searchText.clearFocus()
        hideKeyBoard()
        collapseRecyclerView()
        if (info_container.height > 0) {
            //zoom to the latlng
            populateInfoLyt(country)
            if (info_container.height > 0) {
                zoomToLatLng(country)
            }
            //zoomToLatLng(LatLng(country.latitude, country.longitude))
        } else {
            shrinkMapAndExpandInfoLayout(country)
        }

    }

    private fun zoomToLatLng(country: WorldCountryCovidData) {

//        map?.animateCamera(
//            CameraUpdateFactory.newLatLngZoom(
//                latLng,
//                getZoomLevel(
//                    when (viewModel.metricData.value!!) {
//                        Metric.NEGATIVE -> country.recovered
//                        Metric.POSITIVE -> country.confirmed
//                        Metric.DEATH -> country.dead
//                    } * 0.44f
//                )
//            )
//        )

        try {
            map?.animateCamera(
                CameraUpdateFactory.newLatLngBounds(
                    countries_bb[country.countryCode.toUpperCase(Locale.ROOT)],
                    1
                )
            )
        }catch (e: Exception){

        }



    }

    private fun setUpEventListeners() {
        icActionSettings.setOnClickListener {
            if (metric_container.visibility == View.VISIBLE) {
                metric_container.visibility = View.GONE
            } else {
                metric_container.visibility = View.VISIBLE
            }
        }

        searchText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                //expand the lyt
                if (!toggle) {
                    toggle = !toggle
                    icAction.setImageResource(R.drawable.search_to_close)
                    (icAction.drawable as AnimatedVectorDrawable).start()

                    expandToolbar()
                } else if (toggle && info_container.height > 0) {
                    expandToolbar(true)
                }

            }
        }

        searchText.doOnTextChanged { text, _, _, _ ->
            countryListAdapter.filter.filter(text)
        }

        icAction.setOnClickListener {
            if (toggle) {
                toggle = !toggle
                collapseRecyclerViewAndResetToolbar()
                icAction.setImageResource(R.drawable.close_to_search)
                (icAction.drawable as AnimatedVectorDrawable).start()
                //icAction.setImageResource(R.drawable.search_to_close)
                searchText.setText("")
                searchText.clearFocus()
                hideKeyBoard()
                if (info_container.height > 0) {
                    mapView.expandMap(parent_container.height)
                }
            }
        }

        metric_container.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButtonDeathsWorld -> updateMetric(Metric.DEATH)
                R.id.radioButtonPositiveWorld -> updateMetric(Metric.POSITIVE)
                R.id.radioButtonNegativeWorld -> updateMetric(Metric.NEGATIVE)
            }
        }

        info_container.setOnTouchListener { v, event ->
            info_container.performClick()
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> {
                    Timber.d("-- Released --")
                    toggle = !toggle
                    collapseRecyclerViewAndResetToolbar()
                    icAction.setImageResource(R.drawable.close_to_search)
                    (icAction.drawable as AnimatedVectorDrawable).start()
                    //icAction.setImageResource(R.drawable.search_to_close)
                    searchText.setText("")
                    searchText.clearFocus()
                    mapView.expandMap(parent_container.height)
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    Timber.d("Top of info_lyt : ${info_container.top}, Y : ${event.rawY} ")
                    if (mapView.layoutParams.height <= parent_container.height) {
                        mapView.layoutParams.height =
                            mapView.layoutParams.height + (event.rawY.toInt() - info_container.top)
                        if (mapView.layoutParams.height > parent_container.height) {
                            mapView.layoutParams.height = parent_container.height
                        }
                        mapView.requestLayout()
                    }
                    true
                }
                else -> true
            }

        }

    }

    private fun hideKeyBoard() {
        val inputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun updateMetric(metric: Metric) {
        viewModel.metricData.postValue(metric)
    }

    private fun expandToolbar(onlyList: Boolean = false) {
        if (!onlyList) {
            orgWidth = toolbar1.width
            orgTop = toolbar1.top
        }
        orgHeight = toolbar1.height
        toolbar1.expandToolbar(250, parent_container, onlyList)
    }

    private fun collapseRecyclerViewAndResetToolbar() {
        toolbar1.resetToolbar(250, orgWidth, orgTop, orgHeight)
        countryList.scrollToPosition(0)
    }

    fun collapseRecyclerView() {
        toolbar1.collapseRecyclerView(250, orgHeight)
    }

    fun shrinkMapAndExpandInfoLayout(country: WorldCountryCovidData) {
        populateInfoLyt(country)
        mapView.shrinkMap()
        zoomToLatLng(country)
    }

    private fun populateInfoLyt(country: WorldCountryCovidData) {
        // populate the data
        tvConfirmed.text = NumberFormat.getInstance().format(country.confirmed).toString()
        tvRecovered.text = NumberFormat.getInstance().format(country.recovered).toString()
        tvDeath.text = NumberFormat.getInstance().format(country.dead).toString()
        val outputDateFormat = SimpleDateFormat("dd MMM, yyyy", Locale.US)
        tvDate.text = "As of ${country.updated.substring(0..10).trim()} "
    }

    private fun subscribeToObservers() {

        viewModel.metricData.observe(viewLifecycleOwner, Observer {
            it?.let {
                updateRadioGroupMetrics(it)
                Timber.d("Initial State of Map is $isMapReady")
                if (isMapReady) {
                    viewModel.addToWorldStatck(viewModel.worldData.value?.data!!)
                    map?.clear()
                    initCircles(
                        (viewModel.worldData.value?.data?.data as ArrayList<WorldCountryCovidData>?)!!,
                        map
                    )
                }
            }
        })

    }

    private fun initCircles(countryCovidData: ArrayList<WorldCountryCovidData>, map: GoogleMap?) {

        val latLngBounds = map?.projection?.visibleRegion?.latLngBounds

        Timber.d("${countryCovidData.size}")

        @ColorInt var colorMetric = 0
        viewModel.metricData.value?.let {
            colorMetric = when (it) {
                Metric.NEGATIVE -> ContextCompat.getColor(
                    requireContext(),
                    R.color.colorNegativeIncreaseWorld
                )
                Metric.POSITIVE -> ContextCompat.getColor(
                    requireContext(),
                    R.color.colorPositiveIncreaseWorld
                )
                Metric.DEATH -> ContextCompat.getColor(
                    requireContext(),
                    R.color.colorDeathIncreaseWorld
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

        try {
            map?.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireContext(),
                    R.raw.map_style
                )
            )
        } catch (e: Exception) {
            Timber.e("Map style couldn't be loaded : ${e.message}")
        }

        isMapReady = true

        Timber.d("Map is ready")
    }

    override fun onCameraMove() {}

    override fun onCameraIdle() {
        Timber.d("Map Pan is idle. Init the circles")
        initCircles(viewModel.worldDataStack.value!!, map)
    }

    class MapAsyncTask internal constructor(
        val map: GoogleMap,
        val latLngBounds: LatLngBounds,
        @ColorInt val colorInt: Int,
        val countryCovidData: ArrayList<WorldCountryCovidData>,
        val metric: Metric,
        val context: Context,
        val activity: MainActivity,
        val viewModel: MainViewModel
    ) : AsyncTask<String, Void, ArrayList<WorldCountryCovidData>>() {

        override fun doInBackground(vararg params: String?): ArrayList<WorldCountryCovidData>? {
            val countriesToPop = arrayListOf<WorldCountryCovidData>()
            if (countryCovidData.isNotEmpty()) {
                for (region in countryCovidData) {
                    if (latLngBounds.contains(LatLng(region.latitude, region.longitude))) {
                        val circleOptions = CircleOptions().apply {
                            center(LatLng(region.latitude, region.longitude))
                            fillColor(colorInt)
                            strokeWidth(0f)
                            radius(
                                when (metric) {
                                    Metric.NEGATIVE -> region.recovered
                                    Metric.POSITIVE -> region.confirmed
                                    Metric.DEATH -> region.dead
                                }.toDouble() * 0.44f
                            )
                        }
                        activity.runOnUiThread {
                            Timber.d("On Main Thread time : ${System.currentTimeMillis()}")
                            map.addCircle(circleOptions)
                            countriesToPop.add(region)
//                            viewModel.worldDataStack.value?.remove(region)
                        }
                    }
                }
            }

            return countriesToPop
        }

        override fun onPostExecute(countriesToPop: ArrayList<WorldCountryCovidData>?) {
            viewModel.worldDataStack.value?.removeAll(countriesToPop!!)
        }

    }


}
