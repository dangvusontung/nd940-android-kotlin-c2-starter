package com.udacity.asteroidradar

import android.os.Parcelable
import com.udacity.asteroidradar.database.AsteroidRoomEntity
import com.udacity.asteroidradar.utils.toQueryString
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Asteroid(
    val id: Long, val codename: String, val closeApproachDate: String,
    val absoluteMagnitude: Double, val estimatedDiameter: Double,
    val relativeVelocity: Double, val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
) : Parcelable {
    constructor(roomEntity: AsteroidRoomEntity) : this(
        roomEntity.id,
        roomEntity.codename,
        Date(roomEntity.closeApproachDate).toQueryString(),
        roomEntity.absoluteMagnitude,
        roomEntity.estimatedDiameter,
        roomEntity.relativeVelocity,
        roomEntity.distanceFromEarth,
        roomEntity.isPotentiallyHazardous
    )
}