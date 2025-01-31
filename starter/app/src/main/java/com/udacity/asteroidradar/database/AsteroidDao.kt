package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update

@Dao
interface AsteroidDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(asteroid: AsteroidRoomEntity)

    @Update
    suspend fun update(asteroid: AsteroidRoomEntity)

    @Query("SELECT * FROM asteroid_table WHERE closeApproachDate >=:timeStamp ORDER BY closeApproachDate ASC")
    fun getTodayAsteroid(timeStamp: Long): LiveData<List<AsteroidRoomEntity>>

    @Query("SELECT * FROM asteroid_table WHERE closeApproachDate >=:startDateTimeStamp AND closeApproachDate <=:endDateTimeStamp ORDER BY closeApproachDate ASC")
    fun getAsteroidByDate(startDateTimeStamp: Long, endDateTimeStamp: Long): LiveData<List<AsteroidRoomEntity>>

    @Query("SELECT * FROM asteroid_table WHERE closeApproachDate >=:timeStamp ORDER BY closeApproachDate ASC")
    suspend fun getTodayAsteroidQuery(timeStamp: Long): List<AsteroidRoomEntity>

    @Query("SELECT * FROM asteroid_table WHERE closeApproachDate >=:startDateTimeStamp AND closeApproachDate <=:endDateTimeStamp ORDER BY closeApproachDate")
    suspend fun getAsteroidByDateQuery(startDateTimeStamp: Long, endDateTimeStamp: Long): List<AsteroidRoomEntity>

    @Query("SELECT * FROM asteroid_table ORDER BY closeApproachDate ASC")
    suspend fun getAllAsteroid(): List<AsteroidRoomEntity>
}