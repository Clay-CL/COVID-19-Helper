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
import com.clay.covid_19helper.adapters.CovidSparkAdapter
import com.clay.covid_19helper.models.CasesTimeSeries
import com.clay.covid_19helper.models.StatesDaily
import com.clay.covid_19helper.ui.MainActivity
import com.clay.covid_19helper.ui.MainViewModel
import com.clay.covid_19helper.util.*
import com.clay.covid_19helper.util.Constants.INDIAN_STATES
import com.robinhood.ticker.TickerUtils
import kotlinx.android.synthetic.main.fragment_main.*
import timber.log.Timber
import java.text.NumberFormat

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
                        updateGraph(indiaData.casesTimeSeries)
                    }
                }
                is Resource.Error -> { }
                is Resource.Loading -> { }
            }
        })

        viewModel.stateTimelineData.observe(viewLifecycleOwner, Observer {  stateResource->
            when(stateResource) {
                is Resource.Success -> {
                    stateResource.data?.let {
                        LoadStateDataInBackGround(viewModel, requireContext(), it.statesDaily).execute()
                    }
                }
                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        })

        viewModel.metricData.observe(viewLifecycleOwner, Observer { metric ->
            metric?.let {
                updateRadioGroupMetrics(it)
                updateColorSparkChart(it)
                sparkAdapter.metric = it
//                viewModel.nationalCovidData.value?.data?.casesTimeSeries?.let {
//                    updateGraph(it)
//                }

                val selection = viewModel.selectedState.value!!
                viewModel.mappingStatesCases.value?.get(selection)?.let { data->
                    updateGraph(data)
                }

            }

        })

        viewModel.timeScale.observe(viewLifecycleOwner, Observer {
            it?.let {
                updateRadioGroupTimeScale(it)
                sparkAdapter.daysAgo = it
                val selection = viewModel.selectedState.value!!
                viewModel.mappingStatesCases.value?.get(selection)?.let { data->
                    updateGraph(data)
                }
            }

        })

        viewModel.increase.observe(viewLifecycleOwner, Observer {
            it?.let {
                updateRadioGroupIncrease(it)
                sparkAdapter.increase = it
                val selection = viewModel.selectedState.value!!
                viewModel.mappingStatesCases.value?.get(selection)?.let { data->
                    updateGraph(data)
                }
            }

        })


        viewModel.selectedState.observe(viewLifecycleOwner, Observer { selectedState ->
            //Timber.d("Selected State : $it")
            radioButtonTotal.isEnabled = selectedState == 0
            stateSpinner.selectedIndex = selectedState
            viewModel.mappingStatesCases.value?.get(selectedState)?.let { data ->
                Timber.d("Size: ${data.size} Selected State is $selectedState Data = $data")
                updateGraph(data)
            }

        })



    }

    private fun updateSelectedState(selection: Int) {
        viewModel.selectedState.postValue(selection)
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
        sparkAdapter.data = covidData
        sparkAdapter.notifyDataSetChanged()
    }

    private fun updateInfoForDate(covidData: CasesTimeSeries) {

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
                }?.toInt()
            }
        }



        casesTicker.text = NumberFormat.getInstance().format(numCases)
        // val outputDateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.US)
        dateLabel.text = covidData.date
    }


    private fun setUpAdapter() {
        sparkAdapter = CovidSparkAdapter()
        sparkChart.adapter = sparkAdapter
        stateSpinner.attachDataSource(INDIAN_STATES)

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

        stateSpinner.setOnSpinnerItemSelectedListener { _, _, position, _ ->
            updateSelectedState(position)
        }

    }


    class LoadStateDataInBackGround(
        val viewModel: MainViewModel, context: Context, val statesDaily: List<StatesDaily>
    ) : AsyncTask<Void, Void, Map<Int, List<CasesTimeSeries>>>() {


        override fun doInBackground(vararg params: Void?): Map<Int, List<CasesTimeSeries>> {

            val stateCasesMap = hashMapOf<Int, List<CasesTimeSeries>>()
            viewModel.nationalCovidData.value?.data?.casesTimeSeries?.let { nationalList ->
                stateCasesMap.put(
                    0,
                    nationalList
                )
            }


            val statesDaily = statesDaily
            val groupedStates = statesDaily.groupBy({ it.date }, { it })





            for (i in 1 until INDIAN_STATES.size) {

                val listOfCaseTimeSeries = mutableListOf<CasesTimeSeries>()
                listOfCaseTimeSeries.clear()

                groupedStates.asIterable().forEach { stateEntry ->

                    val confirmedData = stateEntry.value[0].getDataAsList()
                    val recoveredData = stateEntry.value[1].getDataAsList()
                    val deceasedData = stateEntry.value[2].getDataAsList()

                    val casesTimeSeries = CasesTimeSeries(
                        "0",
                        "0",
                        "0",
                        date = stateEntry.key,
                        totalconfirmed = null,
                        totaldeceased = null,
                        totalrecovered = null
                    )

                    val dailyConfirmed = confirmedData[i-1]
                    val dailyRecovered = recoveredData[i-1]
                    val dailyDeceased = deceasedData[i-1]

                    casesTimeSeries.dailyconfirmed = dailyConfirmed
                    casesTimeSeries.dailyrecovered = dailyRecovered
                    casesTimeSeries.dailydeceased = dailyDeceased

                    listOfCaseTimeSeries.add(casesTimeSeries)

                }
                //Timber.d("For $i data is $listOfCaseTimeSeries")

                stateCasesMap[i] = listOfCaseTimeSeries
            }

            return stateCasesMap
        }

        override fun onPostExecute(result: Map<Int, List<CasesTimeSeries>>?) {
            viewModel.mappingStatesCases.postValue(result)
        }
    }


}
