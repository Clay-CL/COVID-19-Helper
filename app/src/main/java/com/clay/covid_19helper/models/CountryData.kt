package com.clay.covid_19helper.models


import com.google.gson.annotations.SerializedName

data class CountryData(
    @SerializedName("countries")
    val countries: List<Country>
)