package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidRepository
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.database.AsteroidDatabase
import kotlinx.coroutines.launch
import java.util.Date

private const val TAG = "MainViewModel"
class MainViewModel(private val repository: AsteroidRepository) : ViewModel() {

    private val _asteroid = repository.asteroids

    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroid

    val pictureOfDay: LiveData<PictureOfDay?> = repository.pictureOfDay

    private val _selectedAsteroid = MutableLiveData<Asteroid?>(null)
    val selectedAsteroid: LiveData<Asteroid?>
        get() = _selectedAsteroid


    init {
        viewModelScope.launch {
            repository.loadAsteroidData()
        }
    }

    fun asteroidItemSelected(asteroid: Asteroid?) {
        _selectedAsteroid.value = asteroid
    }
}