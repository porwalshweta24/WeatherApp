package com.openweather.map.core

/**
 * Created by Shweta on 15/11/21.
 */

object Constants {

    object NetworkService {
        const val TAG = "OpenWeatherMap"
        const val BASE_URL = "http://api.openweathermap.org/"//data/2.5/"
        const val API_KEY = "751d80f6c314139192ffcb62c107e654"
//        const val API_KEY_QUERY = "appid"
        const val DATABASE_NAME = "openweathermap-db"

    }


    object Coords {
        const val LAT = "lat"
        const val LON = "lon"
        const val METRIC = "metric"
    }
}
