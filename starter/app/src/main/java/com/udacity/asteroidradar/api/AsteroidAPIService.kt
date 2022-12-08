package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidAPIService {
    @GET("neo/rest/v1/feed")
    suspend fun getFeed(
        @Query("start_date") startDate: String?,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ) : String

    @GET("planetary/apod")
    suspend fun getPictureOfTheDay(@Query("api_key") apiKey: String = Constants.API_KEY): PictureOfDay
}