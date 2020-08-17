package com.clay.covid_19helper.ui.fragments

import android.content.pm.ResolveInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer

import com.clay.covid_19helper.R
import com.clay.covid_19helper.adapters.CovidSparkAdapter
import com.clay.covid_19helper.models.CasesTimeSeries
import com.clay.covid_19helper.ui.MainActivity
import com.clay.covid_19helper.ui.MainViewModel
import com.clay.covid_19helper.util.Increase
import com.clay.covid_19helper.util.Metric
import com.clay.covid_19helper.util.Resource
import com.clay.covid_19helper.util.TimeScale
import com.robinhood.spark.animation.SparkAnimator
import com.robinhood.ticker.TickerUtils
import kotlinx.android.synthetic.main.fragment_main.*
import timber.log.Timber
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var sparkAdapter: CovidSparkAdapter
    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setUpAdapter()
        subscribeToObservers()

    }

    private fun subscribeToObservers() {
        viewModel.nationalCovidData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    setUpEventListeners()
                    it.data?.let { indiaData ->
                        // Create SparkAdapter
                        // Add Graph functionality
                        // TODO: Heat MAP

                        updateGraph(indiaData.casesTimeSeries)

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
                updateColorSparkChart(it)
                sparkAdapter.metric = it
                viewModel.nationalCovidData.value?.data?.casesTimeSeries?.let {
                    updateGraph(it)
                }
            }

        })

        viewModel.timeScale.observe(viewLifecycleOwner, Observer {
            it?.let {
                updateRadioGroupTimeScale(it)
                sparkAdapter.daysAgo = it
                sparkAdapter.notifyDataSetChanged()
            }

        })

        viewModel.increase.observe(viewLifecycleOwner, Observer {
            it?.let {
                updateRadioGroupIncrease(it)
                sparkAdapter.increase = it
                viewModel.nationalCovidData.value?.data?.casesTimeSeries?.let {
                    updateGraph(it)
                }
            }

        })

        viewModel.stateTimelineData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {

                    }
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
            }
        })

    }

    private fun updateDisplayMetric(metric: Metric) {
        viewModel.metricData.postValue(metric)
    }

    private fun updateIncrease(increase: Increase) {
        viewModel.increase.postValue(increase)
    }

    private fun updateColorSparkChart(metric: Metric) {
        @ColorInt val colorMetric = when (metric) {
            Metric.NEGATIVE -> ContextCompat.getColor(
                requireContext(),
                R.color.colorNegativeIncrease
            )
            Metric.POSITIVE -> ContextCompat.getColor(
                requireContext(),
                R.color.colorPositiveIncrease
            )
            Metric.DEATH -> ContextCompat.getColor(requireContext(), R.color.colorDeathIncrease)
        }

        sparkChart.lineColor = colorMetric
        casesTicker.textColor = colorMetric

    }

    private fun updateRadioGroupTimeScale(timeScale: TimeScale) {
        when (timeScale) {
            TimeScale.MONTH -> radioButtonMonth
            TimeScale.WEEK -> radioButtonWeek
            TimeScale.MAX -> radioButtonMax
        }.isChecked = true
    }

    private fun updateRadioGroupMetrics(metric: Metric) {
        when (metric) {
            Metric.POSITIVE -> radioButtonPositive
            Metric.NEGATIVE -> radioButtonNegative
            Metric.DEATH -> radioButtonDeaths
        }.isChecked = true
    }

    private fun updateRadioGroupIncrease(increase: Increase) {
        when (increase) {
            Increase.DAILY -> radioButtonDaily
            Increase.TOTAL -> radioButtonTotal
        }.isChecked = true
    }

    private fun updateGraph(covidData: List<CasesTimeSeries>) {
        updateInfoForDate(covidData.last())
        sparkAdapter.dailyNationalData = covidData
        sparkAdapter.notifyDataSetChanged()
    }

    private fun updateInfoForDate(covidData: CasesTimeSeries) {

        Timber.d(covidData.dailyconfirmed)


        val numCases = when (sparkAdapter.increase) {
            Increase.DAILY -> {
                when (sparkAdapter.metric) {
                    Metric.NEGATIVE -> covidData.dailyrecovered
                    Metric.POSITIVE -> covidData.dailyconfirmed
                    Metric.DEATH -> covidData.dailydeceased
                }.toInt()
            }
            Increase.TOTAL -> {
                when (sparkAdapter.metric) {
                    Metric.NEGATIVE -> covidData.totalrecovered
                    Metric.POSITIVE -> covidData.totalconfirmed
                    Metric.DEATH -> covidData.totaldeceased
                }.toInt()
            }
        }



        casesTicker.text = NumberFormat.getInstance().format(numCases)
        // val outputDateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.US)
        dateLabel.text = covidData.date
    }


    private fun setUpAdapter() {
        sparkAdapter = CovidSparkAdapter()
        sparkChart.adapter = sparkAdapter

    }

    private fun setUpEventListeners() {

        casesTicker.setCharacterLists(TickerUtils.provideNumberList())

        sparkChart.isScrubEnabled = true
        sparkChart.setScrubListener { itemData ->
            if (itemData is CasesTimeSeries) {
                updateInfoForDate(itemData)
            }
        }

        radioGrpDuration.setOnCheckedChangeListener { _, checkedId ->
            sparkAdapter.daysAgo = when (checkedId) {
                R.id.radioButtonMonth -> TimeScale.MONTH
                R.id.radioButtonWeek -> TimeScale.WEEK
                else -> TimeScale.MAX
            }
            viewModel.timeScale.postValue(sparkAdapter.daysAgo)
        }

        radioGrpCasesType.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButtonNegative -> updateDisplayMetric(Metric.NEGATIVE)
                R.id.radioButtonPositive -> updateDisplayMetric(Metric.POSITIVE)
                R.id.radioButtonDeaths -> updateDisplayMetric(Metric.DEATH)

            }
        }


        dailyCumulativeToggle.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioButtonDaily -> updateIncrease(Increase.DAILY)
                R.id.radioButtonTotal -> updateIncrease(Increase.TOTAL)

            }
        }

    }

}
