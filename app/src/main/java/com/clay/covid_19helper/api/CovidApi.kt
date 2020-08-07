package com.clay.covid_19helper.api

import com.clay.covid_19helper.models.IndiaData
import com.clay.covid_19helper.models.StateTimelineData
import retrofit2.Response
import retrofit2.http.GET

interface CovidApi {

    @GET("/data.json")
    suspend fun getNationalCovidData(): Response<IndiaData>

    @GET("/states_daily.json")
    suspend fun getStateWiseTimelinedata(): Response<StateTimelineData>
}