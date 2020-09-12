package com.clay.covid_19helper.repository

import android.app.Application
import com.clay.covid_19helper.api.NewsApi
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApi: NewsApi,
    val context: Application
) {
    suspend fun getCovidNews(apiKey: String, newsPage: Int) = newsApi.getCovidNews(apiKey = apiKey, pageNumber = newsPage)
}