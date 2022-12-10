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

    private val displayAsteroid = MutableLiveData<List<Asteroid>>()

    val asteroids: LiveData<List<Asteroid>> = displayAsteroid

    val pictureOfDay: LiveData<PictureOfDay?> = repository.pictureOfDay

    private val _selectedAsteroid = MutableLiveData<Asteroid?>(null)

    val selectedAsteroid: LiveData<Asteroid?>
        get() = _selectedAsteroid

    init {
        viewModelScope.launch {
            filterAsteroidByDay()
            try {
                repository.loadAsteroidData()
            } catch (exception: java.lang.Exception) {
                Log.d(TAG, "Error: ${exception}")
            }
        }

    }

    fun asteroidItemSelected(asteroid: Asteroid?) {
        _selectedAsteroid.value = asteroid
    }

    fun filterAsteroidByDay() {
        viewModelScope.launch {
            try {
                val byDay = repository.getAsteroidByDay()
                displayAsteroid.value = byDay
            } catch (ex: java.lang.Exception) {
                Log.d(TAG, "filterAsteroidByWeek: $ex")
            }
        }
    }

    fun filterAsteroidByWeek() {
        viewModelScope.launch {
            try {
                val byWeek = repository.getAsteroidByWeek()
                displayAsteroid.value = byWeek
            } catch (ex: java.lang.Exception) {
                Log.d(TAG, "filterAsteroidByWeek: $ex")
            }
        }
    }

    fun filterAllAsteroid() {
        viewModelScope.launch {
            try {
                val all = repository.getAllAsteroid()
                displayAsteroid.value = all
            } catch (ex: java.lang.Exception) {
                Log.d(TAG, "filterAsteroidByWeek: $ex")
            }
        }
    }
}