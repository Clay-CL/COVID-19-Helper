package com.clay.covid_19helper.repository

import com.clay.covid_19helper.api.RetrofitInstance

class CovidRepository {
    suspend fun getNationalCovidData() = RetrofitInstance.api.getNationalCovidData()

    suspend fun getStateWiseTimelinedata() = RetrofitInstance.api.getStateWiseTimelinedata()

}