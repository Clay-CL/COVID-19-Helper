package com.clay.covid_19helper.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clay.covid_19helper.R
import com.clay.covid_19helper.models.newsmodels.NewsApiResponse
import com.clay.covid_19helper.repository.NewsRepository
import com.clay.covid_19helper.util.MiscUtils
import com.clay.covid_19helper.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel @ViewModelInject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _news = MutableLiveData<Resource<NewsApiResponse>>()

    val news: LiveData<Resource<NewsApiResponse>> = _news

    var newsPage = 1

    private var newsApiResponse: NewsApiResponse? = null

    init {
        getNews(repository.context.resources.getString(R.string.news_api_key))
    }

    fun getNews(apiKey: String) = viewModelScope.launch {
       safeNewsCall(apiKey)
    }

    private suspend fun safeNewsCall(apiKey: String) {
        _news.postValue(Resource.Loading())
        try{
            if(MiscUtils.checkForInternetConnection(repository.context)){
                val response = repository.getCovidNews(apiKey, newsPage)
                _news.postValue(handleBreakingNewsResponse(response))
            }else{
                _news.postValue(Resource.Error("No Internet Connection"))
            }
        }catch(t: Throwable){
            when(t){
                is IOException -> _news.postValue(Resource.Error("Network Failure"))
                else -> _news.postValue(Resource.Error("Conversion Error"))
            }
        }
    }


    private fun handleBreakingNewsResponse(response: Response<NewsApiResponse>): Resource<NewsApiResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                /* pagination occurs here, where we increment the pageNumber to display
                 this does not cause the old data to vanish because we
                 are going to append the new data with the old
                 just that scrolling is smoother
                 and the activity won't refresh our data every time*/
                newsPage++
                if (newsApiResponse == null) {
                    newsApiResponse = resultResponse
                } else {
                    val oldArticles = newsApiResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(newsApiResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


}