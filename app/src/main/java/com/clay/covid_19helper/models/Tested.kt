package com.clay.covid_19helper.models


import com.google.gson.annotations.SerializedName

data class Tested(
    @SerializedName("individualstestedperconfirmedcase")
    val individualstestedperconfirmedcase: String,
    @SerializedName("positivecasesfromsamplesreported")
    val positivecasesfromsamplesreported: String,
    @SerializedName("samplereportedtoday")
    val samplereportedtoday: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("source1")
    val source1: String,
    @SerializedName("testedasof")
    val testedasof: String,
    @SerializedName("testpositivityrate")
    val testpositivityrate: String,
    @SerializedName("testsconductedbyprivatelabs")
    val testsconductedbyprivatelabs: String,
    @SerializedName("testsperconfirmedcase")
    val testsperconfirmedcase: String,
    @SerializedName("testspermillion")
    val testspermillion: String,
    @SerializedName("totalindividualstested")
    val totalindividualstested: String,
    @SerializedName("totalpositivecases")
    val totalpositivecases: String,
    @SerializedName("totalsamplestested")
    val totalsamplestested: String,
    @SerializedName("updatetimestamp")
    val updatetimestamp: String
)