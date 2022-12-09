package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidRepository
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.utils.DateUtil
import kotlinx.coroutines.launch
import java.util.Date
import kotlin.math.log

private const val TAG = "MainViewModel"
class MainViewModel(private val repository: AsteroidRepository) : ViewModel() {

    private var _asteroid = repository.asteroids
    private var _asteroidsByWeek = repository.getAsteroidByWeek

    val asteroids: LiveData<List<Asteroid>>
        get() = displayAsteroid

    val asteroidsByWeek: LiveData<List<Asteroid>>
        get() = _asteroidsByWeek

    private val displayAsteroid = MutableLiveData<List<Asteroid>>()

    val pictureOfDay: LiveData<PictureOfDay?> = repository.pictureOfDay

    private val _selectedAsteroid = MutableLiveData<Asteroid?>(null)

    val selectedAsteroid: LiveData<Asteroid?>
        get() = _selectedAsteroid

    init {
        viewModelScope.launch {
            try {
                repository.loadAsteroidData()
                displayAsteroid.postValue(repository.asteroids.value)
            } catch (exception: java.lang.Exception) {
                Log.d(TAG, "Error: ${exception}")
            }
        }

    }

    fun asteroidItemSelected(asteroid: Asteroid?) {
        _selectedAsteroid.value = asteroid
    }

    fun filterAsteroidByDay() {
        displayAsteroid.value = repository.asteroids.value
        Log.d(TAG, "filterAsteroidByDay hashcode: ${displayAsteroid.hashCode()}")
    }

    fun filterAsteroidByWeek() {
        displayAsteroid.value = repository.getAsteroidByWeek.value
        Log.d(TAG, "filterAsteroidByWeek: hashcode ${displayAsteroid.hashCode()}")
    }

}