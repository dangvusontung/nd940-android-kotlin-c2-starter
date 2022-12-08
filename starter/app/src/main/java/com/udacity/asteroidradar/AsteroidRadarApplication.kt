package com.udacity.asteroidradar

import android.app.Application
import androidx.constraintlayout.solver.state.State.Constraint
import androidx.work.*
import com.udacity.asteroidradar.worker.AsteroidRemoteDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidRadarApplication : Application() {
    private val applicationScope = CoroutineScope((Dispatchers.Main))

    override fun onCreate() {
        super.onCreate()
        setUpWorks()
    }

    private fun setUpWorks() {
        applicationScope.launch {
            val constraint = Constraints.Builder()
                .setRequiresCharging(true) // Require charging
                .setRequiredNetworkType(NetworkType.CONNECTED) // Require connect to the internet
                .build()

            val requestPeriodicWorkRequest =
                PeriodicWorkRequestBuilder<AsteroidRemoteDataWorker>(1, TimeUnit.DAYS) //1 time/day
                    .setConstraints(constraint)
                    .build()

            WorkManager.getInstance().enqueueUniquePeriodicWork(
                AsteroidRemoteDataWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.REPLACE,
                requestPeriodicWorkRequest
            )
        }
    }

}