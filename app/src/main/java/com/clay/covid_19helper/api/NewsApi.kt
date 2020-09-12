package com.clay.covid_19helper.api

import com.clay.covid_19helper.models.newsmodels.NewsApiResponse
import com.clay.covid_19helper.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/everything")
    suspend fun getCovidNews(
        @Query("q") query: String = Constants.COVID_QUERY,
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey")apiKey: String
    ): Response<NewsApiResponse>

}