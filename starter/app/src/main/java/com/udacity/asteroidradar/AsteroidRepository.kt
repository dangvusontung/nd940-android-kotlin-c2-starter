package com.udacity.asteroidradar

import android.app.Application
import android.content.Context
import android.graphics.Picture
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.api.AsteroidAPIService
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.AsteroidRoomEntity
import com.udacity.asteroidradar.utils.DateUtil
import com.udacity.asteroidradar.utils.toQueryString
import com.udacity.asteroidradar.utils.toTimestamp
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.Date

class AsteroidRepository(context: Context) {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .build()

    private val apiService = retrofit.create(AsteroidAPIService::class.java)

    private val asteroidDao = AsteroidDatabase.getInstance(context.applicationContext).asteroidDao

    private val _asteroid = asteroidDao.getTodayAsteroid(Date().time)

    private val _pictureOfTheDay = MutableLiveData<PictureOfDay?>(null)

    val asteroids: LiveData<List<Asteroid>> = _asteroid.map { data ->
        data.map {
            Asteroid(it)
        }
    }

    val pictureOfDay: LiveData<PictureOfDay?>
        get() = _pictureOfTheDay

    suspend fun loadAsteroidData() {
        val currentDate = Date().toQueryString()
        val response = apiService.getFeed(currentDate)
        val jsonObject = JSONObject(response)
        val asteroids = parseAsteroidsJsonResult(jsonObject)

        val asteroidEntities = asteroids.map {
            AsteroidRoomEntity(it)
        }

        asteroidEntities.forEach {
            asteroidDao.insert(it)
        }

        val pictureOfDay = apiService.getPictureOfTheDay()
        _pictureOfTheDay.value = pictureOfDay
    }

    val getAsteroidByWeek = asteroidDao.getAsteroidByDate(
        DateUtil.startOfWeek().toTimestamp(),
        DateUtil.endOfWeek().toTimestamp()
    ).map { data ->
        data.map {
            Asteroid(it)
        }
    }

    suspend fun getAsteroidByDay(): List<Asteroid> {
        val result = asteroidDao.getTodayAsteroidQuery(Date().time).map {
            Asteroid(it)
        }
        return result
    }

    suspend fun getAsteroidByWeek(): List<Asteroid> {
        val result = asteroidDao.getAsteroidByDateQuery(
            DateUtil.startOfWeek().toTimestamp(),
            DateUtil.endOfWeek().toTimestamp()
        ).map {
            Asteroid(it)
        }

        return result
    }

    suspend fun getAllAsteroid(): List<Asteroid> {
        val result = asteroidDao.getAllAsteroid().map {
            Asteroid(it)
        }

        return result
    }
}