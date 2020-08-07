package com.clay.covid_19helper.models


import com.google.gson.annotations.SerializedName

data class IndiaData(
    @SerializedName("cases_time_series")
    val casesTimeSeries: List<CasesTimeSeries>,
    @SerializedName("statewise")
    val statewise: List<Statewise>,
    @SerializedName("tested")
    val tested: List<Tested>
)