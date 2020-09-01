package com.clay.covid_19helper.models


import com.google.gson.annotations.SerializedName

data class WorldCountryCovidData(
    @SerializedName("confirmed")
    val confirmed: Int,
    @SerializedName("country_code")
    val countryCode: String,
    @SerializedName("dead")
    val dead: Int,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("location")
    val location: String,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("recovered")
    val recovered: Int,
    @SerializedName("updated")
    val updated: String
)