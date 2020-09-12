package com.clay.covid_19helper.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.ColorInt
import com.clay.covid_19helper.R
import com.google.android.gms.maps.model.Circle
import kotlin.math.ln

object MiscUtils {
    fun getZoomLevel(circleRadius: Float): Float {
        var zoomLevel = 8
        val radius = circleRadius + circleRadius / 2
        val scale = radius / 250.0
        zoomLevel = (16 - ln(scale) / ln(2.0)).toInt()
        return zoomLevel + .4f
    }


    fun checkForInternetConnection(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
                else -> return false
            }
        }

        return connectivityManager.activeNetworkInfo?.isAvailable ?: false
    }


    /*
            *
            * // returns a color based on the value given when the function is called
                    function getColor(coli) {

                     return coli >= 121 ? '#89a844' :
                     coli > 110 ? '#acd033' :
                     coli > 102.5 ? '#cbd97c' :
                     coli > 100 ? '#c2c083' :
                     '#d1ccad';
                    }
            *
            *
            * */

    // ranges is 0 to 10k, 10k to 1L, 1L to 5L, 5L+ -> for positive cases
    //



    fun getColor(quantity: Int, metric: Metric) = when (metric) {
        Metric.POSITIVE -> when {
            quantity > 5_00_000 -> {
                R.color.positive_val_1
            }
            quantity > 1_00_000 -> {
                R.color.positive_val_2
            }
            quantity > 10_000 -> {
                R.color.positive_val_3
            }
            else -> {
                R.color.positive_val_4
            }
        }

        Metric.NEGATIVE -> when {
            quantity > 5_00_000 -> {
                R.color.negative_val_1
            }
            quantity > 1_00_000 -> {
                R.color.negative_val_2
            }
            quantity > 10_000 -> {
                R.color.negative_val_2
            }
            else -> {
                R.color.negative_val_4
            }
        }
        Metric.DEATH -> when {
            quantity > 10_000 -> {
                R.color.death_val_1
            }
            quantity > 5_000 -> {
                R.color.death_val_2
            }
            quantity > 1_000 -> {
                R.color.death_val_3
            }
            else -> {
                R.color.death_val_4
            }
        }
    }

}