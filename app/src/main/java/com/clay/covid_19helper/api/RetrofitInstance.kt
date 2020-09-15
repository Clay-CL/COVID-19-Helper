package com.clay.covid_19helper.api

import com.clay.covid_19helper.util.Constants.BASE_URL
import com.clay.covid_19helper.util.Constants.WORLD_API_BASE_URL
import com.clay.covid_19helper.util.Constants.WORLD_COVID_DATA_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {

        private fun createRetrofitInstance(base_url: String): Retrofit {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .build()

            return Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api by lazy {
            createRetrofitInstance(BASE_URL).create(CovidApi::class.java)
            //myRetrofit.create(CovidApi::class.java)
        }
        val apiIndia by lazy {
            createRetrofitInstance(WORLD_API_BASE_URL).create(CovidApi::class.java)
        }
        val apiWorld by lazy {
            createRetrofitInstance(WORLD_COVID_DATA_URL).create(CovidApi::class.java)
        }

    }
}