package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.utils.toTimestamp

@Entity(tableName = "asteroid_table")
data class AsteroidRoomEntity(
    @PrimaryKey val id: Long,
    val codename: String,
    val closeApproachDate: Long,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
) {
    constructor(asteroid: Asteroid) : this(
        asteroid.id,
        asteroid.codename,
        asteroid.closeApproachDate.toTimestamp(),
        asteroid.absoluteMagnitude,
        asteroid.estimatedDiameter,
        asteroid.relativeVelocity,
        asteroid.distanceFromEarth,
        asteroid.isPotentiallyHazardous
    )
}