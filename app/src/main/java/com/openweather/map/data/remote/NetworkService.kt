package com.openweather.map.data.remote

import com.openweather.map.data.model.response.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Shweta on 15/11/21.
 */

interface NetworkService {

    @GET("data/2.5/weather")
    fun searchByCityName(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): Single<WeatherResponse>
}
