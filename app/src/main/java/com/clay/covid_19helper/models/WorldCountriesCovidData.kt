package com.clay.covid_19helper.models


import com.google.gson.annotations.SerializedName

data class WorldCountriesCovidData(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val data: List<WorldCountryCovidData>
)