package com.clay.covid_19helper.api

import retrofit2.http.GET

interface CovidApi {

    @GET("/data.json")
    suspend fun getNationalData()
}