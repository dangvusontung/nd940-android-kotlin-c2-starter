package com.udacity.asteroidradar.utils

import java.util.*

object DateUtil {
    fun startOfWeek(): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        return calendar.time.toQueryString()
    }

    fun endOfWeek(): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        return calendar.time.toQueryString()
    }
}