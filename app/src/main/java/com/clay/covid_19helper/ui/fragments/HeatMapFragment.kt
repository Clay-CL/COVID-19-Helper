package com.clay.covid_19helper.ui.fragments

import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.clay.covid_19helper.R
import com.clay.covid_19helper.adapters.ProvinceListAdapter
import com.clay.covid_19helper.models.CountryCovidData
import com.clay.covid_19helper.models.CountryCovidDataItem
import com.clay.covid_19helper.ui.MainActivity
import com.clay.covid_19helper.ui.MainViewModel
import com.clay.covid_19helper.util.AnimationUtils.collapseRecyclerView
import com.clay.covid_19helper.util.AnimationUtils.expandCircle
import com.clay.covid_19helper.util.AnimationUtils.expandMap
import com.clay.covid_19helper.util.AnimationUtils.expandToolbar
import com.clay.covid_19helper.util.AnimationUtils.resetToolbar
import com.clay.covid_19helper.util.AnimationUtils.shrinkCircle
import com.clay.covid_19helper.util.AnimationUtils.shrinkMap
import com.clay.covid_19helper.util.Constants
import com.clay.covid_19helper.util.Metric
import com.clay.covid_19helper.util.MiscUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.data.geojson.GeoJsonLayer
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle
import kotlinx.android.synthetic.main.fragment_heat_map.*
import kotlinx.android.synthetic.main.fragment_heat_map.mapView
import timber.log.Timber
import java.lang.Exception
import java.text.NumberFormat
import kotlin.math.hypot
import kotlin.math.max


