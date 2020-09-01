package com.clay.covid_19helper.util

import com.google.android.gms.maps.model.Circle

object MiscUtils {
    fun getZoomLevel(circleRadius: Float): Float {
        var zoomLevel = 8
        val radius = circleRadius + circleRadius / 2
        val scale = radius / 250.0
        zoomLevel = (16 - Math.log(scale) / Math.log(2.0)).toInt()
        return zoomLevel + .4f
    }
}