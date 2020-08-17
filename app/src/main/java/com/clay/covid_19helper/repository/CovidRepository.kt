package com.clay.covid_19helper.repository

import com.clay.covid_19helper.api.RetrofitInstance

class CovidRepository {
    suspend fun getNationalCovidData() = RetrofitInstance.api.getNationalCovidData()

    suspend fun getStateWiseTimelinedata() = RetrofitInstance.api.getStateWiseTimelinedata()

    suspend fun getCountryList() = RetrofitInstance.apiWorld.getCountryList()

    suspend fun getWorldData() = RetrofitInstance.apiWorld.getWorldData()

    suspend fun getIndiaData() = RetrofitInstance.apiWorld.getIndiaData()


}