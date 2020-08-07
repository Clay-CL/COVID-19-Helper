package com.clay.covid_19helper.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.clay.covid_19helper.BaseApplication
import com.clay.covid_19helper.models.IndiaData
import com.clay.covid_19helper.models.StateTimelineData
import com.clay.covid_19helper.repository.CovidRepository
import com.clay.covid_19helper.util.Metric
import com.clay.covid_19helper.util.Resource
import com.clay.covid_19helper.util.TimeScale
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MainViewModel(app: Application, private val covidRepository: CovidRepository) :
    AndroidViewModel(app) {

    val nationalCovidData: MutableLiveData<Resource<IndiaData>> = MutableLiveData()

    val stateTimelineData: MutableLiveData<Resource<StateTimelineData>> = MutableLiveData()

    val metricData: MutableLiveData<Metric> = MutableLiveData()

    val timeScale: MutableLiveData<TimeScale> = MutableLiveData()

    val selectedState: MutableLiveData<Int> = MutableLiveData()

    init {
        getNationalCovidData()
        postInitialValues()
    }

    private fun postInitialValues() {
        metricData.postValue(Metric.POSITIVE)
        timeScale.postValue(TimeScale.MAX)
        selectedState.postValue(0)
    }

    fun getNationalCovidData() {
        viewModelScope.launch {
            safeHandleNationalCovidDataResponse()
        }
    }

    private suspend fun safeHandleNationalCovidDataResponse() {
        nationalCovidData.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = covidRepository.getNationalCovidData()
                nationalCovidData.postValue(handleNationalCovidDataResponse(response))
            } else {
                nationalCovidData.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> nationalCovidData.postValue(Resource.Error("Network Failure"))
                else -> nationalCovidData.postValue(Resource.Error(t.message))
            }
        }
    }

    private fun handleNationalCovidDataResponse(response: Response<IndiaData>): Resource<IndiaData>? {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }


    // internet Utils

    private fun hasInternetConnection(): Boolean {
        //we need the connectivity manager
        // we don't necessarily need activity context
        // we can even use application context here as
        // as long as the app lives
        // the applicationContext is not null
        val connectivityManager = getApplication<BaseApplication>()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

            return when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (this.type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }

        return false

    }


}