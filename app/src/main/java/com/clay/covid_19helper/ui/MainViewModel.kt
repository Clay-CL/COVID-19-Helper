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
import com.clay.covid_19helper.R
import com.clay.covid_19helper.models.CountryCovidData
import com.clay.covid_19helper.models.CountryData
import com.clay.covid_19helper.models.IndiaData
import com.clay.covid_19helper.models.StateTimelineData
import com.clay.covid_19helper.repository.CovidRepository
import com.clay.covid_19helper.util.Increase
import com.clay.covid_19helper.util.Metric
import com.clay.covid_19helper.util.Resource
import com.clay.covid_19helper.util.TimeScale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

class MainViewModel(app: Application, private val covidRepository: CovidRepository) :
    AndroidViewModel(app) {

    val nationalCovidData: MutableLiveData<Resource<IndiaData>> = MutableLiveData()

    val stateTimelineData: MutableLiveData<Resource<StateTimelineData>> = MutableLiveData()

    val countryListData: MutableLiveData<Resource<CountryData>> = MutableLiveData()

    val worldData: MutableLiveData<Resource<CountryCovidData>> = MutableLiveData()

    val indiaData: MutableLiveData<Resource<CountryCovidData>> = MutableLiveData()

    val metricData: MutableLiveData<Metric> = MutableLiveData()

    val timeScale: MutableLiveData<TimeScale> = MutableLiveData()

    val increase: MutableLiveData<Increase> = MutableLiveData()

    val selectedState: MutableLiveData<Int> = MutableLiveData()

    init {
        getNationalCovidData()
        getStateWiseCovidData()
        getCountryListData()
        getWorldData()
        getIndiaData()
        postInitialValues()
    }


    private fun postInitialValues() {
        metricData.postValue(Metric.POSITIVE)
        timeScale.postValue(TimeScale.MAX)
        increase.postValue(Increase.DAILY)
        selectedState.postValue(0)
    }

    fun getNationalCovidData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                safeHandleNationalCovidDataResponse()
            }
        }
    }

    fun getStateWiseCovidData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                safeHandleStateWiseCovidDataResponse()
            }
        }
    }

    fun getCountryListData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                safeHandleCountryListDataResponse()
            }
        }
    }

    fun getWorldData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                safeHandleWorldDataResponse()
            }
        }
    }

    fun getIndiaData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                safeHandleIndiaDataResponse()
            }
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

    private suspend fun safeHandleStateWiseCovidDataResponse() {
        stateTimelineData.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = covidRepository.getStateWiseTimelinedata()
                stateTimelineData.postValue(handleStateWiseCovidDataResponse(response))
            } else {
                stateTimelineData.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> stateTimelineData.postValue(Resource.Error("Network Failure"))
                else -> stateTimelineData.postValue(Resource.Error(t.message))
            }
        }
    }

    private fun handleStateWiseCovidDataResponse(response: Response<StateTimelineData>): Resource<StateTimelineData>? {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }


    private suspend fun safeHandleCountryListDataResponse() {
        countryListData.postValue(Resource.Loading())
        try {

            if (hasInternetConnection()) {
                val response = covidRepository.getCountryList()

                countryListData.postValue(handleCountryListDataResponse(response))

            } else {
                countryListData.postValue(Resource.Error("No Internet Connection"))
            }

        } catch (t: Throwable) {
            when (t) {
                is IOException -> stateTimelineData.postValue(Resource.Error("Network Failure"))
                else -> stateTimelineData.postValue(Resource.Error(t.message))
            }
        }
    }

    private fun handleCountryListDataResponse(response: Response<CountryData>): Resource<CountryData>? {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private suspend fun safeHandleWorldDataResponse() {
        worldData.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = covidRepository.getWorldData()
                worldData.postValue(handleWorldDataResponse(response))
            } else {
                worldData.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> stateTimelineData.postValue(Resource.Error("Network Failure"))
                else -> stateTimelineData.postValue(Resource.Error(t.message))
            }
        }
    }

    private fun handleWorldDataResponse(response: Response<CountryCovidData>): Resource<CountryCovidData>? {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }


    private suspend fun safeHandleIndiaDataResponse() {
        indiaData.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = covidRepository.getIndiaData()
                indiaData.postValue(handleIndiaDataResponse(response))
            } else {
                indiaData.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> stateTimelineData.postValue(Resource.Error("Network Failure"))
                else -> stateTimelineData.postValue(Resource.Error(t.message))
            }
        }
    }

    private fun handleIndiaDataResponse(response: Response<CountryCovidData>): Resource<CountryCovidData>? {
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