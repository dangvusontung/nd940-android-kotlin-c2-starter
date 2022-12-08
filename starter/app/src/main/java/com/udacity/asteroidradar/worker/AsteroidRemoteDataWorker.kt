package com.udacity.asteroidradar.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.AsteroidRepository
import com.udacity.asteroidradar.database.AsteroidDatabase

class AsteroidRemoteDataWorker(appContext: Context, parameters: WorkerParameters) :
    CoroutineWorker(appContext, parameters) {

    override suspend fun doWork(): Result {
        val repository = AsteroidRepository(applicationContext)

        return try {
            repository.loadAsteroidData()
            Result.success()
        } catch (exception: java.lang.Exception) {
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "GET_REMOTE_DATA"
    }
}