class HeatMapFragment : Fragment(R.layout.fragment_heat_map), OnMapReadyCallback,
    GoogleMap.OnCameraMoveListener {

    private var map: GoogleMap? = null
    lateinit var viewModel: MainViewModel

    private var isMapReady = false

    private var orgHeight = 0
    private var orgWidth = 0
    private var orgTop = 0
    private var toggleExpansion = false

    private var toggleLegendView = true

    private lateinit var provinceListAdapter: ProvinceListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchTextIndia.mText?.let { searchTextIndia.animateText(it) }

        viewModel = (activity as MainActivity).viewModel
        subscribeToObservers()
        setupEventListeners()
        setUpRecyclerView()

        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)
        map?.setOnCameraMoveListener(this)


    }

    private fun setUpRecyclerView() {
        provinceListAdapter = ProvinceListAdapter(viewModel.indiaData.value?.data!!).apply {
            collapseFun = {
                onItemSelected(it)
            }
        }
        provinceList.apply {
            adapter = provinceListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun onItemSelected(province: CountryCovidDataItem) {
        searchTextIndia.setText(province.provinceState)
        searchTextIndia.clearFocus()
        hideKeyBoard()
        collapseRecyclerView()
        if (info_container_india.height > 0) {
            //zoom to the latlng
            populateInfoLyt(province)
            zoomToLatLng(province)
            //zoomToLatLng(LatLng(country.latitude, country.longitude))
        } else {
            shrinkMapAndExpandInfoLayout(province)
        }
    }

    private fun zoomToLatLng(province: CountryCovidDataItem) {
        map?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    province.lat!!,
                    province.long!!
                ),
                7f
            )
        )
    }

    private fun populateInfoLyt(province: CountryCovidDataItem) {
        tvConfirmedIndia.text = NumberFormat.getInstance().format(province.confirmed).toString()
        tvRecoveredIndia.text = NumberFormat.getInstance().format(province.recovered).toString()
        tvDeathIndia.text = NumberFormat.getInstance().format(province.deaths).toString()
        tvDateIndia.text = province.lastUpdate.toString()
    }


    private fun setupEventListeners() {
        metricContainerIndia.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButtonDeathsIndia -> updateMetric(Metric.DEATH)
                R.id.radioButtonPositiveIndia -> updateMetric(Metric.POSITIVE)
                R.id.radioButtonNegativeIndia -> updateMetric(Metric.NEGATIVE)
            }
        }

        searchTextIndia.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (!toggleExpansion) {
                    toggleExpansion = !toggleExpansion
                    icActionIndia.setImageResource(R.drawable.search_to_close)
                    (icActionIndia.drawable as AnimatedVectorDrawable).start()

                    expandToolbar()
                } else if (toggleExpansion && info_container_india.height > 0) {
                    expandToolbar(true)
                }
            }
        }

        searchTextIndia.doOnTextChanged { text, _, _, _ ->
            provinceListAdapter.filter.filter(text)
        }

        icActionIndia.setOnClickListener {
            if (toggleExpansion) {
                toggleExpansion = !toggleExpansion
                collapseRecyclerViewAndResetToolbar()
                icActionIndia.setImageResource(R.drawable.close_to_search)
                (icActionIndia.drawable as AnimatedVectorDrawable).start()
                //icAction.setImageResource(R.drawable.search_to_close)
                searchTextIndia.setText("")
                searchTextIndia.clearFocus()
                hideKeyBoard()
                if (info_container_india.height > 0) {
                    mapView.expandMap(parent_container_india.height)
                }
                setDefaultIndiaZoom()
            }
        }


        showLegend.setOnClickListener {
            if (!toggleLegendView) {
                showLegendAnim()
            }
        }

        toggleLegend.setOnClickListener {
            if (toggleLegendView) {
                hideLegendView()
            }
        }

        info_container_india.setOnTouchListener { v, event ->
            info_container_india.performClick()
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_UP -> {
                    Timber.d("-- Released --")
                    toggleExpansion = !toggleExpansion
                    collapseRecyclerViewAndResetToolbar()
                    icActionIndia.setImageResource(R.drawable.close_to_search)
                    (icActionIndia.drawable as AnimatedVectorDrawable).start()
                    //icAction.setImageResource(R.drawable.search_to_close)
                    searchTextIndia.setText("")
                    searchTextIndia.clearFocus()
                    mapView.expandMap(parent_container_india.height)
                    setDefaultIndiaZoom()
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    Timber.d("Top of info_lyt : ${info_container_india.top}, Y : ${event.rawY} ")
                    if (mapView.layoutParams.height <= parent_container_india.height) {
                        mapView.layoutParams.height =
                            mapView.layoutParams.height + (event.rawY.toInt() - info_container_india.top)
                        if (mapView.layoutParams.height > parent_container_india.height) {
                            mapView.layoutParams.height = parent_container_india.height
                        }
                        mapView.requestLayout()
                    }
                    true
                }
                else -> true
            }

        }

    }

    private fun subscribeToObservers() {
        viewModel.metricData.observe(viewLifecycleOwner, Observer { metric ->
            updateRadioGroupMetrics(metric)
            updateLegend(metric)
            if (isMapReady) {
                map?.clear()
                LoadGeoJsonAsyncTask(
                    map!!,
                    requireContext(),
                    activity as MainActivity,
                    viewModel.indiaData.value?.data!!,
                    metric
                ).execute()
            }
        })

    }

    private fun updateMetric(metric: Metric) {
        viewModel.metricData.postValue(metric)
    }

    private fun updateLegend(metric: Metric) {
        when (metric) {
            Metric.NEGATIVE -> {
                color1.background =
                    ContextCompat.getColor(requireContext(), R.color.negative_val_1).toDrawable()
                color2.background =
                    ContextCompat.getColor(requireContext(), R.color.negative_val_2).toDrawable()
                color3.background =
                    ContextCompat.getColor(requireContext(), R.color.negative_val_3).toDrawable()
                color4.background =
                    ContextCompat.getColor(requireContext(), R.color.negative_val_4).toDrawable()

                tvRange1.text = "5L and above"
                tvRange2.text = "1L to 5L"
                tvRange3.text = "10K to 1L"
                tvRange4.text = "0 to 10K"

            }
            Metric.POSITIVE -> {
                color1.background =
                    ContextCompat.getColor(requireContext(), R.color.positive_val_1).toDrawable()
                color2.background =
                    ContextCompat.getColor(requireContext(), R.color.positive_val_2).toDrawable()
                color3.background =
                    ContextCompat.getColor(requireContext(), R.color.positive_val_3).toDrawable()
                color4.background =
                    ContextCompat.getColor(requireContext(), R.color.positive_val_4).toDrawable()

                tvRange1.text = "5L and above"
                tvRange2.text = "1L to 5L"
                tvRange3.text = "10K to 1L"
                tvRange4.text = "0 to 10K"

            }
            Metric.DEATH -> {
                color1.background =
                    ContextCompat.getColor(requireContext(), R.color.death_val_1).toDrawable()
                color2.background =
                    ContextCompat.getColor(requireContext(), R.color.death_val_2).toDrawable()
                color3.background =
                    ContextCompat.getColor(requireContext(), R.color.death_val_3).toDrawable()
                color4.background =
                    ContextCompat.getColor(requireContext(), R.color.death_val_4).toDrawable()

                tvRange1.text = "10K and above"
                tvRange2.text = "5K to 10K"
                tvRange3.text = "1K to 5K"
                tvRange4.text = "0 to 1K"

            }
        }
    }

    private fun updateRadioGroupMetrics(metric: Metric) {
        when (metric) {
            Metric.POSITIVE -> radioButtonPositiveIndia
            Metric.NEGATIVE -> radioButtonNegativeIndia
            Metric.DEATH -> radioButtonDeathsIndia
        }.isChecked = true
    }

    override fun onMapReady(map: GoogleMap?) {
        this.map = map
        setDefaultIndiaZoom()
        isMapReady = true
        Timber.d("Map Ready")
        try {
            map?.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireContext(),
                    R.raw.map_style_india
                )
            )
        } catch (e: Exception) {
            Timber.e("Map style couldn't be loaded : ${e.message}")
        }


