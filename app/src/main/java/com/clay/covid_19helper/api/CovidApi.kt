package com.clay.covid_19helper.api

import com.clay.covid_19helper.models.CountryCovidData
import com.clay.covid_19helper.models.CountryData
import com.clay.covid_19helper.models.IndiaData
import com.clay.covid_19helper.models.StateTimelineData
import retrofit2.Response
import retrofit2.http.GET

interface CovidApi {

    @GET("data.json")
    suspend fun getNationalCovidData(): Response<IndiaData>

    @GET("states_daily.json")
    suspend fun getStateWiseTimelinedata(): Response<StateTimelineData>

//    @GET("/help/countries")
//    @Headers(value = ["x-rapidapi-host: covid-19-data.p.rapidapi.com"])
//    suspend fun getCountryList(@Header("x-rapidapi-key") apiKey: String): Response<CountryData>

    @GET("countries/")
    suspend fun getCountryList(): Response<CountryData>

    @GET("confirmed/")
    suspend fun getWorldData(): Response<CountryCovidData>

    @GET("countries/India/confirmed/")
    suspend fun getIndiaData(): Response<CountryCovidData>


}