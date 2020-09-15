package com.clay.covid_19helper.models


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CasesTimeSeries(
    @SerializedName("dailyconfirmed")
    var dailyconfirmed: String,
    @SerializedName("dailydeceased")
    var dailydeceased: String,
    @SerializedName("dailyrecovered")
    var dailyrecovered: String,
    @SerializedName("date")
    var date: String,
    @SerializedName("totalconfirmed")
    var totalconfirmed: String?,
    @SerializedName("totaldeceased")
    var totaldeceased: String?,
    @SerializedName("totalrecovered")
    var totalrecovered: String?
)