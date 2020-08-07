package com.clay.covid_19helper.models


import com.google.gson.annotations.SerializedName

data class StateTimelineData(
    @SerializedName("states_daily")
    val statesDaily: List<StatesDaily>
)