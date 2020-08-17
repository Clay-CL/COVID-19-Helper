package com.clay.covid_19helper.adapters

import android.graphics.RectF
import com.clay.covid_19helper.models.CasesTimeSeries
import com.clay.covid_19helper.util.Increase
import com.clay.covid_19helper.util.Metric
import com.clay.covid_19helper.util.TimeScale
import com.robinhood.spark.SparkAdapter

class CovidSparkAdapter : SparkAdapter() {

    var dailyNationalData = listOf<CasesTimeSeries>()

    var metric = Metric.POSITIVE

    var daysAgo = TimeScale.MAX

    var increase = Increase.DAILY

    override fun getY(index: Int): Float {
        val chosenDayData = dailyNationalData[index]
        when(increase) {
            Increase.DAILY -> {
                return when (metric) {
                    Metric.NEGATIVE -> chosenDayData.dailyrecovered
                    Metric.POSITIVE -> chosenDayData.dailyconfirmed
                    Metric.DEATH -> chosenDayData.dailydeceased
                }.toFloat()
            }
            Increase.TOTAL -> {
                return when (metric) {
                    Metric.NEGATIVE -> chosenDayData.totalrecovered
                    Metric.POSITIVE -> chosenDayData.totalconfirmed
                    Metric.DEATH -> chosenDayData.totaldeceased
                }.toFloat()
            }
        }
    }

    override fun getItem(index: Int) = dailyNationalData[index]

    override fun getCount() = dailyNationalData.size

    override fun getDataBounds(): RectF {
        val bounds = super.getDataBounds()
        if (daysAgo != TimeScale.MAX) {
            bounds.apply {
                left = count - daysAgo.numDays.toFloat()
            }
        }
        return bounds
    }
}