//        LoadGeoJsonAsyncTask(
//            map!!,
//            requireContext(),
//            activity as MainActivity,
//            viewModel.indiaData.value?.data!!,
//            viewModel.metricData.value!!
//        ).execute()


    }

    private fun setDefaultIndiaZoom() {
        map?.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                Constants.countries_bb["IN"],
                2
            )
        )
    }

    override fun onCameraMove() {
        //
    }

    private fun hideKeyBoard() {
        val inputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    //ui related anims

    private fun expandToolbar(onlyList: Boolean = false) {
        if (!onlyList) {
            orgWidth = toolbarIndia.width
            orgTop = toolbarIndia.top
        }
        orgHeight = toolbarIndia.height
        toolbarIndia.expandToolbar(250, parent_container_india, onlyList)
    }

    private fun collapseRecyclerViewAndResetToolbar() {
        toolbarIndia.resetToolbar(250, orgWidth, orgTop, orgHeight)
        provinceList.scrollToPosition(0)
    }

    private fun collapseRecyclerView() {
        toolbarIndia.collapseRecyclerView(250, orgHeight)
    }


    private fun showLegendAnim() {

        Timber.d("Anim started")

        val xCenter = showLegend.x / 2
        val yCenter = showLegend.y / 2

        val startRadius = 0
        val endRadius =
            hypot(parent_container_india.width.toDouble(), parent_container_india.height.toDouble())

        val revealAnim = ViewAnimationUtils.createCircularReveal(
            legendContainer,
            xCenter.toInt(), yCenter.toInt(), startRadius.toFloat(), endRadius.toFloat()
        )
        legendContainer.visibility = VISIBLE
        revealAnim.doOnStart {
            showLegend.shrinkCircle()
        }
        revealAnim.interpolator = AccelerateInterpolator()
        revealAnim.start()

        toggleLegendView = !toggleLegendView

    }

    private fun hideLegendView() {
        val xCenter = showLegend.right - (showLegend.width / 2)
        val yCenter = showLegend.bottom - (showLegend.height / 2)

        val startRadius = max(parent_container_india.width, parent_container_india.height)
        val endRadius = 0

        val hideAnim = ViewAnimationUtils.createCircularReveal(
            legendContainer,
            xCenter, yCenter, startRadius.toFloat(), endRadius.toFloat()
        )

        hideAnim.doOnEnd {
            legendContainer.visibility = GONE

        }
        hideAnim.interpolator = AccelerateDecelerateInterpolator()
        showLegend.expandCircle()
        hideAnim.start()
        toggleLegendView = !toggleLegendView
    }

    private fun shrinkMapAndExpandInfoLayout(province: CountryCovidDataItem) {
        populateInfoLyt(province)
        mapView.shrinkMap()
        zoomToLatLng(province)
    }

    // end anims


    class LoadGeoJsonAsyncTask(
        val map: GoogleMap,
        val context: Context,
        val activity: MainActivity,
        val data: CountryCovidData,
        val metric: Metric
    ) : AsyncTask<String, Void, Unit>() {

        override fun onPreExecute() {
            map.clear()
        }

        override fun doInBackground(vararg params: String?) {

            for (region in data) {
                try {
                    val layer = GeoJsonLayer(
                        map, Constants.indian_regions[region.provinceState]!!,
                        context
                    )
                    Timber.d("${region.confirmed}")
                    @ColorInt val myColor = ContextCompat.getColor(
                        context, when (metric) {
                            Metric.NEGATIVE -> MiscUtils.getColor(region.recovered, metric)
                            Metric.POSITIVE -> MiscUtils.getColor(region.confirmed, metric)
                            Metric.DEATH -> MiscUtils.getColor(region.deaths, metric)
                        }
                    )
                    val polygonStyle = GeoJsonPolygonStyle()
                    polygonStyle.fillColor = myColor
                    polygonStyle.strokeWidth = 0.5f
                    polygonStyle.strokeColor = ContextCompat.getColor(context, R.color.black)
                    layer.features.forEach {
                        it.polygonStyle = polygonStyle
                    }

                    activity.runOnUiThread {
                        layer.addLayerToMap()
                    }
                } catch (e: Exception) {
                    Timber.d("${region.provinceState}")
                }
            }
            return
        }


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

}
