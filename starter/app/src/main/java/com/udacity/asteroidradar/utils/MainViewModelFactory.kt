package com.udacity.asteroidradar.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.AsteroidRepository
import com.udacity.asteroidradar.main.MainViewModel

class MainViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            val repository = AsteroidRepository(application)
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        } else {
            throw java.lang.IllegalArgumentException("Cannot init view model")
        }
    }
}